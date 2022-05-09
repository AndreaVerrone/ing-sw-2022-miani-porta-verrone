package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.messages.servertoclient.PossibleGames;
import it.polimi.ingsw.server.ClientHandler;

import java.util.Collection;

/**
 * A message sent from client to the server to ask for existing games.
 */
public class GetGames extends ClientCommandNetMsg{


    @Override
    public void processMessage(ClientHandler clientInServer) {
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
        Collection<String> games = clientInServer.getSessionController().getGames();
        clientInServer.sendMessage(new PossibleGames(games));
    }

    @Override
    public void processResponse(ResponseMessage response) {

    }
}
