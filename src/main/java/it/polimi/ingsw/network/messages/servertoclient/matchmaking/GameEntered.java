package it.polimi.ingsw.network.messages.servertoclient.matchmaking;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.ReducedPlayerLoginInfo;
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
     * Creates a new message to comunicate to the client that he entered a game
     * @param playerLoginInfos the information of all the players currently in the lobby of the game
     * @param numPlayers the number of players requested to start the game
     * @param isExpert {@code true} if the game uses the expert rules, {@code false} otherwise
     */
    public GameEntered(Collection<ReducedPlayerLoginInfo> playerLoginInfos, int numPlayers, boolean isExpert) {
        this.playerLoginInfos = new ArrayList<>(playerLoginInfos);
        this.numPlayers = numPlayers;
        this.isExpert = isExpert;
    }

    @Override
    public void processMessage(ClientController client) {
        client.createGameView(playerLoginInfos, numPlayers, isExpert);
    }
}
