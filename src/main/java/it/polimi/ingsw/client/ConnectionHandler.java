package it.polimi.ingsw.client;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.NetworkSender;
import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.messages.NetworkMessage;
import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.clienttoserver.game.QuitGame;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.SendUserIdentifier;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.network.messages.servertoclient.PingMessage;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * A class used to handle the connection with the server
 */
public class ConnectionHandler implements Runnable, NetworkSender {

    /**
     * The controller of the client
     */
    private final ClientView clientView;
    /**
     * The socket connected to the server
     */
    private final Socket server;

    /**
     * A collection of all the request messages sent to the client that not received a response yet
     */
    private final Map<UUID, ClientCommandNetMsg> sentMessages = new HashMap<>();
    /**
     * The time, in seconds, after that this socket will signal a time-out exception
     */
    private static final int SOKET_TIME_OUT = 10;
    /**
     * The time, in seconds, after that the socket server side will signal a timo-out exception
     */
    private static final int Server_SOCKET_TIME_OUT = 15;
    /**
     * The input stream from the server
     */
    private ObjectInputStream input;
    /**
     * The output stream to the server
     */
    private ObjectOutputStream output;

    /**
     * The path where the file containing the identifier of the user should be saved
     */
    public static final String fileIdentifierPath = "identifier.txt";

    /**
     * A flag indicating if the client requested to close the application.
     * This is used to gracefully close all the tasks and delete all not necessary resources.
     */
    private boolean wantToClose = false;

    /**
     * Creates a new connection with the server using the IP and port specified.
     *
     * @param clientView The view of the client
     * @param serverIP         the IP of the server
     * @param serverPort       the port to use to connect on the server
     * @throws IOException if an I/O error occurs when creating the connection
     */
    public ConnectionHandler(ClientView clientView, String serverIP, int serverPort) throws IOException{
        this.clientView = clientView;
        server = new Socket(serverIP, serverPort);
        server.setSoTimeout(SOKET_TIME_OUT * 1000);
    }


    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(server.getOutputStream());
            input = new ObjectInputStream(server.getInputStream());
        } catch (IOException e) {
            System.out.println("Can't reach the server!");
            return;
        }

        sendIdentifier();

        long checkUnreceivedDelay = 8000;
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleWithFixedDelay(
                this::sendPing, 1, Server_SOCKET_TIME_OUT / 3, TimeUnit.SECONDS);
        executorService.scheduleWithFixedDelay(
                this::checkForExpired, checkUnreceivedDelay, checkUnreceivedDelay, TimeUnit.MILLISECONDS);

        try {
            handleConnection();
        } catch (IOException e) {
            System.out.println("server has died");
            e.printStackTrace();
        } finally {
            executorService.shutdown();
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleConnection() throws IOException {
        try {
            while (!wantToClose) {
                try {
                    Object message = input.readObject();
                    handleMessage(message);
                } catch (SocketTimeoutException e) {
                    clientView.getScreenBuilder().build(ScreenBuilder.Screen.CONNECTION_ERROR);
                }
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("A violation of the protocol occurred");
        }
    }

    private void handleMessage(Object message) {
        if (message instanceof PingMessage)
            return;
        if (message instanceof ResponseMessage response) {
            UUID parentId = response.getParentMessage();
            boolean exists;
            synchronized (sentMessages) {
                exists = sentMessages.containsKey(parentId);
            }
            if (exists) {
                ClientCommandNetMsg parentMessage;
                synchronized (sentMessages) {
                    parentMessage = sentMessages.remove(parentId);
                }
                parentMessage.processResponse(response, clientView);
            }
            return;
        }
        ServerCommandNetMsg request = (ServerCommandNetMsg) message;
        sendMessage(ResponseMessage.newSuccess(request));
        request.processMessage(clientView);
    }

    private void checkForExpired() {
        Collection<ClientCommandNetMsg> messages;
        synchronized (sentMessages) {
            messages = sentMessages.values();
        }
        for (var message : messages) {
            if (message.isExpired()) {
                System.out.println("No response for " + message.getIdentifier());
                synchronized (sentMessages) {
                    sentMessages.remove(message.getIdentifier());
                }
            }
        }
    }

    private void sendPing() {
        try {
            output.writeObject(new PingMessage());
        } catch (IOException e) {
            System.out.println("Can't send ping to the server");
        }
    }

    /**
     * Sends a message to the client associated with this handler. If the message is a request, it also
     * put it in the queue for resending.
     *
     * @param message the message to send
     */
    @Override
    public void sendMessage(NetworkMessage message) {
        try {
            output.writeObject(message);
        } catch (IOException e) {
            System.out.println("Something went wrong sending message " + message.getIdentifier());
        }
        if (message instanceof ClientCommandNetMsg clientMsg)
            sentMessages.putIfAbsent(clientMsg.getIdentifier(), clientMsg);
    }

    private void sendIdentifier() {
        String identifier = readIdentifier();

        SendUserIdentifier userIdentifier = new SendUserIdentifier(identifier);
        sendMessage(userIdentifier);
    }

    private String readIdentifier() {
        File file = new File(fileIdentifierPath);
        if (file.exists()) {
            try (FileInputStream fileInput = new FileInputStream(file);
                 DataInputStream inputStream = new DataInputStream(fileInput)) {
                return inputStream.readUTF();
            } catch (IOException e) {
                deleteIdentifier();
            }
        }
        User newUser = new User();
        try (FileOutputStream fileOutputStream = new FileOutputStream(file);
             DataOutputStream outputStream = new DataOutputStream(fileOutputStream)) {
            outputStream.writeUTF(newUser.getIdentifier());
        } catch (IOException e) {
            deleteIdentifier();
        }
        return newUser.getIdentifier();
    }

    /**
     * Quits from the game the client is in, regardless of the state of the game.
     */
    public void quitGame(){
        sendMessage(new QuitGame());
        deleteIdentifier();
        sendIdentifier();
    }

    /**
     * Closes all the current tasks and terminates the application, causing no more messages to be read or sent.
     */
    public void closeApplication(){
        sendMessage(new QuitGame());
        wantToClose = true;
        deleteIdentifier();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void deleteIdentifier(){
        File file = new File(fileIdentifierPath);
        if (file.exists())
            file.delete();
    }
}
