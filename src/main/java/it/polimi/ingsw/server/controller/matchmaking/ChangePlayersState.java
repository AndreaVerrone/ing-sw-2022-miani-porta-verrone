package it.polimi.ingsw.server.controller.matchmaking;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.controller.game.IGame;

import java.util.Optional;

/**
 * The initial state of {@link MatchMaking}. Here, players can enter or exit the lobby
 */
class ChangePlayersState implements MatchMakingState {

    /**
     * The {@code MatchMaking} that this state represents
     */
    private final MatchMaking matchMaking;

    /**
     * Creates a new state associated with the passed {@code MatchMaking}.
     * <p>
     * This state represents the initial situation of the matchmaking and should be assigned only when
     * the matchmaking is created.
     * @param matchMaking the matchmaking associated with this state
     */
    ChangePlayersState(MatchMaking matchMaking) {
        this.matchMaking = matchMaking;
    }

    /**
     * @param nickname the nickname chosen by the player
     * @throws NotValidArgumentException if the nickname is already taken
     * @throws NotValidOperationException if the lobby is full
     */
    @Override
    public void addPlayer(String nickname) throws NotValidArgumentException, NotValidOperationException {
        if (isNicknameOfAPlayer(nickname))
            throw new NotValidArgumentException("Nickname already taken!");
        if (matchMaking.getPlayers().size() == matchMaking.getNumPlayers())
            throw new NotValidOperationException("The lobby is already full!");
        matchMaking.addPlayer(new PlayerLoginInfo(nickname));
    }

    /**
     *
     * @param nickname the nickname of the player to remove
     * @throws NotValidArgumentException if there is no player with the provided nickname
     */
    @Override
    public void removePlayer(String nickname) throws NotValidArgumentException {
        if (!isNicknameOfAPlayer(nickname))
            throw new NotValidArgumentException("There isn't a player with this nickname!");
        for (PlayerLoginInfo player : matchMaking.getPlayers()) {
            if (player.getNickname().equals(nickname)) {
                matchMaking.removePlayer(player);
                return;
            }
        }
    }

    /**
     * @param value the new number of players
     * @throws NotValidArgumentException if the selected number of players is not valid
     * (i.e. if it's not one of the value supported or less than the player already present in the lobby)
     */
    @Override
    public void changeNumOfPlayers(int value) throws NotValidArgumentException {
        if (value < 2 || value > 4 || value < matchMaking.getPlayers().size())
            throw new NotValidArgumentException("Number of players not valid!");
        matchMaking.setNumPlayers(value);
    }

    /**
     *
     * @throws NotValidOperationException if not all the expected players has joined the lobby
     */
    @Override
    public Optional<Game> next() throws NotValidOperationException {
        if (matchMaking.getNumPlayers() != matchMaking.getPlayers().size())
            throw new NotValidOperationException("There aren't enough players in the lobby to start the game!");
        matchMaking.chooseFirstPlayer();
        matchMaking.setState(new SetPlayerParametersState(matchMaking, 1));
        return Optional.empty();
    }

    private boolean isNicknameOfAPlayer(String nickname) {
        return matchMaking.getPlayers().stream().anyMatch(
                playerLoginInfo -> playerLoginInfo.getNickname().equals(nickname));
    }

    @Override
    public StateType getType() {
        return StateType.CHANGE_PLAYER_STATE;
    }
}
