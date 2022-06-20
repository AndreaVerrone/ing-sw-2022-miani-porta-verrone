package it.polimi.ingsw.network.messages.clienttoserver;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.network.messages.NetworkMessage;
import it.polimi.ingsw.network.messages.responses.ErrorCode;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.network.messages.responses.Result;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;

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
    protected ClientCommandNetMsg() {
        whenSent = System.currentTimeMillis();
    }

    /**
     * A method used to process this message.
     * <p>
     * This method runs in the server.
     *
     * @param clientInServer the clientInServer that sent this message
     */
    public void processMessage(ClientHandler clientInServer) {
        try {
            normalProcess(clientInServer);
        } catch (NotValidOperationException e) {
            clientInServer.sendMessage(
                    new ResponseMessage(this, Result.FAILURE, e.getErrorCode()));
        } catch (NotValidArgumentException e) {
            clientInServer.sendMessage(
                    new ResponseMessage(this, Result.FAILURE, e.getErrorCode()));
        }
    }

    /**
     * The behaviour of this message in the server if everything is fine.
     *
     * @param clientInServer the clientInServer that sent this message
     * @throws NotValidArgumentException  if something is wrong with the argument of the message
     * @throws NotValidOperationException if the message was sent in an invalid moment
     */
    abstract protected void normalProcess(ClientHandler clientInServer)
            throws NotValidArgumentException, NotValidOperationException;

    /**
     * Processes the response sent from the server.
     * <p>
     * This method runs in the client.
     *
     * @param response the response of this request
     * @param clientView the view of the client
     */
    public void processResponse(ResponseMessage response, ClientView clientView){
        // if there is an error
        if (!response.isSuccess()) {
            // get the error code and print it
            ErrorCode errorCode = response.getErrorCode();
            clientView.displayErrorMessage(Translator.getErrorMessage(errorCode));
        }
    }

    /**
     * Returns if this message was sent more than 4 seconds ago.
     *
     * @return {@code true} if 4 seconds are passed, {@code false} otherwise
     */
    public boolean isExpired() {
        long timePassed = System.currentTimeMillis() - whenSent;
        long expireTime = 4000;
        return timePassed > expireTime;
    }
}
