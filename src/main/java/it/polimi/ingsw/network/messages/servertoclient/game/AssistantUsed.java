package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.view.ClientView;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.player.Assistant;

/**
 * A message sent from server to all client connected to a game to indicate that the current
 * player used an assistant
 */
public class AssistantUsed extends ServerCommandNetMsg {

    /**
     * Nickname of the player that used the assistant card
     */
    private final String nickname;

    /**
     * The assistant used.
     */
    private final Assistant assistant;

    /**
     * Creates a new message to indicate the usage of a particular assistant from the current player.
     * @param assistant the assistant used
     */
    public AssistantUsed(String nickname, Assistant assistant) {
        this.nickname = nickname;
        this.assistant = assistant;
    }

    @Override
    public void processMessage(ClientView client) {
        client.lastAssistantUsedChanged(nickname,assistant);
    }

}
