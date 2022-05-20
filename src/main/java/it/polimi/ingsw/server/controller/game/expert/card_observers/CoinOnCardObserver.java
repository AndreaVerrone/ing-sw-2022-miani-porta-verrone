package it.polimi.ingsw.server.controller.game.expert.card_observers;

import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;

/**
 * Interface to implement the observer pattern.
 */
public interface CoinOnCardObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param characterCardsType the card type on which the change has been happened
     * @param coinOnCard the actual value (true, if the coin is present, false otherwise)
     */
    void coinOnCardObserverUpdate(CharacterCardsType characterCardsType, boolean coinOnCard);
}
