package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * A message sent from server to all client connected to a game to indicate that
 * the number of bans on an island has been changed.
 */
public class BanOnIslandChanged extends ServerCommandNetMsg {

    /**
     * the ID of the island on which the change has been happened.
     */
    private final int ID;

    /**
     * the actual number of bans on the island.
     */
    private final int actualNumOfBans;

    /**
     * the constructor of the class
     * @param ID the ID of the island on which the change has been happened
     * @param actualNumOfBans the actual number of bans on the island
     */
    public BanOnIslandChanged(int ID, int actualNumOfBans) {
        this.ID = ID;
        this.actualNumOfBans = actualNumOfBans;
    }

    /**
     * A method used to process this message.
     * <p>
     * This method runs in the client.
     *
     * @param client the client that receives this message
     */
    @Override
    public void processMessage(ClientController client) {
        client.updateBanOnIsland(ID,actualNumOfBans);
    }
}
