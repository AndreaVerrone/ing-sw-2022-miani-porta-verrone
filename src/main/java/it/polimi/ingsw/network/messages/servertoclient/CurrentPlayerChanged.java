package it.polimi.ingsw.network.messages.servertoclient;

import it.polimi.ingsw.client.ClientController;

/**
 * A message sent from the server to all clients connected to a game to indicate that the current player changed
 */
public class CurrentPlayerChanged extends ServerCommandNetMsg{

    /**
     * The nickname of the new current player
     */
    private final String currentPlayer;

    /**
     * Creates a new message to indicate that the current player changed.
     * @param currentPlayer the nickname of the new current player
     */
    public CurrentPlayerChanged(String currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void processMessage(ClientController client) {
        client.currentPlayerChanged(currentPlayer);
    }

}
