package it.polimi.ingsw.server.observers;

import it.polimi.ingsw.server.model.TowerType;

/**
 * Interface to implement the observer pattern.
 */
public interface TowerOnIslandObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param islandIDWithChange the island on which a tower has been put or removed
     * @param actualTower the actual tower on the island
     */
    public void towerOnIslandObserverUpdate(int islandIDWithChange, TowerType actualTower );
}
