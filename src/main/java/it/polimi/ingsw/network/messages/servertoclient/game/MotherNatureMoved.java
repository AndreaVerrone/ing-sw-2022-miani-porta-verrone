package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * A message sent from the server to all the client connected to a game to indicate that mother nature
 * has moved from its previous position
 */
public class MotherNatureMoved extends ServerCommandNetMsg {

    /**
     * The number of movement mother nature has done
     */
    private final int movement;

    /**
     * Creates a new message to comunicate that mother nature has moved
     * @param movement the number of movement
     */
    public MotherNatureMoved(int movement) {
        this.movement = movement;
    }


    @Override
    public void processMessage(ClientController client) {
        // TODO: 11/05/2022 update view
    }

}
