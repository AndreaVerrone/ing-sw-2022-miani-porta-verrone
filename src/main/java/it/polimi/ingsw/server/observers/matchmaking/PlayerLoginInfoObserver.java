package it.polimi.ingsw.server.observers.matchmaking;

import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

/**
 * An observer for all the changes of a PlayerLoginInfo that needs to be notified to the client
 */
public interface PlayerLoginInfoObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param tower tower type selected by the player
     * @param player nickname of player that selected the tower
     */
    void towerSelectedObserverUpdate(String player, TowerType tower);

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     *
     * @param wizard wizard selected by the player
     * @param player nickname of the player that selected the wizard
     */
    void wizardSelectedObserverUpdate(String player, Wizard wizard);
}
