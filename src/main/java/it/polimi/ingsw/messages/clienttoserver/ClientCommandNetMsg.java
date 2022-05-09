package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.messages.NetworkMessage;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A command message sent from the client to the server
 */
abstract public class ClientCommandNetMsg extends NetworkMessage {

    /**
     * The time, expressed in milliseconds since epoch, when this message was sent.
     */
    private final long whenSent;

    /**
     * Creates a new message that needs to be sent to a client
     */
    ClientCommandNetMsg(){
        whenSent = System.currentTimeMillis();
    }

    /**
     * A method used to process this message.
     * <p>
     * This method runs in the server.
     * @param clientInServer the clientInServer that sent this message
     */
    abstract public void processMessage(ClientHandler clientInServer);

    /**
     * Processes the response sent from the server.
     * <p>
     * This method runs in the client.
     * @param response the response of this request
     */
    abstract public void processResponse(ResponseMessage response);

    /**
     * Returns if this message was sent more than 4 seconds ago.
     * @return {@code true} if 4 seconds are passed, {@code false} otherwise
     */
    public boolean isExpired(){
        long timePassed = System.currentTimeMillis() - whenSent;
        long expireTime = 4000;
        return timePassed > expireTime;
    }
}
