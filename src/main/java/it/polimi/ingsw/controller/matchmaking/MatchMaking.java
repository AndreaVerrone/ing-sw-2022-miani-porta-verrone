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
    private final Collection<PlayerLoginInfo> players = new ArrayList<>();

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
    public void next() throws NotValidOperationException {
        state.next();
    }

}
