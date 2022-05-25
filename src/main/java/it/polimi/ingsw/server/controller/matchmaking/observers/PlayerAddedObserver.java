package it.polimi.ingsw.server.controller.matchmaking.observers;

/**
 * Interface to implement the observer pattern.
 */
public interface PlayerAddedObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param nickname nickname of the player added
     */
    void playerAddedObserverUpdate(String nickname);
}
