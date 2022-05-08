package it.polimi.ingsw.server;

import it.polimi.ingsw.messages.NetworkMessage;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.messages.clienttoserver.ClientCommandNetMsg;
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
 * A class used to handle client connection
 */
public class ClientHandler implements Runnable{

    /**
     * The socket bound to this client
     */
    private final Socket client;

    /**
     * The output stream of this client
     */
    private ObjectOutputStream output;

    /**
     * The input stream of this client
     */
    private ObjectInputStream input;

    /**
     * A collection of all the request messages sent to the client that not received a response yet
     */
    private final Map<UUID, ServerCommandNetMsg> sentMessages = new HashMap<>();

    /**
     * Creates a new client handler for the provided socket connection
     * @param client the socket bound to this client
     */
    public ClientHandler(Socket client){
        this.client = client;
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

        long resendDelay = 4000;
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(
                this::resendMessages, resendDelay,resendDelay, TimeUnit.MILLISECONDS);

        try {
            handleConnection();
        } catch (IOException e){
            System.out.println("An error occurred when handling client " + client.getInetAddress());
        } finally {
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
                Object message = input.readObject();
                handleMessage(message);
            }
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("A violation of the protocol occurred for " + client.getInetAddress());
        }
    }

    private void handleMessage(Object message) {
        if (message instanceof ResponseMessage response){
            UUID parentId = response.getParentMessage();
            boolean exists;
            synchronized (sentMessages){
                exists = sentMessages.containsKey(parentId);
            }
            if (exists) {
                ServerCommandNetMsg parentMessage;
                synchronized (sentMessages){
                    parentMessage = sentMessages.remove(parentId);
                }
                parentMessage.processResponse(response);
            }
            return;
        }
        ClientCommandNetMsg request = (ClientCommandNetMsg) message;
        request.processMessage(this);
    }

    /**
     * Sends a message to the client associated with this handler. If the message is a request, it also
     * put it in the queue for resending.
     * @param message the message to send
     */
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
        synchronized (sentMessages){
            messages = sentMessages.values();
        }
        for (var message: messages){
            if (message.isExpired()) {
                message.resetTimestamp();
                sendMessage(message);
            }
        }
    }
}
