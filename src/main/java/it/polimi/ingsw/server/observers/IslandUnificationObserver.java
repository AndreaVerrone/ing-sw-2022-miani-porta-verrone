package it.polimi.ingsw.server.observers;

/**
 * Interface to implement the observer pattern.
 */
public interface IslandUnificationObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param islandID the ID of the island kept
     * @param islandRemovedID ID of the island that has been removed
     * @param sizeIslandRemoved the size of the island to remove
     */
    void islandUnificationObserverUpdate(int islandID, int islandRemovedID, int sizeIslandRemoved);
}
