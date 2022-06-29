package it.polimi.ingsw.server.observers.game.player;

import it.polimi.ingsw.server.model.player.Assistant;

import java.util.Collection;

/**
 * Interface to implement the observer pattern.
 */
public interface ChangeAssistantDeckObserver {

    /**
     * Notifies that the assistant deck of a player has changed
     *
     * @param nickName the nickname of the player
     * @param actualDeck the actual deck
     */
    void changeAssistantDeckObserverUpdate(String nickName, Collection<Assistant> actualDeck);
}
