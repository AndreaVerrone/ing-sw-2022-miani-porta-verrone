package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.PawnType;

import java.util.Collection;
import java.util.Collections;


/**
 *A class to handle the various states of the game.It can change the current state and can call operations on it.
 */
public class Game implements IGame{
    /**
     * State in which the player is playing an assistant card
     */
    private final State playAssistantState;
    /**
     * State in which the player moves a student from his entrance to an island or his dining room
     */
    private final State moveStudentState;
    /**
     * State in which the player is moving mother nature
     */
    private final State moveMotherNatureState;
    /**
     * State in which the player is choosing the island from where gets the students
     */
    private final State chooseCloudState;
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

    @Override
    public String getCurrentPlayerNickname() {
        return model.getCurrentPlayer().getNickname();
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
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition)throws NotValidOperationException, NotValidArgumentException{
        state.choseStudentFromLocation(color,originPosition);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void chooseDestination(Position destination)throws NotValidOperationException,NotValidArgumentException{
        state.chooseDestination(destination);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
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
