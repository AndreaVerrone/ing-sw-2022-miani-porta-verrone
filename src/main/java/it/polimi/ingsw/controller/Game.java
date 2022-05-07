package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.Assistant;

import java.util.Collection;


/**
 *A class to handle the various states of the game.It can change the current state and can call operations on it.
 */
public class Game implements IGame{
    /**
     * State in which the player is playing an assistant card
     */
    private State playAssistantState;
    /**
     * State in which the player moves a student from his entrance to an island or his dining room
     */
    private State moveStudentState;
    /**
     * State in which the player is moving mother nature
     */
    private State moveMotherNatureState;
    /**
     * State in which the player is choosing the island from where gets the students
     */
    private State chooseCloudState;
    /**
     * State for the end of the game
     */
    private State endState;
    /**
     * Current state of the game
     */
    private State state;
    /**
     * Model of the game
     */
    private final GameModel model;
    /**
     * If this flag is true the game is in its last round
     */
    private boolean lastRoundFlag = false;

    public Game(Collection<PlayerLoginInfo> players){
        //TODO: create all states and add documentation
        model = new GameModel(players);
        state = playAssistantState;
    }

    /**
     * Changes the current state of the game
     * @param newState new state of the game
     */
    protected void setState(State newState){
        state = newState;
    }

    /**
     * Sets the {@code lastRoundFlag} to true
     */
    protected void setLastRoundFlag(){ lastRoundFlag = true;}

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void useAssistant(Assistant assistant) throws NotValidOperationException, NotValidArgumentException {
        state.useAssistant(assistant);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void moveStudentToIsland(PawnType student, int islandID) throws NotValidOperationException, NotValidArgumentException {
        state.moveStudentToIsland(student, islandID);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void moveStudentToDiningRoom(PawnType student) throws NotValidOperationException, NotValidArgumentException {
        state.moveStudentToDiningRoom(student);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void moveMotherNature(int positions) throws NotValidOperationException, NotValidArgumentException {
        state.moveMotherNature(positions);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void takeFromCloud(int cloudID) throws NotValidOperationException, NotValidArgumentException {
        state.takeFromCloud(cloudID);
    }

    protected GameModel getModel() {
        return model;
    }

    protected boolean getLastRoundFlag(){ return lastRoundFlag;}

    protected State getState() {
        return state;
    }

    protected State getPlayAssistantState() {
        return playAssistantState;
    }

    protected State getMoveStudentState() {
        return moveStudentState;
    }

    protected State getMoveMotherNatureState() {
        return moveMotherNatureState;
    }

    protected State getChooseCloudState() {
        return chooseCloudState;
    }

    protected State getEndState() {
        return endState;
    }

    //TODO: setters for all states if needed for characters cards
}
