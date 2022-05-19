package it.polimi.ingsw.server.controller;

/**
 * Interface to implement the observer pattern.
 */
public interface CostOfCharacterCardObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param characterCardsType the card type on which the change has been happened
     * @param actualCost the actual value of the cost for the usage of the card
     */
    public void costOfCharacterCardObserverUpdate(CharacterCardsType characterCardsType, int actualCost);

}
