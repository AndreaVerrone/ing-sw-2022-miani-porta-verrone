package it.polimi.ingsw.server.controller.matchmaking.observers;

import it.polimi.ingsw.server.controller.Match;

/**
 * Class to implement the observer used when the number of player for the game is selected
 */
public class NumberOfPlayers implements NumberOfPlayersObserver{

    /**
     * @see Match;
     */
    private final Match match;

    /**
     * Constructor of the class
     * @param match Match of the game
     */
    public NumberOfPlayers(Match match){
        this.match = match;
    }

    @Override
    public void numberOfPlayersUpdate(int numberOfPlayers) {
        match.notifyNumberOfPlayers(numberOfPlayers);
    }
}
