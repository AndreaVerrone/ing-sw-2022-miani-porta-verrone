package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * A message sent from server to all client connected to a game to indicate that the number of coins in the bag has changed.
 */
public class CoinNumberInBagChanged extends ServerCommandNetMsg {

    /**
     * Number of coins in the bag
     */
    private final int numberOfCoinsInBag;

    /**
     * Creates a new message to indicate that the number of coins in the bag has changed.
     * @param numberOfCoinsInBag new number of coins  in the bag
     */
    public CoinNumberInBagChanged(int numberOfCoinsInBag){
        this.numberOfCoinsInBag = numberOfCoinsInBag;
    }

    @Override
    public void processMessage(ClientController client) {
        //TODO; update view
    }
}
