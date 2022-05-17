package it.polimi.ingsw.model;

import it.polimi.ingsw.model.player.Assistant;

/**
 * Interface to implement the observer pattern.
 */
public interface LastAssistantUsedObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param nickName the nickname of the player that has the deck that has been changed
     * @param actualLastAssistant the actual last assistant
     */
    public void lastAssistantUsedObserverUpdate(String nickName, Assistant actualLastAssistant);
}
