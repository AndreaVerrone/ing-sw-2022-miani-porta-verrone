package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A message sent from the client to the server to indicate which assistant to use
 */
public class UseAssistant extends ClientCommandNetMsg{

    /**
     * The assistant chosen
     */
    private final Assistant assistant;

    /**
     * Creates a new message to tell the server which assistant is chosen by the player to be used.
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
    public void processResponse(ResponseMessage response) {
        // TODO: 11/05/2022 handle response in client
    }
}
