package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.TowerType;

/**
 * A message sent from the server to all clients connected to a game
 * to indicate that the tower on an island has changed
 */
public class TowerOnIslandChanged extends ServerCommandNetMsg {

    /**
     * The new tower on the island
     */
    private final TowerType newTower;

    /**
     * The ID of the island
     */
    private final int IslandID;

    /**
     * Creates a new message to indicate that the tower on an island changed
     * @param newTower the new tower on the island
     * @param islandID the ID of the island
     */
    public TowerOnIslandChanged(TowerType newTower, int islandID) {
        this.newTower = newTower;
        IslandID = islandID;
    }

    @Override
    public void processMessage(ConnectionHandler client) {
        client.sendMessage(ResponseMessage.newSuccess(this));
        // TODO: 11/05/2022 update view
    }

}
