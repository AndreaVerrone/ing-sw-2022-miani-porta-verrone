package it.polimi.ingsw.network.messages.servertoclient.matchmaking;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * A message sent from the server to the client to communicate
 * the changing of the number of players requested to begin a game.
 */
public class NumPlayersChanged extends ServerCommandNetMsg {

    /**
     * The new number of players requested in the game
     */
    private final int newNumOfPlayers;

    /**
     * Creates a new message to communicate the changing of the number of players requested to begin a game.
     * @param newNumOfPlayers the new number of players requested
     */
    public NumPlayersChanged(int newNumOfPlayers) {
        this.newNumOfPlayers = newNumOfPlayers;
    }

    @Override
    public void processMessage(ClientController client) {
        client.numPlayersChanged(newNumOfPlayers);
    }

}
