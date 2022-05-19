package it.polimi.ingsw.server.model;

/**
 * Interface to implement the observer pattern.
 */
public interface ConquerIslandObserver {

    /**
     * This method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     */
    public void conquerIslandObserverUpdate();

}