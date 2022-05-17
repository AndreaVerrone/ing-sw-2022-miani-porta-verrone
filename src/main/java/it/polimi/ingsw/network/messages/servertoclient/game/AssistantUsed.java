package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ConnectionHandler;
import it.polimi.ingsw.network.messages.responses.ResponseMessage;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.model.player.Assistant;

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
    public void processMessage(ConnectionHandler client) {
        client.sendMessage(ResponseMessage.newSuccess(this));
        // TODO: 11/05/2022 update the view
    }

}
