package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

public class CoinNumberOfPlayerChanged extends ServerCommandNetMsg {

    /**
     * Nickname of the player whose coin number has changed
     */
    private final String nickname;

    /**
     * New number of coins of the player
     */
    private final int numberOfCoins;

    /**
     * Creates a new message to inform all the players in a game that the number of coins of a player has changed.
     * @param nickname of the player whose number of coins has changed
     * @param numberOfCoins new number of coins of the player
     */
    public CoinNumberOfPlayerChanged(String nickname, int numberOfCoins){
        this.nickname = nickname;
        this.numberOfCoins = numberOfCoins;
    }

    @Override
    public void processMessage(ClientController client) {
        //TODO: update view
    }
}
