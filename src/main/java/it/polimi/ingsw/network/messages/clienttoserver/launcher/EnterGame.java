package it.polimi.ingsw.network.messages.clienttoserver.launcher;

import it.polimi.ingsw.network.messages.clienttoserver.ClientCommandNetMsg;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;

/**
 * Message sent from the client to the server to indicate the will of the user to enter a game
 */
public class EnterGame extends ClientCommandNetMsg {

    /**
     * The nickname chosen by the user
     */
    private final String nickname;

    /**
     * The unique ID of the game he wants to join
     */
    private final String gameID;

    /**
     * Creates a new message to enter the specified game with the provided nickname.
     *
     * @param nickname the nickname chosen by the user
     * @param gameID   the id of the game to enter
     */
    public EnterGame(String nickname, String gameID) {
        this.nickname = nickname;
        this.gameID = gameID;
    }

    @Override
    protected void normalProcess(ClientHandler clientInServer)
            throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().enterGame(nickname, gameID);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }
}
