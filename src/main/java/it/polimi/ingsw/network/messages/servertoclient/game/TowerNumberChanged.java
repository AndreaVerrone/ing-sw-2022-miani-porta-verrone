package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

public class TowerNumberChanged extends ServerCommandNetMsg {

    /**
     * the player that has the school board on which the change has been happened.
     */
    private final String player;

    /**
     * the actual num of towers in the school board.
     */
    private final int actualNumOfTowers;

    /**
     * the constructor of the class
     * @param player the player that has the school board on which the change has been happened.
     * @param actualNumOfTowers the actual num of towers in the school board.
     */
    public TowerNumberChanged(String player, int actualNumOfTowers) {
        this.player = player;
        this.actualNumOfTowers = actualNumOfTowers;
    }

    /**
     * A method used to process this message.
     * <p>
     * This method runs in the client.
     *
     * @param client the client that receives this message
     */
    @Override
    public void processMessage(ClientView client) {
        client.towerNumberOfPlayerChanged(player,actualNumOfTowers);
    }
}
