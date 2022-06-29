package it.polimi.ingsw.server.observers.game.table;

public interface ChangeCoinNumberInBagObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param actualNumOfCoins the actual number of coins in the bag
     */
    void changeCoinNumberInBagObserverUpdate(int actualNumOfCoins);
}
