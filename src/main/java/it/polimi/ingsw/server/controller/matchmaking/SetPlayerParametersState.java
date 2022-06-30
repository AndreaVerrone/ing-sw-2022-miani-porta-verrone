package it.polimi.ingsw.server.controller.matchmaking;

import it.polimi.ingsw.network.messages.responses.ErrorCode;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.Optional;
import java.util.Random;

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
     * Creates a new state in which the current player need to choose the wizard and the tower he wants
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
            throw new NotValidArgumentException(ErrorCode.TOWER_NOT_AVAILABLE);
        matchMaking.setTowerOfCurrentPlayer(tower);
    }

    /**
     * @throws NotValidArgumentException  if the wizard selected is not available
     */
    @Override
    public void setWizardOfPlayer(Wizard wizard) throws NotValidArgumentException {
        if (!matchMaking.getWizardsAvailable().contains(wizard))
            throw new NotValidArgumentException(ErrorCode.WIZARD_NOT_AVAILABLE);
        matchMaking.setWizardOfCurrentPlayer(wizard);
    }

    /**
     * @throws NotValidOperationException if the current player hasn't chosen yet the tower or wizard
     */
    @Override
    public Optional<Game> next() throws NotValidOperationException {
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

    @Override
    public Optional<Game> skipTurn() {
        PlayerLoginInfo currentPlayer = matchMaking.getCurrentPlayer();
        Random random = new Random();
        if (currentPlayer.getTowerType() == null) {
            int size = matchMaking.getTowersAvailable().size();
            while (true) {
                int randomTower = random.nextInt(size);
                try {
                    setTowerOfPlayer(TowerType.values()[randomTower]);
                    break;
                } catch (NotValidArgumentException ignore) {}
            }
        }
        if (currentPlayer.getWizard() == null) {
            int size = matchMaking.getWizardsAvailable().size();
            while (true) {
                int randomWizard = random.nextInt(size);
                try {
                    setWizardOfPlayer(Wizard.values()[randomWizard]);
                    break;
                } catch (NotValidArgumentException ignore) {}
            }
        }
        try {
            return next();
        } catch (NotValidOperationException e) {
            throw new AssertionError(e);
        }

    }

    @Override
    public StateType getType() {
        return StateType.SET_PLAYER_PARAMETER_STATE;
    }
}
