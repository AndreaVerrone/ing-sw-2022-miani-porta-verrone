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
public class Game{
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
     * It is true, if the current player can use the character card.
     * <p>
     * Note that a player can use only one time a character card during its turn in the action phase
     */
    private boolean canUseCharacterCard=true;

    public Game(Collection<PlayerLoginInfo> players){
        //TODO: create all states and add documentation
        model = new GameModel(players);
        state = playAssistantState;
    }

    public boolean getCanUseCharacterCard() {
        return canUseCharacterCard;
    }

    public void setCanUseCharacterCard(boolean canUseCharacterCard) {
        this.canUseCharacterCard = canUseCharacterCard;
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
     * Method to use an assistant card
     * @param assistant is the assistant card to be played
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if has been passed an assistant card that cannot be used,
     *                                   or it is not present in the player's deck
     */
    public void useAssistant(Assistant assistant) throws NotValidOperationException, NotValidArgumentException {
        state.useAssistant(assistant);
    }

    /**
     * Method to move a student from the entrance to an island
     * @param student student color to move
     * @param islandID island ID to where move the student
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the student to move is not present in entrance or the island doesn't exist
     */
    public void moveStudentToIsland(PawnType student, int islandID) throws NotValidOperationException, NotValidArgumentException {
        state.moveStudentToIsland(student, islandID);
    }

    /**
     * Method to move a student from the entrance to the dining room
     * @param student student color to move
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the student is not present in entrance or it is present, but the table for the
     *                                   students of that color is full
     */
    public void moveStudentToDiningRoom(PawnType student) throws NotValidOperationException, NotValidArgumentException {
        state.moveStudentToDiningRoom(student);
    }

    /**
     * Method to move mother nature of a certain number of islands
     * @param positions number of islands to move on mother nature
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the position is not positive, or it is not compliant with the rules of the game
     */
    public void moveMotherNature(int positions) throws NotValidOperationException, NotValidArgumentException {
        state.moveMotherNature(positions);
    }

    /**
     * Method to get all the students from a chosen cloud and put them in the entrance
     * @param cloudID ID of the cloud from which get the students
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the cloud passed as a parameter is empty
     */
    public void takeFromCloud(int cloudID) throws NotValidOperationException, NotValidArgumentException {
        state.takeFromCloud(cloudID);
    }

    /**
     * This method allow to use the character card passed as a parameter.
     * @param characterCard the character card to use
     * @throws NotValidOperationException if the character card cannot be used because the player cannot pay
     * for the usage, the state of the game do not allow the usage or the player has already used a character card
     * during its turn.
     * @throws NotValidArgumentException if the character card does not exist
     */
    public void useCharacterCard(CharacterCard characterCard) throws NotValidOperationException, NotValidArgumentException {

        // current player
        Player currentPlayer = getModel().getCurrentPlayer();

        // check that the player can use it since it is the first time that he use a
        // character card during its turn
        if(!canUseCharacterCard){
            throw new NotValidOperationException("you have already used a character card during this turn");
        }

        // check that the player can use it since it has enough money
        if(currentPlayer.getCoins()<characterCard.getCost()){
            throw new NotValidOperationException("you have not enough coin");
        }

        state.useCharacterCard(characterCard);
    }

    /**
     * This method allow to move one student from the character card 1
     * to the island passed as a parameter.
     * After the calling of the method, if the student bag is not empty,
     * a student (taken from the bag) will be added on the character card.
     * @param pawnType color of the student to move to island
     * @param islandID island on which put the student
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the student or the island does not exist
     */
    public void moveFromCardToIsland(PawnType pawnType, int islandID) throws NotValidOperationException, NotValidArgumentException{
        state.moveFromCardToIsland(pawnType,islandID);
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
