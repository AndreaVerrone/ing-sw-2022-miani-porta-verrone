package it.polimi.ingsw.network.messages.clienttoserver.launcher;

import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.network.messages.servertoclient.launcher.GameCreated;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;

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
}
