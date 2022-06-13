package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.player.Assistant;

import java.util.Collection;

/**
 * A message sent from server to the client connected to a game to indicate
 * what is the actual deck in his hand.
 */
public class DeckChanged extends ServerCommandNetMsg {

    /**
     * It is the owner of the deck that it is changed
     */
    private final String player;

    /**
     * the list of the assistants in the deck.
     */
    private final Collection<Assistant> assistantsList;

    /**
     * the constructor of the class
     *
     * @param player the player that has the deck that has been changed
     * @param assistantsList the list of the assistants in the deck
     */
    public DeckChanged(String player, Collection<Assistant> assistantsList) {
        this.player = player;
        this.assistantsList = new ArrayList<>(assistantsList);
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
        client.setAssistantsList(assistantsList,player);
    }
}
