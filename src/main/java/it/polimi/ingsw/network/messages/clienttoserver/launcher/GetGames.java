package it.polimi.ingsw.network.messages.clienttoserver.launcher;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.network.messages.servertoclient.launcher.PossibleGames;
import it.polimi.ingsw.server.ClientHandler;

import java.util.Collection;

/**
 * A message sent from client to the server to ask for existing games.
 */
public class GetGames extends ClientCommandNetMsg {


    @Override
    protected void normalProcess(ClientHandler clientInServer) {
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
        Collection<String> games = clientInServer.getSessionController().getGames();
        clientInServer.sendMessage(new PossibleGames(games));
    }

    @Override
    public void processResponse(ResponseMessage response, ClientController clientController) {

    }
}
