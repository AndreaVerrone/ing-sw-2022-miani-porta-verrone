package it.polimi.ingsw.controller.matchmaking;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Wizard;

import java.util.Optional;

/**
 * In this state all the players has joined the lobby,
 * and one by one all the players choose a tower and a wizard
 */
class SetPlayerParametersState implements MatchMakingState{

    /**
     * The {@code MatchMaking} that this state represents
     */
    private final MatchMaking matchMaking;

    /**
     * The player that is being served (i.e. if the player that need to choose tower or wizard is the first,
     * this will be 1, if it's the second it will be 2 and so on).
     */
    private final int playerServing;

    /**
     * Creates a new state in which the current player need to choose the wizard and the tower he want
     * to use during the game.
     * @param matchMaking the matchmaking associated with this state
     * @param playerServing the number representing the player that is being served (i.e. 1 for the first
     *                      player, 2 for the second and so on)
     */
    protected SetPlayerParametersState(MatchMaking matchMaking, int playerServing){
        assert playerServing >= 1 && playerServing <= matchMaking.getNumPlayers();
        this.matchMaking = matchMaking;
        this.playerServing = playerServing;
    }

    /**
     * @throws NotValidArgumentException  if the tower selected is not available
     */
    @Override
    public void setTowerOfPlayer(TowerType tower) throws NotValidArgumentException {
        if (!matchMaking.getTowersAvailable().contains(tower))
            throw new NotValidArgumentException("Tower not available!");
        matchMaking.setTowerOfCurrentPlayer(tower);
    }

    /**
     * @throws NotValidArgumentException  if the wizard selected is not available
     */
    @Override
    public void setWizardOfPlayer(Wizard wizard) throws NotValidArgumentException {
        if (!matchMaking.getWizardsAvailable().contains(wizard))
            throw new NotValidArgumentException("Wizard not available!");
        matchMaking.setWizardOfCurrentPlayer(wizard);
    }

    /**
     * @throws NotValidOperationException if the current player hasn't chosen yet the tower or wizard
     */
    @Override
    public Optional<IGame> next() throws NotValidOperationException {
        PlayerLoginInfo currentPlayer = matchMaking.getCurrentPlayer();
        if (currentPlayer.getTowerType() == null || currentPlayer.getWizard() == null)
            throw new NotValidOperationException();
        if (playerServing == matchMaking.getNumPlayers()) {
            if (matchMaking.isHardMode())
                return Optional.of(new ExpertGame(matchMaking.getPlayers()));
            return Optional.of(new Game(matchMaking.getPlayers()));
        }
        matchMaking.nextPlayer();
        matchMaking.setState(new SetPlayerParametersState(matchMaking, playerServing+1));
        return Optional.empty();
    }
}
