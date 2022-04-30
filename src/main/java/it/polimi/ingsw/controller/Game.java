package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.Assistant;

import java.util.Collection;

public class Game{
    /**
     * State in which the player is playing an assistant card
     */
    private State playAssistantState;
    /**
     * State in which the player is a student to an island or his dining room
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

    public Game(Collection<PlayerLoginInfo> players){
        //TODO: create all states
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
     * Method to use an assistant card
     * @param assistant is the assistant card to be played
     */
    public void useAssistant(Assistant assistant) {
        state.useAssistant(assistant);
    }

    /**
     * Method to move a student from the entrance to an island
     * @param student student color to move
     * @param islandID island ID to where move the student
     */
    public void moveStudentToIsland(PawnType student, int islandID) {
        state.moveStudentToIsland(student, islandID);
    }

    /**
     * Method to move a student from the entrance to the dining room
     * @param student student color to move
     */
    public void moveStudentToDiningRoom(PawnType student) {
        state.moveStudentToDiningRoom(student);
    }

    /**
     * Method to move mother nature of a certain number of islands
     * @param positions number of islands to move on mother nature
     */
    public void moveMotherNature(int positions) {
        state.moveMotherNature(positions);
    }

    /**
     * Method to get all the students from a chosen cloud and put them in the entrance
     * @param cloudID ID of the cloud from which get the students
     */
    public void takeFromCloud(int cloudID) {
        state.takeFromCloud(cloudID);
    }

    protected GameModel getModel() {
        return model;
    }

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
