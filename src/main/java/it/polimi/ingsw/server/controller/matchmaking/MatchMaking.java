package it.polimi.ingsw.server.controller.matchmaking;

import it.polimi.ingsw.server.controller.*;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.controller.matchmaking.observers.NumberOfPlayersObserver;
import it.polimi.ingsw.server.controller.matchmaking.observers.PlayersChangedObserver;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.observers.ChangeCurrentPlayerObserver;

import java.util.*;

/**
 * A class used to handle the lobby of players when a new game is requested
 */
public class MatchMaking{

    /**
     * The current state of this match making
     */
    private MatchMakingState state;

    /**
     * The number of players in the game.
     */
    private int numPlayers;

    /**
     * {@code true} if this game is in expert mode, {@code false} otherwise.
     */
    private boolean isHardMode;

    /**
     * The players present in this lobby ready to play.
     */
    private final List<PlayerLoginInfo> players = new ArrayList<>();

    /**
     * The index of the current player. This is used to set the tower and wizard.
     */
    private int currentPlayer;

    /**
     * The wizards available to be chosen by a player.
     */
    private final Set<Wizard> wizardsAvailable = new HashSet<>(List.of(Wizard.values()));

    /**
     * The color of tower available to be chosen by a player.
     */
    private final Set<TowerType> towersAvailable = new HashSet<>(List.of(TowerType.values()));


    /**
     * Creates a new lobby where the players can enter to start a new game. The number of players
     * and the difficulty is chosen in advance, but can be modified later.
     * @param numPlayers the number of players of this game
     * @param isHardMode {@code true} if wanted to start a hard game, {@code false} otherwise
     */
    public MatchMaking(int numPlayers, boolean isHardMode){
        this.numPlayers = numPlayers;
        this.isHardMode = isHardMode;
        setState(new ChangePlayersState(this));
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public boolean isHardMode() {
        return isHardMode;
    }

    /**
     * An unmodifiable view of the players currently present in this lobby.
     * @return the players in this lobby
     */
    public Collection<PlayerLoginInfo> getPlayers(){
        return Collections.unmodifiableCollection(players);
    }

    /**
     * Gets the player that need to choose a tower and a wizard
     * @return the current player in turn
     */
    public PlayerLoginInfo getCurrentPlayer(){
        return players.get(currentPlayer);
    }

    /**
     * The wizards available to be chosen by a player.
     * <p>
     * Note: this should be used only to display the possibilities.
     * To modify the content use the appropriate methods.
     * @return the wizards available
     * @see #setWizardOfPlayer(Wizard)
     */
    public Set<Wizard> getWizardsAvailable() {
        return new HashSet<>(wizardsAvailable);
    }

    /**
     * The color of tower available to be chosen by a player.
     * <p>
     * Note: this should be used only to display the possibilities.
     * To modify the content use the appropriate methods.
     * @return the color of tower available
     * @see #setTowerOfPlayer(TowerType)
     */
    public Set<TowerType> getTowersAvailable() {
        return new HashSet<>(towersAvailable);
    }


    protected void setState(MatchMakingState newState){
        state = newState;
        notifyChangeCurrentStateObservers();
    }

    protected void setNumPlayers(int value){
        numPlayers = value;
        notifyNumberOfPlayersObserver();
    }

    /**
     * Sets if the game it's been creating need to use expert rules.
     * @param isHardMode {@code true} if expert rules are required, {@code false} otherwise
     */
    public void setHardMode(boolean isHardMode){
        this.isHardMode = isHardMode;
    }

    /**
     * Change the number of players needed in this game. This cannot be less than the player already
     * present in this lobby.
     * @param value the new number of players
     * @throws NotValidArgumentException if the selected number of players is not valid (i.e. if it's not
     * one of the value supported or less than the player already present in this lobby)
     * @throws NotValidOperationException if the number of player can't be changed in the current state
     */
    public void changeNumOfPlayers(int value) throws NotValidOperationException, NotValidArgumentException {
        state.changeNumOfPlayers(value);
    }

    /**
     * Adds a player with the provided nickname to this lobby. The nickname must be unique.
     * @param nickname the nickname chosen by the player
     * @throws NotValidArgumentException if the nickname is already taken
     * @throws NotValidOperationException if a new player can't be added in the current state
     */
    public void addPlayer(String nickname) throws NotValidOperationException, NotValidArgumentException {
        state.addPlayer(nickname);
    }

    /**
     * Removes the player with the provided nickname from this lobby.
     * @param nickname the nickname of the player to remove
     * @throws NotValidArgumentException if there is no player with the provided nickname
     * @throws NotValidOperationException if a player can't leave the game in the current state
     */
    public void removePlayer(String nickname) throws NotValidOperationException, NotValidArgumentException {
        state.removePlayer(nickname);
    }

    /**
     * Sets the tower type of the current player in the queue.
     * @param towerType the type of tower to assign
     * @throws NotValidArgumentException if the tower selected is not available
     * @throws NotValidOperationException if the tower of the player can't be changed in the current state
     */
    public void setTowerOfPlayer(TowerType towerType) throws NotValidOperationException, NotValidArgumentException {
        state.setTowerOfPlayer(towerType);
    }

    /**
     * Sets the wizard of the current player in the queue.
     * @param wizard the wizard to assign
     * @throws NotValidArgumentException if the wizard selected is not available
     * @throws NotValidOperationException if the wizard of the player can't be changed in the current state
     */
    public void setWizardOfPlayer(Wizard wizard) throws NotValidOperationException, NotValidArgumentException {
        state.setWizardOfPlayer(wizard);
    }

    /**
     * Moves the match making to the next state. It also returns the new game created, if any.
     * @throws NotValidOperationException if the state can't be changed (i.e. not all the expected operations
     * of the current state were done)
     * @return {@link Optional#empty()} if no game was meant to be created, or an {@code Optional} containing
     * the game created
     */
    public Optional<Game> next() throws NotValidOperationException {
        return state.next();
    }


    /**
     * Adds a player in this lobby.
     * <p>
     * This is a protected version intended to be used only by the states of this class.
     * For the general method, see {@link #addPlayer(String)}.
     * @param playerLoginInfo the player to add
     */
    protected void addPlayer(PlayerLoginInfo playerLoginInfo){
        players.add(playerLoginInfo);
        notifyPlayersChangedObserver();
    }

    /**
     * Removes a player from this lobby.
     * <p>
     * This is a protected version intended to be used only by the states of this class.
     * For the general method, see {@link #removePlayer(String)}.
     * @param playerLoginInfo the player to remove
     */
    protected void removePlayer(PlayerLoginInfo playerLoginInfo){
        players.remove(playerLoginInfo);
        notifyPlayersChangedObserver();
    }

    /**
     * Choose a random player to be the first in the selection of tower color and wizard.
     */
    protected void chooseFirstPlayer(){
        currentPlayer = new Random().nextInt(numPlayers);
        notifyChangeCurrentPlayerObservers();
    }

    /**
     * Change the current player to be the next in this game
     */
    protected void nextPlayer(){
        currentPlayer = (currentPlayer + 1) % numPlayers;
        notifyChangeCurrentPlayerObservers();
    }

    /**
     * Sets the tower of the current player to be the one passed as argument.
     * @param tower the tower to assign to the current player
     */
    protected void setTowerOfCurrentPlayer(TowerType tower){
        PlayerLoginInfo player = getCurrentPlayer();
        TowerType towerOfPlayer = player.getTowerType();
        if (towerOfPlayer == tower)
            return;
        if (towerOfPlayer != null)
            towersAvailable.add(towerOfPlayer);
        player.setTowerType(tower);
        towersAvailable.remove(tower);
    }

    /**
     * Sets the wizard of the current player to be the one passed as argument.
     * @param wizard the tower to assign to the current player
     */
    protected void setWizardOfCurrentPlayer(Wizard wizard){
        PlayerLoginInfo player = getCurrentPlayer();
        Wizard wizardOfPlayer = player.getWizard();
        if (wizardOfPlayer == wizard)
            return;
        if (wizardOfPlayer != null)
            wizardsAvailable.add(wizardOfPlayer);
        player.setWizard(wizard);
        wizardsAvailable.remove(wizard);
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
        StateType state = this.state.getType();
        for(ChangeCurrentStateObserver observer : changeCurrentStateObservers) {
            observer.changeCurrentStateObserverUpdate(state);
            if (state == StateType.SET_PLAYER_PARAMETER_STATE)
                observer.requestChoosePlayerParameter(getTowersAvailable(), getWizardsAvailable());
        }
    }

  // MANAGEMENT OF OBSERVERS FOR PLAYERS OF THE MATCH
    /**
     * List of the observer on the players of the match
     */
    private final List<PlayersChangedObserver> playersChangedObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the players.
     * @param observer the observer to be added
     */
    public void addPlayersChangedObserver(PlayersChangedObserver observer){
        playersChangedObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the players.
     * @param observer the observer to be removed
     */
    public void removePlayersChangedObserver(PlayersChangedObserver observer){
        playersChangedObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that the players of the match have changed.
     */
    private void notifyPlayersChangedObserver(){
        for(PlayersChangedObserver observer : playersChangedObservers)
            observer.playersChangedObserverUpdate(getPlayers());
    }

    // MANAGEMENT OF OBSERVERS ON CURRENT PLAYER
    /**
     * List of the observer on the current player
     */
    private final List<ChangeCurrentPlayerObserver> changeCurrentPlayerObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on current player.
     * @param observer the observer to be added
     */
    public void addChangeCurrentPlayerObserver(ChangeCurrentPlayerObserver observer){
        changeCurrentPlayerObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on current player.
     * @param observer the observer to be removed
     */
    public void removeChangeCurrentPlayerObserver(ChangeCurrentPlayerObserver observer){
        changeCurrentPlayerObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on current player.
     */
    private void notifyChangeCurrentPlayerObservers(){
        for(ChangeCurrentPlayerObserver observer : changeCurrentPlayerObservers)
            observer.changeCurrentPlayerObserverUpdate(players.get(currentPlayer).getNickname());
    }

    // MANAGEMENT OF OBSERVERS FOR CHANGING NUMBER OF PLAYERS
    /**
     * List of the observer on the number of players
     */
    private final List<NumberOfPlayersObserver> numberOfPlayersObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the number of players.
     * @param observer the observer to be added
     */
    public void addNumberOfPlayersObserver(NumberOfPlayersObserver observer){
        numberOfPlayersObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the number of players.
     * @param observer the observer to be removed
     */
    public void removeNumberOfPlayersObserver(NumberOfPlayersObserver observer){numberOfPlayersObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that the number of players has been changed
     */
    private void notifyNumberOfPlayersObserver(){
        for(NumberOfPlayersObserver observer : numberOfPlayersObservers)
            observer.numberOfPlayersObserverUpdate(this.numPlayers);
    }

}
