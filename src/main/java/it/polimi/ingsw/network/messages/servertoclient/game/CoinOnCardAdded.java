package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
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
     * True if the coin was added to the card
     */
    private final boolean coinOnCard;

    /**
     * Creates a new message to inform all the players in a game that there is a coin on a character card.
     * @param card Card where the coin was added
     * @param coinOnCard True if the coin was added to the card
     */
    public CoinOnCardAdded(CharacterCardsType card, boolean coinOnCard){
        this.card = card;
        this.coinOnCard = coinOnCard;
    }

    @Override
    public void processMessage(ClientController client) {
        //TODO: update view
    }
}
