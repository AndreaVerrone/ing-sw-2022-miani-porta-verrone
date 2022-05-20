package it.polimi.ingsw.server.controller.matchmaking;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.game.IGame;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.*;

/**
 * A class used to handle the lobby of players when a new game is requested
 */
public class MatchMaking implements IMatchMaking{

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

    @Override
    public void setHardMode(boolean isHardMode){
        this.isHardMode = isHardMode;
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void changeNumOfPlayers(int value) throws NotValidOperationException, NotValidArgumentException {
        state.changeNumOfPlayers(value);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void addPlayer(String nickname) throws NotValidOperationException, NotValidArgumentException {
        state.addPlayer(nickname);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void removePlayer(String nickname) throws NotValidOperationException, NotValidArgumentException {
        state.removePlayer(nickname);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void setTowerOfPlayer(TowerType towerType) throws NotValidOperationException, NotValidArgumentException {
        state.setTowerOfPlayer(towerType);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void setWizardOfPlayer(Wizard wizard) throws NotValidOperationException, NotValidArgumentException {
        state.setWizardOfPlayer(wizard);
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     */
    @Override
    public Optional<IGame> next() throws NotValidOperationException {
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
}
