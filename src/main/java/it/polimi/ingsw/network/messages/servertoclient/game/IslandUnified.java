package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * A message sent from server to all client connected to a game to indicate
 * the unification of the islands
 */
public class IslandUnified extends ServerCommandNetMsg {

    /**
     * the ID of the island to keep.
     */
    private final int IDIslandToKeep;

    /**
     * the ID of the island to remove.
     */
    private final int IDIslandRemoved;

    /**
     * the size of the island to remove.
     */
    private final int sizeIslandRemoved;

    /**
     * the constructor of the class
     * @param idIslandToKeep the ID of the island to keep
     * @param idIslandRemoved the ID of the island to remove
     * @param sizeIslandRemoved the size of the island to remove
     */
    public IslandUnified(int idIslandToKeep, int idIslandRemoved, int sizeIslandRemoved) {
        IDIslandToKeep = idIslandToKeep;
        IDIslandRemoved = idIslandRemoved;
        this.sizeIslandRemoved = sizeIslandRemoved;
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
        client.islandUnification(IDIslandToKeep,IDIslandRemoved,sizeIslandRemoved);
    }
}
