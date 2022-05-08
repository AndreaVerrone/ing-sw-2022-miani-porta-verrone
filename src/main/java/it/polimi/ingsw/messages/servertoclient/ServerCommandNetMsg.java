package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.messages.NetworkMessage;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A command message sent from the server to the client
 */
abstract public class ServerCommandNetMsg extends NetworkMessage {

    /**
     * The time, expressed in milliseconds since epoch, when this message was sent.
     */
    private final long whenSent;

    /**
     * Creates a new message that needs to be sent to a client
     */
    ServerCommandNetMsg(){
        whenSent = System.currentTimeMillis();
    }

    /**
     * A method used to process this message.
     * @apiNote This method runs in the client.
     */
    abstract public void processMessage();

    /**
     * Processes the response sent from the client.
     * @param response the response of this request
     * @implNote This method runs in the server.
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
}
