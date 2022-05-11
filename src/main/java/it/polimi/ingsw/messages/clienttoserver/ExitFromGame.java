package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.messages.responses.Result;
import it.polimi.ingsw.server.ClientHandler;

/**
 * A message sent from client to server to comunicate the will of the player to leave the game
 */
public class ExitFromGame extends ClientCommandNetMsg{

    /**
     * The nickname of the user making this request
     */
    private final String nickname;

    /**
     * Creates a new request to the server to exit the game a player is currently in.
     * @param nickname the nickname of the player
     */
    public ExitFromGame(String nickname){
        this.nickname = nickname;
    }
    
    
    @Override
    public void processMessage(ClientHandler clientInServer) {
        try {
            clientInServer.getSessionController().exitFromGame(nickname);
            clientInServer.sendMessage(ResponseMessage.newSuccess(this));
        } catch (NotValidOperationException e) {
            clientInServer.sendMessage(new ResponseMessage(this, Result.INVALID_OPERATION, e.getErrorCode()));
        } catch (NotValidArgumentException e) {
            clientInServer.sendMessage(new ResponseMessage(this, Result.INVALID_ARGUMENT, e.getErrorCode()));
        }
    }

    @Override
    public void processResponse(ResponseMessage response) {
        // TODO: 10/05/2022 show the response to the client 
    }
}
