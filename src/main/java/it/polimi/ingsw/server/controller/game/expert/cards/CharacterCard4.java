package it.polimi.ingsw.server.controller.game.expert.cards;

import it.polimi.ingsw.server.controller.game.expert.CharacterCard;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.controller.game.expert.card_states.UseCharacterCard4State;

public class CharacterCard4 extends CharacterCard {

    /**
     * This is the Game class
     * @see ExpertGame
     */
    private final ExpertGame game;

    /**
     * Creates a new character card.
     * @param game the Game class
     */
    CharacterCard4(ExpertGame game) {
        super(CharacterCardsType.CARD4);
        this.game=game;
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard4State(game,game.getState(),this));
    }
}
