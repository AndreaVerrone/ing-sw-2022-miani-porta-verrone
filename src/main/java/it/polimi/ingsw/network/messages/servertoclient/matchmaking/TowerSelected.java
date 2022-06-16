package it.polimi.ingsw.network.messages.servertoclient.matchmaking;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.utils.TowerType;

/**
 * A message sent from the server to the client to notify that the player has selected a tower.
 */
public class TowerSelected extends ServerCommandNetMsg {

    /**
     * Nickname of the player that selected the tower
     */
    String nickname;

    /**
     * Tower selected by the player
     */
    TowerType tower;

    /**
     * Creates a new message to inform all the players in a game that the player has selected a tower.
     * @param nickname of the player that selected the tower
     * @param tower tower selected by the player
     */
    public TowerSelected(String nickname, TowerType tower){
        this.nickname = nickname;
        this.tower = tower;
    }

    @Override
    public void processMessage(ClientController client) {
        client.towerChanged(nickname, tower);
    }
}
