package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * A message sent from the server to all the client connected to a game to indicate
 * the island on which mother nature is
 */
public class MotherNatureMoved extends ServerCommandNetMsg {

    /**
     * The island on which mother nature is.
     */
    private final int position;

    /**
     * Creates a new message to comunicate that mother nature has moved
     * @param newPosition the new position of mother nature
     */
    public MotherNatureMoved(int newPosition) {
        this.position = newPosition;
    }


    @Override
    public void processMessage(ClientView client) {
        client.motherNaturePositionChanged(position);
    }

}
