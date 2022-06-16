package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.utils.StudentList;

/**
 * A message sent from server to all client connected to a game to indicate that students were added on the card
 */
public class StudentsOnCardAdded extends ServerCommandNetMsg {

    /**
     * Card where the coin was added
     */
    private final CharacterCardsType card;

    /**
     * All the students on the card
     */
    private final StudentList studentsOnCard;

    /**
     * Creates a new message to inform all the players in a game that students were added on the card.
     * @param card Card where the coin was added
     * @param studentsOnCard the students on the card
     */
    public StudentsOnCardAdded(CharacterCardsType card, StudentList studentsOnCard){
        this.card = card;
        this.studentsOnCard = studentsOnCard;
    }

    @Override
    public void processMessage(ClientController client) {
        client.studentsOnCardChanged(card, studentsOnCard);
    }
}
