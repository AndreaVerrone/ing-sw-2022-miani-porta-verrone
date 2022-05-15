package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Assistant;

import java.util.Collection;

/**
 * Interface to implement the observer pattern.
 */
public interface ChangeAssistantDeckObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param nickName the nickname of the player that has the school board on which the changes have been happened
     * @param actualDeck the actual deck
     */
    public void changeAssistantDeckObserverUpdate(String nickName, Collection<Assistant> actualDeck);
}
