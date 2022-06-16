package it.polimi.ingsw.network.messages.clienttoserver.matchmaking;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;

/**
 * A message sent from client to server to comunicate the will of the player to leave the game
 */
public class ExitFromGame extends ClientCommandNetMsg {

    /**
     * The nickname of the user making this request
     */
    private final String nickname;

    /**
     * Creates a new request to the server to exit the game a player is currently in.
     *
     * @param nickname the nickname of the player
     */
    public ExitFromGame(String nickname) {
        this.nickname = nickname;
    }


    @Override
    protected void normalProcess(ClientHandler clientInServer)
            throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().exitFromGame(nickname);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }
}
