package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.Assistant;

import java.util.Collection;

public class Game implements State{
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

    protected void setState(State newState){
        state = newState;
    }

    @Override
    public void useAssistant(Assistant assistant) {
        state.useAssistant(assistant);
    }

    @Override
    public void moveStudentToIsland(PawnType student, int islandID) {
        state.moveStudentToIsland(student, islandID);
    }

    @Override
    public void moveStudentToDiningRoom(PawnType student) {
        state.moveStudentToDiningRoom(student);
    }

    @Override
    public void moveMotherNature(int positions) {
        state.moveMotherNature(positions);
    }

    @Override
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
