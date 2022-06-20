package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;

/**
 * A message sent from server to all client connected to a game to indicate that there is a coin on a character card.
 */
public class CoinOnCardAdded extends ServerCommandNetMsg {

    /**
     * Card where the coin was added
     */
    private final CharacterCardsType card;

    /**
     * Creates a new message to inform all the players in a game that there is a coin on a character card.
     * @param card Card where the coin was added
     */
    public CoinOnCardAdded(CharacterCardsType card){
        this.card = card;
    }

    @Override
    public void processMessage(ClientView client) {
        client.coinOnCardAdded(card);
    }
}
