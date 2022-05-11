package it.polimi.ingsw.messages.clienttoserver.game;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.messages.clienttoserver.matchmaking.ExitFromGame;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A message sent from client to server to exit a game regardless of the current state.
 * For a more controlled and gracefully way of exiting a game, see {@link ExitFromGame}
 */
public class QuitGame extends ClientCommandNetMsg {
    @Override
    protected void normalProcess(ClientHandler clientInServer) throws NotValidArgumentException, NotValidOperationException {
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
        clientInServer.getSessionController().quitGame();
    }

    @Override
    public void processResponse(ResponseMessage response) {
        // TODO: 11/05/2022 show the exit and update view
    }
}
