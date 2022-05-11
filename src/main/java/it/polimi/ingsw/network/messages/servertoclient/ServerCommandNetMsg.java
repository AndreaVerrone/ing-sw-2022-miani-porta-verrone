package it.polimi.ingsw.network.messages.servertoclient;

import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.network.messages.NetworkMessage;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;

/**
 * A command message sent from the server to the client
 */
abstract public class ServerCommandNetMsg extends NetworkMessage {

    /**
     * The time, expressed in milliseconds since epoch, when this message was sent.
     */
    private long whenSent;

    /**
     * Creates a new message that needs to be sent to a client
     */
    protected ServerCommandNetMsg(){
        whenSent = System.currentTimeMillis();
    }

    /**
     * A method used to process this message.
     * <p>
     * This method runs in the client.
     * @param client the client that receives this message
     */
    abstract public void processMessage(ConnectionHandler client);

    /**
     * Processes the response sent from the client.
     * <p>
     * This method runs in the server.
     * @param response the response of this request
     */
    abstract public void processResponse(ResponseMessage response);

    /**
     * Returns if this message was sent more than 2 seconds ago.
     * @return {@code true} if 2 seconds are passed, {@code false} otherwise
     */
    public boolean isExpired(){
        long timePassed = System.currentTimeMillis() - whenSent;
        long expireTime = 2000;
        return timePassed > expireTime;
    }

    /**
     * Change the time in which this message was sent to now.
     */
    public void resetTimestamp(){
        whenSent = System.currentTimeMillis();
    }
}
