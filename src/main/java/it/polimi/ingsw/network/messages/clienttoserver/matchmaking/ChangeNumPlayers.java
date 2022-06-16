package it.polimi.ingsw.network.messages.clienttoserver.matchmaking;

import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;

/**
 * A message sent from client to the server to communicate the will to change the number of players of a game
 */
public class ChangeNumPlayers extends ClientCommandNetMsg {

    /**
     * The new number of players requested.
     */
    private final int newNumPlayers;

    /**
     * Creates a new message to change the necessary number of players in a game.
     *
     * @param newNumPlayers the new number of players requested
     */
    public ChangeNumPlayers(int newNumPlayers) {
        this.newNumPlayers = newNumPlayers;
    }

    @Override
    protected void normalProcess(ClientHandler clientInServer)
            throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().changeNumOfPlayers(newNumPlayers);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }
}
