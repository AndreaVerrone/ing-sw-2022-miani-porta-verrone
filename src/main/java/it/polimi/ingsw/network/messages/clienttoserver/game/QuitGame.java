package it.polimi.ingsw.network.messages.clienttoserver.game;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.clienttoserver.matchmaking.ExitFromGame;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A message sent from client to server to exit a game regardless of the current state.
 * For a more controlled and gracefully way of exiting a game, see {@link ExitFromGame}
 */
public class QuitGame extends ClientCommandNetMsg {
    @Override
    protected void normalProcess(ClientHandler clientInServer) {
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
        clientInServer.getSessionController().quitGame();
    }

    @Override
    public void processResponse(ResponseMessage response, ClientView clientView) {
        clientView.getScreenBuilder().build(ScreenBuilder.Screen.HOME);
    }
}
