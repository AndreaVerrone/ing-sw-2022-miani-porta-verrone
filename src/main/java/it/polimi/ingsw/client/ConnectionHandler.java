package it.polimi.ingsw.client;

import it.polimi.ingsw.messages.NetworkMessage;
import it.polimi.ingsw.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.messages.clienttoserver.CreateNewGameMessage;
import it.polimi.ingsw.messages.clienttoserver.EnterGame;
import it.polimi.ingsw.messages.clienttoserver.GetGames;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.messages.servertoclient.ServerCommandNetMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.*;
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

        long resendDelay = 8000;
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
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
        // TODO: 09/05/2022 change implementation. this is used for initial debugging
        boolean stop = false;
        Scanner scanner = new Scanner(System.in);
        while (!stop) {
            System.out.println("Send a message!");
            System.out.println("c for create, g for get all, e for enter");
            String command = scanner.nextLine();
            switch (command) {
                case "c" -> {
                    System.out.println("creating game");
                    System.out.println("choose player number");
                    int number = Integer.parseInt(scanner.nextLine());
                    System.out.println("choose difficulty");
                    boolean hard = Boolean.parseBoolean(scanner.nextLine());
                    output.writeObject(new CreateNewGameMessage(number, hard));
                    ((ServerCommandNetMsg) input.readObject()).processMessage(this);
                    stop = true;
                }
                case "g" -> {
                    output.writeObject(new GetGames());
                    ((ServerCommandNetMsg) input.readObject()).processMessage(this);
                }
                case "e" -> {
                    System.out.println("choose a game");
                    String gameID = scanner.nextLine();
                    System.out.println("choose a nickname");
                    String nickname = scanner.nextLine();
                    output.writeObject(new EnterGame(nickname, gameID));
                    var response = ((ResponseMessage) input.readObject());
                    if (response.isSuccess())
                        stop = true;
                    handleMessage(response);
                }
            }
        }

        try {
            while (true) {
                Object message = input.readObject();
                handleMessage(message);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("A violation of the protocol occurred");
        }
    }

    private void handleMessage(Object message) {
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
}
