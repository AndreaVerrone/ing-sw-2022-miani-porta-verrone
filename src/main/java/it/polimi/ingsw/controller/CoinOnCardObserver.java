package it.polimi.ingsw.controller;

/**
 * Interface to implement the observer pattern.
 */
public interface CoinOnCardObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param characterCard the card on which the change has been happened
     * @param coinOnCard the actual value (true, if the coin is present, false otherwise)
     */
    public void coinOnCardObserverUpdate(CharacterCard characterCard, boolean coinOnCard);
}
