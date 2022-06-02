package it.polimi.ingsw.server.controller;

/**
 * Class to implement the observer used when the state of the matchmaking or of the game has changed
 */
public class ChangeCurrentState implements  ChangeCurrentStateObserver{

    /**
     * @see Match
     */
    private final Match match;

    /**
     * Constructor of the class
     * @param match Match of the game
     */
    public ChangeCurrentState(Match match){
        this.match = match;
    }

    @Override
    public void changeCurrentStateObserverUpdate(StateType stateType) {
        match.notifyChangeCurrentState(stateType);
    }
}
