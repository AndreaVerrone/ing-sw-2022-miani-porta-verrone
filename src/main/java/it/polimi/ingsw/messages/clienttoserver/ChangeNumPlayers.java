package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.messages.responses.Result;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A message sent from client to the server to communicate the will to change the number of players of a game
 */
public class ChangeNumPlayers extends ClientCommandNetMsg{

    /**
     * The new number of players requested.
     */
    private final int newNumPlayers;

    /**
     * Creates a new message to change the necessary number of players in a game.
     * @param newNumPlayers the new number of players requested
     */
    public ChangeNumPlayers(int newNumPlayers) {
        this.newNumPlayers = newNumPlayers;
    }

    @Override
    public void processMessage(ClientHandler clientInServer) {
        try {
            clientInServer.getSessionController().changeNumOfPlayers(newNumPlayers);
            clientInServer.sendMessage(ResponseMessage.newSuccess(this));
        } catch (NotValidOperationException e) {
            clientInServer.sendMessage(new ResponseMessage(this, Result.INVALID_OPERATION, e.getErrorCode()));
        } catch (NotValidArgumentException e) {
            clientInServer.sendMessage(new ResponseMessage(this, Result.INVALID_ARGUMENT, e.getErrorCode()));
        }
    }

    @Override
    public void processResponse(ResponseMessage response) {
        // TODO: 11/05/2022 show response to client
    }
}
