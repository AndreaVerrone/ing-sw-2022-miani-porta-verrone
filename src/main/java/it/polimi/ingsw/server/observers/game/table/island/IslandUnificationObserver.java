package it.polimi.ingsw.server.observers.game.table.island;

/**
 * Interface to implement the observer pattern.
 */
public interface IslandUnificationObserver {

    /**
     * Notifies that two islands have been unified, specifying which island is kept in the game,
     * which has been removed and what was its size.
     *
     * @param islandID the ID of the island kept
     * @param islandRemovedID ID of the island that has been removed
     * @param sizeIslandRemoved the size of the island to remove
     */
    void islandUnificationObserverUpdate(int islandID, int islandRemovedID, int sizeIslandRemoved);
}
