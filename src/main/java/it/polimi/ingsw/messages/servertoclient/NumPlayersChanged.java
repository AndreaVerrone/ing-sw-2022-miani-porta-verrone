package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.messages.responses.ResponseMessage;

/**
 * A message sent from the server to the client to communicate
 * the changing of the number of players requested to begin a game.
 */
public class NumPlayersChanged extends ServerCommandNetMsg{

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
    public void processMessage(ConnectionHandler client) {
        client.sendMessage(ResponseMessage.newSuccess(this));
        // TODO: 11/05/2022 update the view of the client
    }

    @Override
    public void processResponse(ResponseMessage response) {

    }
}
