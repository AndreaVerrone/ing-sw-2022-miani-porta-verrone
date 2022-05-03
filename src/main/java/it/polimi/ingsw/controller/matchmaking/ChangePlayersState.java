package it.polimi.ingsw.controller.matchmaking;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.controller.PlayerLoginInfo;

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
            throw new NotValidArgumentException();
        if (matchMaking.getPlayers().size() == matchMaking.getNumPlayers())
            throw new NotValidOperationException();
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
            throw new NotValidArgumentException();
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
            throw new NotValidArgumentException();
        matchMaking.setNumPlayers(value);
    }

    /**
     *
     * @throws NotValidOperationException if not all the expected players has joined the lobby
     */
    @Override
    public void next() throws NotValidOperationException {
        if (matchMaking.getNumPlayers() != matchMaking.getPlayers().size())
            throw new NotValidOperationException();
        matchMaking.chooseFirstPlayer();
        matchMaking.setState(new SetPlayerParametersState(matchMaking, 1));
    }

    private boolean isNicknameOfAPlayer(String nickname) {
        return matchMaking.getPlayers().stream().anyMatch(
                playerLoginInfo -> playerLoginInfo.getNickname().equals(nickname));
    }
}
