package it.polimi.ingsw.network.messages.servertoclient.launcher;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * Message sent from the server to the client to notify the creation of a new game
 */
public class GameCreated extends ServerCommandNetMsg {

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
    public void processMessage(ClientView client) {
        client.getScreenBuilder().build(ScreenBuilder.Screen.ASK_NICKNAME, gameID);
    }

}
