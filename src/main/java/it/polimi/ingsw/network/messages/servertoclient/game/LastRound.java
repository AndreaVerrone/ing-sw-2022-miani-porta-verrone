package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * A message sent from the server to all clients connected to a game to indicate that
 * they are playing the last round of the game
 */
public class LastRound extends ServerCommandNetMsg {
    @Override
    public void processMessage(ConnectionHandler client) {
        client.sendMessage(ResponseMessage.newSuccess(this));
        // TODO: 11/05/2022 show in view
    }

}
