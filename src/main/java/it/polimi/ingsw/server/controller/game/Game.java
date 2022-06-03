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
public class Game implements IGame {
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

    @Override
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
    public void chooseStudentFromLocation(PawnType color, Position originPosition)throws NotValidOperationException, NotValidArgumentException{
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

    @Override
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
