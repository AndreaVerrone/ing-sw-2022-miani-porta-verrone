package it.polimi.ingsw.network.messages.clienttoserver.matchmaking;

import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidOperationException;

/**
 * A message sent from the client to the server to move to the next phase of the game
 */
public class NextPhase extends ClientCommandNetMsg {
    @Override
    protected void normalProcess(ClientHandler clientInServer) throws NotValidOperationException {
        clientInServer.getSessionController().next();
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }
}
