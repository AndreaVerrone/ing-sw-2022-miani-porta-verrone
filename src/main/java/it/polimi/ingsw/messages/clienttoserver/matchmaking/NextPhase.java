package it.polimi.ingsw.messages.clienttoserver.matchmaking;

import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A message sent from the client to the server to move to the next phase of the game
 */
public class NextPhase extends ClientCommandNetMsg {
    @Override
    protected void normalProcess(ClientHandler clientInServer) throws NotValidOperationException {
        clientInServer.getSessionController().next();
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }

    @Override
    public void processResponse(ResponseMessage response) {
        // TODO: 11/05/2022 handle response in client
    }
}
