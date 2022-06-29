package it.polimi.ingsw.server.observers.game.player;

import it.polimi.ingsw.server.model.player.Assistant;

/**
 * Interface to implement the observer pattern.
 */
public interface LastAssistantUsedObserver {

    /**
     * Notifies that the last assistant used by a player has changed.
     * @param nickName the nickname of the player
     * @param actualLastAssistant the actual last assistant
     */
    void lastAssistantUsedObserverUpdate(String nickName, Assistant actualLastAssistant);
}
