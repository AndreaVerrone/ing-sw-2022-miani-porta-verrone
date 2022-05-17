package it.polimi.ingsw.network.messages.clienttoserver.launcher;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A message sent from client to server when a user wants to resume a game he was playing
 */
public class ResumeGame extends ClientCommandNetMsg {

    @Override
    protected void normalProcess(ClientHandler clientInServer) throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().resumeGame();
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }

    @Override
    public void processResponse(ResponseMessage response) {
        // TODO: 12/05/2022 show response in view
    }
}
