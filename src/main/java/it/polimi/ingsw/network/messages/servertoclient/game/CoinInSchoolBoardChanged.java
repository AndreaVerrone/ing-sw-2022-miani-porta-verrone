package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * A message sent from server to all client connected to a game to indicate that the coins
 * in the dining room has been changed.
 */
public class CoinInSchoolBoardChanged extends ServerCommandNetMsg {

    /**
     * the player that has the school board on which the change has been happened.
     */
    private final String player;

    /**
     * the actual num of coins in the school board.
     */
    private final int actualNumOfCoins;

    /**
     * the constructor of the class.
     * @param player the player that has the school board on which the change has been happened.
     * @param actualNumOfCoins the actual num of coins in the school board.
     */
    public CoinInSchoolBoardChanged(String player, int actualNumOfCoins) {
        this.player = player;
        this.actualNumOfCoins = actualNumOfCoins;
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
        client.coinNumberOfPlayerChanged(player,actualNumOfCoins);
    }
}
