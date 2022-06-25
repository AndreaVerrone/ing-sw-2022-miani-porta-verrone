package it.polimi.ingsw.network.messages.servertoclient;

import it.polimi.ingsw.client.view.ClientView;

/**
 * A message sent from server to all client connected to a game to notify that a player left the game
 */
public class PlayerLeftGame extends ServerCommandNetMsg{

    /**
     * The nickname of the player
     */
    private final String nicknamePlayer;

    /**
     * Creates a new message to notify all the clients connected to a game that a player left the game
     * @param nicknamePlayer the nickname of the player
     */
    public PlayerLeftGame(String nicknamePlayer) {
        this.nicknamePlayer = nicknamePlayer;
    }

    @Override
    public void processMessage(ClientView client) {
        client.notifyPlayerLeftGame(nicknamePlayer);
    }
}
