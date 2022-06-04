package it.polimi.ingsw.network.messages.clienttoserver.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ErrorCode;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.model.player.Assistant;

/**
 * A message sent from the client to the server to indicate which assistant to use
 */
public class UseAssistant extends ClientCommandNetMsg {

    /**
     * The assistant chosen
     */
    private final Assistant assistant;

    /**
     * Creates a new message to tell the server which assistant is chosen by the player to be used.
     *
     * @param assistant the assistant chosen
     */
    public UseAssistant(Assistant assistant) {
        this.assistant = assistant;
    }

    @Override
    protected void normalProcess(ClientHandler clientInServer) throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().useAssistant(assistant);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }

    @Override
    public void processResponse(ResponseMessage response, ClientController clientController) {

        if (response.isSuccess()) {
            //TODO: notify the view of the success
            return;
        }

        ErrorCode errorCode = response.getErrorCode();
        // clientController.displayErrorMessage(Translator.getErrorMessage(errorCode));
        // todo: remove comment after merge
    }
}


