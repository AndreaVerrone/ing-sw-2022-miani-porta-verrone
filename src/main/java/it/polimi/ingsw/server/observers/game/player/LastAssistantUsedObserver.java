package it.polimi.ingsw.server.observers.game.player;

import it.polimi.ingsw.server.model.player.Assistant;

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
    void lastAssistantUsedObserverUpdate(String nickName, Assistant actualLastAssistant);
}
