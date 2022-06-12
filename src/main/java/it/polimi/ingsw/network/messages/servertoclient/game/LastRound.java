package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * A message sent from the server to all clients connected to a game to indicate that
 * they are playing the last round of the game
 */
public class LastRound extends ServerCommandNetMsg {
    @Override
    public void processMessage(ClientController client) {
        client.notifyLastRound();
    }

}
