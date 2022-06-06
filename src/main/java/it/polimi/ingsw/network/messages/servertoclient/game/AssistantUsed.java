package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.player.Assistant;

/**
 * A message sent from server to all client connected to a game to indicate that the current
 * player used an assistant
 */
public class AssistantUsed extends ServerCommandNetMsg {

    /**
     * The assistant used.
     */
    private final Assistant assistant;

    /**
     * Creates a new message to indicate the usage of a particular assistant from the current player.
     * @param assistant the assistant used
     */
    public AssistantUsed(Assistant assistant) {
        this.assistant = assistant;
    }

    @Override
    public void processMessage(ClientController client) {
        client.setAssistantsUsed(client.getNickNameCurrentPlayer(),assistant);
    }

}
