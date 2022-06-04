package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.player.Assistant;

import java.util.List;

/**
 * A message sent from server to the client connected to a game to indicate
 * what is the actual deck in his hand.
 */
public class DeckChanged extends ServerCommandNetMsg {

    /**
     * the list of the assistants in the deck.
     */
    private final List<Assistant> assistantsList;

    /**
     * the constructor of the class
     * @param assistantsList the list of the assistants in the deck
     */
    public DeckChanged(List<Assistant> assistantsList) {
        this.assistantsList = assistantsList;
    }

    /**
     * A method used to process this message.
     * <p>
     * This method runs in the client.
     *
     * @param client the client that receives this message
     */
    @Override
    public void processMessage(ClientController client) {
        client.setAssistantsList(assistantsList);
    }
}
