package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.observers.ChangeCoinNumberInBagObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * A class describing the 20 coins in the game.
 */
public class CoinsBag {

    /**
     * The maximum amount of coins that can exist in a game.
     */
    private final int MAX_COINS = 20;

    /**
     * The number of coins available on the table.
     * The starting value is 20.
     */
    private int coinsAvailable = MAX_COINS;

    public int getCoinsAvailable() {
        return coinsAvailable;
    }

    /**
     * Takes a coin from this bag.
     */
    public void takeCoin() {
        if (coinsAvailable <= 0) {
            return;
        }
        coinsAvailable --;
        notifyChangeCoinNumberInBagObservers(this.coinsAvailable);
    }

    /**
     * Adds {@code coins} in this bag.
     * <p>
     * The {@code coins} can't be negative and the number of coins in this bag can't exceed 20.
     * @param coins the number of coins to add
     */
    public void addCoins(int coins){
        assert coins > 0 : "You can't add a negative value of coins";
        assert coinsAvailable + coins <= MAX_COINS : "The coins added are too much";

        coinsAvailable += coins;
        notifyChangeCoinNumberInBagObservers(this.coinsAvailable);
    }

    // MANAGEMENT OF OBSERVERS ON COINS IN THE COINS BAG
    /**
     * List of the observer on the coin number in coins bag.
     */
    private final List<ChangeCoinNumberInBagObserver> changeCoinNumberInBagObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the coin number in coins bag.
     * @param observer the observer to be added
     */
    public void addChangeCoinNumberInBagObserver(ChangeCoinNumberInBagObserver observer){
        changeCoinNumberInBagObservers.add(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on the coin number in coins bag.
     * @param actualNumOfCoins the actual number of coins in the bag
     */
    private void notifyChangeCoinNumberInBagObservers(int actualNumOfCoins){
        for(ChangeCoinNumberInBagObserver observer : changeCoinNumberInBagObservers)
            observer.changeCoinNumberInBagObserverUpdate(actualNumOfCoins);
    }
}
