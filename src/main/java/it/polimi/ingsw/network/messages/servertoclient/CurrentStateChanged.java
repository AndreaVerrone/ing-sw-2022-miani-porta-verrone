package it.polimi.ingsw.network.messages.servertoclient;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.server.controller.StateType;

/**
 * A message sent from the server to all clients connected to a game to indicate that the current state of the game
 * or of the matchmaking phase changed.
 */
public class CurrentStateChanged extends ServerCommandNetMsg{

    /**
     * New current state of the game or matchmaking
     */
    private final StateType newState;

    /**
     * Creates a new message to indicate that the current state of the game or of the matchmaking phase changed.
     * @param newState type of the new current state
     */
    public CurrentStateChanged(StateType newState){
        this.newState = newState;
    }

    @Override
    public void processMessage(ClientController client) {
        //TODO: 27/05/2022 handle change and show in view
    }
}
