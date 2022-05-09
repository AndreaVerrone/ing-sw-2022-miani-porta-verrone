package it.polimi.ingsw.messages.clienttoserver;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.messages.responses.ErrorCode;
import it.polimi.ingsw.messages.responses.ResponseMessage;
import it.polimi.ingsw.messages.responses.Result;
import it.polimi.ingsw.server.ClientHandler;

/**
 * Message sent from the client to the server to indicate the will of the user to enter a game
 */
public class EnterGame extends ClientCommandNetMsg{

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
     * @param nickname the nickname chosen by the user
     * @param gameID the id of the game to enter
     */
    public EnterGame(String nickname, String gameID){
        this.nickname = nickname;
        this.gameID = gameID;
    }

    @Override
    public void processMessage(ClientHandler clientInServer) {
        try {
            clientInServer.getSessionController().enterGame(nickname, gameID);
            clientInServer.sendMessage(ResponseMessage.newSuccess(this));
        } catch (NotValidOperationException e) {
            clientInServer.sendMessage(new ResponseMessage(this, Result.INVALID_OPERATION, e.getErrorCode()));
        } catch (NotValidArgumentException e) {
            clientInServer.sendMessage(new ResponseMessage(this, Result.INVALID_ARGUMENT, e.getErrorCode()));
        }
    }

    @Override
    public void processResponse(ResponseMessage response) {
        if (response.isSuccess()){
            //TODO: notify the view of the success
            return;
        }
        ErrorCode errorCode = response.getErrorCode();
        if (response.getResult() == Result.INVALID_ARGUMENT){
            switch (errorCode) {
                case GAME_NOT_EXIST -> {
                    //TODO: notify view
                }
                case NICKNAME_TAKEN -> {
                    // TODO: 09/05/2022 ask for another nickname 
                }
            }
        } else {
            switch (errorCode){
                case GAME_IS_FULL -> {
                    // TODO: 09/05/2022 notify view
                }
                case GENERIC -> {
                    // TODO: 09/05/2022 notify view can't join
                }
            }
        }
    }
}
