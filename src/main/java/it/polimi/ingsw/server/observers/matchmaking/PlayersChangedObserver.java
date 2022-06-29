package it.polimi.ingsw.server.observers.matchmaking;

import it.polimi.ingsw.server.controller.PlayerLoginInfo;

import java.util.Collection;

/**
 * Interface to implement the observer pattern.
 */
public interface PlayersChangedObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param players collection with all the nicknames of the players in the match
     */
    void playersChangedObserverUpdate(Collection<PlayerLoginInfo> players);
}
