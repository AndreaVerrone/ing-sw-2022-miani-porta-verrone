package it.polimi.ingsw.network.messages.servertoclient.matchmaking;

import it.polimi.ingsw.client.reduced_model.ReducedPlayerLoginInfo;
import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A message sent from the server to the client to comunicate the success in entering
 * a game and all the parameters necessary to display the lobby
 */
public class GameEntered extends ServerCommandNetMsg {

    /**
     * The information of all the players currently in the lobby of the game entered
     */
    private final Collection<ReducedPlayerLoginInfo> playerLoginInfos;
    /**
     * The number of players requested to start the game
     */
    private final int numPlayers;
    /**
     * If the game uses the expert rules
     */
    private final boolean isExpert;

    /**
     * The nickname of the current player
     */
    private final String currentPlayer;

    /**
     * Creates a new message to comunicate to the client that he entered a game
     *
     * @param playerLoginInfos the information of all the players currently in the lobby of the game
     * @param numPlayers       the number of players requested to start the game
     * @param isExpert         {@code true} if the game uses the expert rules, {@code false} otherwise
     * @param currentPlayer the nickname of the current player
     */
    public GameEntered(Collection<ReducedPlayerLoginInfo> playerLoginInfos, int numPlayers, boolean isExpert, String currentPlayer) {
        this.playerLoginInfos = new ArrayList<>(playerLoginInfos);
        this.numPlayers = numPlayers;
        this.isExpert = isExpert;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void processMessage(ClientView client) {
        client.createMatchmakingView(playerLoginInfos, numPlayers, isExpert, currentPlayer);
    }
}
