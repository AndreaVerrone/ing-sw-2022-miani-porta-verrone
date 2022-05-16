package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.NotEnoughCoinsException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


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
 
    /**
     * List of winners of the game.If the list has more than one player it is considered a draw
     */
    private Collection<Player> winners = null;

    public Game(Collection<PlayerLoginInfo> players){
        //TODO: create all states and add documentation
        model = new GameModel(players);

        playAssistantState = new PlayAssistantState(this);
        moveStudentState = new MoveStudentState(this);
        moveMotherNatureState = new MoveMotherNatureState(this);
        chooseCloudState = new ChooseCloudState(this);

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
     * Set the winners of the game
     * @param winners players that have won. If more than one is considered a draw
     */
    protected void setWinner(Collection<Player> winners){
        this.winners = winners;
        //TODO: update observer
    }

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


    /**
     * This method allows to select a student (of the PawnType specified in the parameter) that comes from the position
     * (also specified in the parameters).
     * @param color the {@code PawnType} of the student
     * @param originPosition the {@code Position} from where take the student
     * @throws NotValidOperationException if the position is not the one that was supposed to be in the considered state
     * @throws NotValidArgumentException if the student is not present in the specified location
     */
    public void choseStudentFromLocation(PawnType color, Position originPosition)throws NotValidOperationException, NotValidArgumentException{
        state.choseStudentFromLocation(color,originPosition);
    }

    /**
     * This method allows to choose a destination on which operate based on the state.
     * @param destination the Position
     * @throws NotValidOperationException if the position is not the one that was supposed to be in the considered state
     * @throws NotValidArgumentException if the
     */
    public void chooseDestination(Position destination)throws NotValidOperationException,NotValidArgumentException{
        state.chooseDestination(destination);
    }

    /**
     * This method allow to use the character card of the type passed as a parameter.
     * @param cardType type of card chosen
     * @throws NotValidOperationException if a player tries to use a card in basic mode
     */
    public void useCharacterCard(CharacterCardsType cardType) throws NotValidOperationException, NotValidArgumentException {
        throw new NotValidOperationException("Cannot use cards in basic mode!");
    }


    /**
     * Does the reset operations at the end of every turn
     */
    public void endOfTurn(){
        //Nothing to do in basic mode
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

    protected Collection<Player> getWinner(){return Collections.unmodifiableCollection(winners);}

    //TODO: setters for all states if needed for characters cards
}
