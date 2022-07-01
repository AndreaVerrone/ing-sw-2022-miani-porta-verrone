package it.polimi.ingsw.server.observers.game;

import java.util.Collection;

/**
 * Interface to implement the observer pattern.
 */
public interface EndOfGameObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     */
    void endOfGameObserverUpdate(Collection<String> winners);
}
