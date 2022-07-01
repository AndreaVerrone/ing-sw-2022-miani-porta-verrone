package it.polimi.ingsw.server.observers.game.table;

/**
 * Interface to implement the observer pattern.
 */
public interface EmptyStudentBagObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     */
    void emptyStudentBagObserverUpdate();

}
