package it.polimi.ingsw.controller.matchmaking;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.controller.PlayerLoginInfo;
import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Wizard;

import java.util.*;

/**
 * A class used to handle the lobby of players when a new game is requested
 */
public class MatchMaking {

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
        state = new ChangePlayersState(this);
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
    }

    protected void setNumPlayers(int value){
        numPlayers = value;
    }

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
     * Moves the match making to the next state.
     * @throws NotValidOperationException if the state can't be changed (i.e. not all the expected operations
     * of the current state were done)
     */
    public void next() throws NotValidOperationException {
        state.next();
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
    }

    /**
     * Choose a random player to be the first in the selection of tower color and wizard.
     */
    protected void chooseFirstPlayer(){
        currentPlayer = new Random().nextInt(numPlayers);
    }

    /**
     * Change the current player to be the next in this game
     */
    protected void nextPlayer(){
        currentPlayer = (currentPlayer + 1) % numPlayers;
    }
}
