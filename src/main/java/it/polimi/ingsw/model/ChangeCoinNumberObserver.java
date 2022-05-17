package it.polimi.ingsw.model;

/**
 * Interface to implement the observer pattern.
 */
public interface ChangeCoinNumberObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param nickNameOfPlayer the nickname of the player associated to this school board
     * @param actualNumOfCoins the actual num of coins in the school board
     */
    public void changeCoinNumberObserverUpdate(String nickNameOfPlayer, int actualNumOfCoins);
}
