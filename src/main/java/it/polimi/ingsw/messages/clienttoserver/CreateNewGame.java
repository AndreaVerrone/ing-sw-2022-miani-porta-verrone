package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.messages.responses.Result;
import it.polimi.ingsw.messages.servertoclient.GameCreated;
import it.polimi.ingsw.server.ClientHandler;

/**
 * Message sent from client to server to indicate that he wants to create a new game
 */
public class CreateNewGame extends ClientCommandNetMsg {

    /**
     * The number of players requested to start the game.
     */
    private final int numOfPlayers;

    /**
     * If the game must use expert rules.
     */
    private final boolean wantExpert;

    /**
     * Creates a new request to create a new game for the specified number of player using the
     * expert rules, if requested.
     *
     * @param numOfPlayers the number of players needed
     * @param wantExpert   {@code true} if the expert rules must be applied, {@code false} otherwise
     */
    public CreateNewGame(int numOfPlayers, boolean wantExpert) {
        this.numOfPlayers = numOfPlayers;
        this.wantExpert = wantExpert;
    }

    @Override
    protected void normalProcess(ClientHandler clientInServer)
            throws NotValidArgumentException {
        if (!(numOfPlayers == 2 || numOfPlayers == 3))
            throw new NotValidArgumentException();
        String newGameID = clientInServer.getSessionController().createNewGame(numOfPlayers, wantExpert);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
        clientInServer.sendMessage(new GameCreated(newGameID));
    }


    @Override
    public void processResponse(ResponseMessage response) {
        if (response.getResult() == Result.INVALID_ARGUMENT) {
            // TODO: 09/05/2022 notify view of the error
        }
    }
}
