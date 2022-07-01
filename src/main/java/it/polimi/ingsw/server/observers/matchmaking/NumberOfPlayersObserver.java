package it.polimi.ingsw.server.observers.matchmaking;

/**
 * Interface to implement the observer pattern.
 */
public interface NumberOfPlayersObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param numberOfPlayers number of players of the match
     */
    void numberOfPlayersObserverUpdate(int numberOfPlayers);
}