package it.polimi.ingsw.network.messages.servertoclient.matchmaking;

import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.controller.PlayerLoginInfo;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
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
    private final ArrayList<PlayerLoginInfo> players;

    /**
     * Creates a new message to inform all the players in a game that something in the players list changed.
     * @param players the players currently in the game
     */
    public PlayersChanged(Collection<PlayerLoginInfo> players) {
        this.players = new ArrayList<>(players);
    }

    @Override
    public void processMessage(ConnectionHandler client) {
        client.sendMessage(ResponseMessage.newSuccess(this));
        // TODO: 10/05/2022 update the list of players showed 
    }

    @Override
    public void processResponse(ResponseMessage response) {

    }
}
