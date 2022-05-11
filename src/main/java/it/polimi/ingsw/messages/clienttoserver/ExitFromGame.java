package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A message sent from client to server to comunicate the will of the player to leave the game
 */
public class ExitFromGame extends ClientCommandNetMsg {

    /**
     * The nickname of the user making this request
     */
    private final String nickname;

    /**
     * Creates a new request to the server to exit the game a player is currently in.
     *
     * @param nickname the nickname of the player
     */
    public ExitFromGame(String nickname) {
        this.nickname = nickname;
    }


    @Override
    protected void normalProcess(ClientHandler clientInServer)
            throws NotValidArgumentException, NotValidOperationException {
        clientInServer.getSessionController().exitFromGame(nickname);
        clientInServer.sendMessage(ResponseMessage.newSuccess(this));
    }

    @Override
    public void processResponse(ResponseMessage response) {
        // TODO: 10/05/2022 show the response to the client 
    }
}