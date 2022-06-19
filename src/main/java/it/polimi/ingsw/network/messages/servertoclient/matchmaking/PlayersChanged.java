package it.polimi.ingsw.network.messages.servertoclient.matchmaking;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.reduced_model.ReducedPlayerLoginInfo;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A message sent from the server to the client to notify the changing of the players in the game.
 */
public class PlayersChanged extends ServerCommandNetMsg {

    /**
     * The players currently in the game
     */
    private final Collection<ReducedPlayerLoginInfo> players;

    /**
     * Creates a new message to inform all the players in a game that something in the players list changed.
     * @param players the players currently in the game
     */
    public PlayersChanged(Collection<ReducedPlayerLoginInfo> players) {
        this.players = new ArrayList<>(players);
    }

    @Override
    public void processMessage(ClientView client) {
        client.playersChanged(players);
    }

}
