package it.polimi.ingsw.server.controller.matchmaking.observers;

import it.polimi.ingsw.server.controller.Match;
import it.polimi.ingsw.server.model.utils.TowerType;

/**
 * Class to implement the observer for tower selection
 */
public class TowerSelected implements TowerSelectedObserver{

    /**
     * @see Match
     */
    private  final Match match;

    /**
     * Constructor of the class
     * @param match Match of the game
     */
    public TowerSelected(Match match){
        this.match = match;
    }

    @Override
    public void towerSelectedObserverUpdate(String player, TowerType tower) {
        match.notifyTowerSelected(player, tower);
    }
}
