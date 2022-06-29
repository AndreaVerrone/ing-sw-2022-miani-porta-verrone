package it.polimi.ingsw.server.observers.game.table.island;

import it.polimi.ingsw.server.model.utils.TowerType;

/**
 * Interface to implement the observer pattern.
 */
public interface TowerOnIslandObserver {

    /**
     * Notifies that the tower on an island have been changed
     * @param islandIDWithChange the id of the island
     * @param actualTower the actual tower on the island
     */
    void towerOnIslandObserverUpdate(int islandIDWithChange, TowerType actualTower );
}
