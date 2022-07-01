package it.polimi.ingsw.network.messages.servertoclient.launcher;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;

/**
 * A message sent from server to a client that asked to resume a game to send him his
 * nickname and ID of the game he was playing
 */
public class UpdateNicknameGameID extends ServerCommandNetMsg {

    /**
     * The nickname of the player
     */
    private final String nickname;
    /**
     * The ID of the game he was playing
     */
    private final String gameID;

    /**
     * Creates a new message to send the nickname and ID of the game to a client that asked
     * to resume a game he was playing
     * @param nickname the nickname of the client
     * @param gameID the ID of the game he was playing
     */
    public UpdateNicknameGameID(String nickname, String gameID) {
        this.nickname = nickname;
        this.gameID = gameID;
    }

    @Override
    public void processMessage(ClientView client) {
        client.updateNicknameGameID(nickname, gameID);
    }
}
