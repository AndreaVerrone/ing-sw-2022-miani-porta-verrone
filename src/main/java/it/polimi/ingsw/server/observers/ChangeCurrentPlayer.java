package it.polimi.ingsw.server.observers;

import it.polimi.ingsw.server.controller.Match;

/**
 * Class that implements the observer used when the current player of matchmaking or of the game has changed
 */
public class ChangeCurrentPlayer implements ChangeCurrentPlayerObserver{

    /**
     * @see Match
     */
    private final Match match;

    /**
     * Constructor of the class
     * @param match Match of the game
     */
    public ChangeCurrentPlayer(Match match){
        this.match = match;
    }

    @Override
    public void changeCurrentPlayerObserverUpdate(String actualCurrentPlayerNickname) {
        match.notifyChangeCurrentPlayer(actualCurrentPlayerNickname);
    }
}
