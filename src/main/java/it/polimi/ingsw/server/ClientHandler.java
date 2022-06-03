package it.polimi.ingsw.server;

import it.polimi.ingsw.network.NetworkSender;
import it.polimi.ingsw.network.User;
import it.polimi.ingsw.network.messages.NetworkMessage;
import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.SendUserIdentifier;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.network.messages.servertoclient.PingMessage;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
 * A class used to handle client connection
 */
public class ClientHandler implements Runnable, NetworkSender {

    /**
     * The socket bound to this client
     */
    private final Socket client;
    /**
     * The session controller for this client.
     */
    private final SessionController sessionController;
    /**
     * A collection of all the request messages sent to the client that not received a response yet
     */
    private final Map<UUID, ServerCommandNetMsg> sentMessages = new HashMap<>();
    /**
     * The output stream of this client
     */
    private ObjectOutputStream output;
    /**
     * The input stream of this client
     */
    private ObjectInputStream input;

    /**
     * Creates a new client handler for the provided socket connection
     *
     * @param client the socket bound to this client
     */
    public ClientHandler(Socket client) {
        this.client = client;
        sessionController = new SessionController(this);
    }

    @Override
    public void run() {
        try {
            output = new ObjectOutputStream(client.getOutputStream());
            input = new ObjectInputStream(client.getInputStream());
        } catch (IOException e) {
            System.out.println("Can't open connection to " + client.getInetAddress());
            return;
        }

        try {
            ((SendUserIdentifier) input.readObject()).process(this);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("An error occurred with identification of client");
            try {
                client.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            return;
        }

        long resendDelay = 4000;
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);
        executorService.scheduleWithFixedDelay(
                this::sendPing, 1, Server.CLIENT_SOCKET_TIME_OUT / 3, TimeUnit.SECONDS);
        executorService.scheduleWithFixedDelay(
                this::resendMessages, resendDelay, resendDelay, TimeUnit.MILLISECONDS);

        try {
            handleConnection();
        } catch (IOException e) {
            System.out.println("An error occurred when handling client " + client.getInetAddress());
        } finally {
            sessionController.detachFromGame();
            executorService.shutdown();
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleConnection() throws IOException {

        try {
            while (true) {
                try {
                    Object message = input.readObject();
                    handleMessage(message);
                } catch (SocketTimeoutException e) {
                    sessionController.skipPlayerTurn();
                }
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("A violation of the protocol occurred for " + client.getInetAddress());
        }
    }

    private void handleMessage(Object message) {
        if (message instanceof PingMessage)
            return;
        if (message instanceof ResponseMessage response) {
            UUID parentId = response.getParentMessage();
            synchronized (sentMessages) {
                sentMessages.remove(parentId);
            }
            return;
        }
        ClientCommandNetMsg request = (ClientCommandNetMsg) message;
        request.processMessage(this);
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
        if (message instanceof ServerCommandNetMsg serverMsg)
            sentMessages.putIfAbsent(serverMsg.getIdentifier(), serverMsg);
    }

    private void resendMessages() {
        Collection<ServerCommandNetMsg> messages;
        synchronized (sentMessages) {
            messages = sentMessages.values();
        }
        for (var message : messages) {
            if (message.isExpired()) {
                message.resetTimestamp();
                sendMessage(message);
            }
        }
    }

    private void sendPing() {
        try {
            output.writeObject(new PingMessage());
        } catch (IOException e) {
            System.out.println("Can't send ping to " + client.getInetAddress());
        }
    }

    /**
     * Sets the user of this connection.
     *
     * @param user the user that opened this connection
     */
    public void setUser(User user) {
        sessionController.setUser(user);
    }

    public SessionController getSessionController() {
        return sessionController;
    }

}
