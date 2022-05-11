package it.polimi.ingsw.network.messages.servertoclient;

import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;

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
    public void processMessage(ConnectionHandler client) {
        client.sendMessage(ResponseMessage.newSuccess(this));
        // TODO: 11/05/2022 handle change and show in view
    }

    @Override
    public void processResponse(ResponseMessage response) {

    }
}
