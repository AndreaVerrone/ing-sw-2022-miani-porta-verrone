package it.polimi.ingsw.model;

/**
 * Interface to implement the observer pattern.
 */
public interface IslandNumberObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param actualNumOfIslands the actual number of island on the game table
     */
    public void islandNumberObserverUpdate(int actualNumOfIslands);

}
