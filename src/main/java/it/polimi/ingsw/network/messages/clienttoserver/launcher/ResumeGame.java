package it.polimi.ingsw.network.messages.clienttoserver.launcher;

import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;

/**
 * A message sent from client to server when a user wants to resume a game he was playing
 */
public class ResumeGame extends ClientCommandNetMsg {

    @Override
    protected void normalProcess(ClientHandler clientInServer) throws NotValidArgumentException {
        clientInServer.getSessionController().resumeGame();
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }
}
