package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.NetworkMessage;
import it.polimi.ingsw.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.messages.clienttoserver.launcher.SendUserIdentifier;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.messages.servertoclient.PingMessage;
import it.polimi.ingsw.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.User;

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
public class ConnectionHandler implements Runnable {

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
    private final int SOKET_TIME_OUT = 10;
    /**
     * The time, in seconds, after that the socket server side will signal a timo-out exception
     */
    private final int Server_SOCKET_TIME_OUT = 15;
    /**
     * The input stream from the server
     */
    private ObjectInputStream input;
    /**
     * The output stream to the server
     */
    private ObjectOutputStream output;


    /**
     * Creates a new connection with the server using the IP and port specified.
     *
     * @param serverIP   the IP of the server
     * @param serverPort the port to use to connect on the server
     * @throws IOException if an I/O error occurs when creating the connection
     */
    protected ConnectionHandler(String serverIP, int serverPort) throws IOException {
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

        try {
            trySendSecret();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Can't send identity to server!");
            try {
                server.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }

        long resendDelay = 8000;
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleWithFixedDelay(
                this::sendPing, 1, Server_SOCKET_TIME_OUT / 3, TimeUnit.SECONDS);
        executorService.scheduleWithFixedDelay(
                this::checkForExpired, resendDelay, resendDelay, TimeUnit.MILLISECONDS);

        try {
            handleConnection();
        } catch (IOException e) {
            System.out.println("server has died");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Error in protocol");
        } finally {
            executorService.shutdown();
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleConnection() throws IOException, ClassNotFoundException {
        try {
            while (true) {
                try {
                    Object message = input.readObject();
                    handleMessage(message);
                } catch (SocketTimeoutException e) {
                    // TODO: 11/05/2022 show connection error on screen
                    System.out.println("Connecting...");
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
                parentMessage.processResponse(response);
            }
            return;
        }
        ServerCommandNetMsg request = (ServerCommandNetMsg) message;
        request.processMessage(this);
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
    public void sendMessage(NetworkMessage message) {
        try {
            output.writeObject(message);
        } catch (IOException e) {
            System.out.println("Something went wrong sending message " + message.getIdentifier());
        }
        if (message instanceof ClientCommandNetMsg clientMsg)
            sentMessages.putIfAbsent(clientMsg.getIdentifier(), clientMsg);
    }

    private void trySendSecret() throws IOException, ClassNotFoundException {
        String identifier;
        try {
            identifier = readSecret();
        } catch (IOException e) {
            identifier = new User().getIdentifier();
        }

        System.out.println("The secret is:\t" + identifier);
        sendMessage(new SendUserIdentifier(identifier));
        ResponseMessage responseMessage = (ResponseMessage) input.readObject();
        if (responseMessage.isSuccess())
            System.out.println("Secret received successfully");
    }

    private String readSecret() throws IOException {
        String path = "secret.txt";
        File file = new File(path);
        boolean createdNew = file.createNewFile();
        if (createdNew) {
            User newUser = new User();
            DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(file));
            outputStream.writeUTF(newUser.getIdentifier());
            return newUser.getIdentifier();
        }
        DataInputStream fileInputStream = new DataInputStream(new FileInputStream(file));
        return fileInputStream.readUTF();
    }
}
