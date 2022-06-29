package it.polimi.ingsw.server.observers.game.table.island;

/**
 * An observer for all the changes of an island that need to be notified to the client
 */
public interface IslandObserver extends StudentsOnIslandObserver, TowerOnIslandObserver, IslandUnificationObserver{
}
