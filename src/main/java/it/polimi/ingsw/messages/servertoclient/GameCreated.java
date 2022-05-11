package it.polimi.ingsw.messages.servertoclient;

import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.messages.clienttoserver.EnterGame;
import it.polimi.ingsw.messages.responses.ResponseMessage;

import java.util.Scanner;

/**
 * Message sent from the server to the client to notify the creation of a new game
 */
public class GameCreated extends ServerCommandNetMsg{

    /**
     * The unique id of the game created.
     */
    private final String gameID;

    /**
     * Creates a new message to indicate the client that a new game has been created.
     * @param gameID the id of the new game
     */
    public GameCreated(String gameID){
        this.gameID = gameID;
    }

    @Override
    public void processMessage(ConnectionHandler client) {
        client.sendMessage(ResponseMessage.newSuccess(this));
        String nickname = "";
        // TODO: 09/05/2022 ask for a nickname
        // TODO: 09/05/2022 show game id
        client.sendMessage(new EnterGame(nickname, gameID));
    }

    @Override
    public void processResponse(ResponseMessage response) {

    }
}