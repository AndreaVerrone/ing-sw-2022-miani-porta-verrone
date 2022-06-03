package it.polimi.ingsw.server.controller.game;

import it.polimi.ingsw.server.controller.ChangeCurrentStateObserver;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.controller.game.states.*;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.PawnType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;


/**
 *A class to handle the various states of the game.It can change the current state and can call operations on it.
 */
public class Game {
    /**
     * State in which the player is playing an assistant card
     */
    private final GameState playAssistantState;
    /**
     * State in which the player moves a student from his entrance to an island or his dining room
     */
    private final GameState moveStudentState;
    /**
     * State in which the player is moving mother nature
     */
    private final GameState moveMotherNatureState;
    /**
     * State in which the player is choosing the island from where gets the students
     */
    private final GameState chooseCloudState;
    /**
     * Current state of the game
     */
    private GameState state;
    /**
     * Model of the game
     */
    private final GameModel model;
    /**
     * If this flag is true the game is in its last round
     */
    private boolean lastRoundFlag = false;

    /**
     * The number of turns that have been played in the current round
     */
    private int turnsPlayed = 0;
 
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

        setState(playAssistantState);
    }

    /**
     * Gets the nickname of the player that need to play now.
     * @return the nickname of the current player
     */
    public String getCurrentPlayerNickname() {
        return model.getCurrentPlayer().getNickname();
    }

    /**
     * Changes the current state of the game
     * @param newState new state of the game
     */
    public void setState(GameState newState){
        state = newState;
        notifyChangeCurrentStateObservers();
    }

    /**
     * Sets the {@code lastRoundFlag} to true
     */
    public void setLastRoundFlag(){ lastRoundFlag = true;}

    /**
     * Set the winners of the game
     * @param winners players that have won. If more than one is considered a draw
     */
    public void setWinner(Collection<Player> winners){
        this.winners = winners;
        //TODO: update observer
    }

    /**
     * Method to use an assistant card
     *
     * @param assistant is the assistant card to be played
     * @throws NotValidOperationException if this method has been invoked in a state in which
     *                                    this operation is not supported
     * @throws NotValidArgumentException  if has been passed an assistant card that cannot be used,
     *                                    or it is not present in the player's deck
     */
    public void useAssistant(Assistant assistant) throws NotValidOperationException, NotValidArgumentException {
        state.useAssistant(assistant);
    }


    /**
     * Method to move mother nature of a certain number of islands
     *
     * @param positions number of islands to move on mother nature
     * @throws NotValidOperationException if this method has been invoked in a state in which
     *                                    this operation is not supported
     * @throws NotValidArgumentException  if the position is not positive, or it is not
     *                                    compliant with the rules of the game
     */
    public void moveMotherNature(int positions) throws NotValidOperationException, NotValidArgumentException {
        state.moveMotherNature(positions);
    }

    /**
     * Method to get all the students from a chosen cloud and put them in the entrance
     *
     * @param cloudID ID of the cloud from which get the students
     * @throws NotValidOperationException if this method has been invoked in a state in which
     *                                    this operation is not supported
     * @throws NotValidArgumentException  if the cloud passed as a parameter is empty
     */
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
    public void chooseStudentFromLocation(PawnType color, Position originPosition)throws NotValidOperationException, NotValidArgumentException{
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
     * Method to use a character card of the specified type
     * @param cardType type of the character card to use
     * @throws NotValidOperationException if the card is used in basic mode or the players hasn't
     *                                    enough money to use it or the current player has already used a card
     * @throws NotValidArgumentException if the card doesn't exist
     */
    public void useCharacterCard(CharacterCardsType cardType) throws NotValidOperationException, NotValidArgumentException {
        throw new NotValidOperationException("Cannot use cards in basic mode!");
    }


    /**
     * Does the reset operations at the end of every turn
     */
    public void endOfTurn(){
        turnsPlayed++;
        if (turnsPlayed == model.getPlayerList().size()){ // end of round
            if (lastRoundFlag){
                setState(new EndState(this));
                return;
            }
            turnsPlayed = 0;
            model.getGameTable().fillClouds();
            model.calculatePlanningPhaseOrder();
            setState(playAssistantState);
            return;
        }
        model.nextPlayerTurn();
        setState(moveStudentState);
    }

    public GameModel getModel() {
        return model;
    }

    public boolean getLastRoundFlag(){ return lastRoundFlag;}

    public GameState getState() {
        return state;
    }

    public GameState getPlayAssistantState() {
        return playAssistantState;
    }

    public GameState getMoveStudentState() {
        return moveStudentState;
    }

    public GameState getMoveMotherNatureState() {
        return moveMotherNatureState;
    }

    public GameState getChooseCloudState() {
        return chooseCloudState;
    }

    public Collection<Player> getWinner(){return Collections.unmodifiableCollection(winners);}

    /**
     * Skips the turn of the current player, doing random choices when necessary
     */
    public void skipTurn() {
        state.skipTurn();
    }

    // MANAGEMENT OF OBSERVERS FOR STATE SWITCH
    /**
     * List of the observer on the current state
     */
    private final List<ChangeCurrentStateObserver> changeCurrentStateObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on current state.
     * @param observer the observer to be added
     */
    public void addChangeCurrentStateObserver(ChangeCurrentStateObserver observer){
        changeCurrentStateObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on current state.
     * @param observer the observer to be removed
     */
    public void removeChangeCurrentStateObserver(ChangeCurrentStateObserver observer){
        changeCurrentStateObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on current state.
     */
    private void notifyChangeCurrentStateObservers(){
        for(ChangeCurrentStateObserver observer : changeCurrentStateObservers)
            observer.changeCurrentStateObserverUpdate(this.state.getType());
    }

}
