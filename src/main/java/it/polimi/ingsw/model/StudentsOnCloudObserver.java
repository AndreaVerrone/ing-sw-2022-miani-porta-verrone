package it.polimi.ingsw.model;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsOnCloudObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     */
    public void studentsOnCloudObserverUpdate();
}
