package it.polimi.ingsw.network.messages.servertoclient.launcher;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

import java.util.Collection;
import java.util.HashSet;

/**
 * A message sent from server to client to send all the games that can be joined
 */
public class PossibleGames extends ServerCommandNetMsg {

    /**
     * All the IDs of the games present in server
     */
    private final HashSet<String> games;

    /**
     * Creates a new message for showing the IDs of the games passed as parameter.
     * @param games a list of ID of games existing
     */
    public PossibleGames(Collection<String> games){
        this.games = new HashSet<>(games);
    }

    @Override
    public void processMessage(ClientView client) {
        client.getScreenBuilder().build(ScreenBuilder.Screen.GAMES_LIST, games);
    }

}
