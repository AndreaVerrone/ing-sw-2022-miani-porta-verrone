package it.polimi.ingsw.server.controller.matchmaking.observers;

import it.polimi.ingsw.server.controller.Match;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;

import java.util.Collection;

/**
 * Class to implement the observer used when the player in a game have changed
 */
public class PlayersChanged implements PlayersChangedObserver{

    /**
     * @see Match
     */
    private final Match match;

    /**
     * Constructor of the class
     * @param match Match of the game
     */
    public PlayersChanged(Match match){
        this.match = match;
    }

    @Override
    public void playersChangedObserverUpdate(Collection<PlayerLoginInfo> players) {
        match.notifyPlayersChanged(players);
    }
}
