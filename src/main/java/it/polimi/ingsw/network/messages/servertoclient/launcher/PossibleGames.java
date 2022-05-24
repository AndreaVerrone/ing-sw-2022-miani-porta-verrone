package it.polimi.ingsw.network.messages.servertoclient.launcher;

import it.polimi.ingsw.client.ClientController;
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
    public void processMessage(ClientController client) {
        // TODO: 09/05/2022 show in view the ids
    }

}
