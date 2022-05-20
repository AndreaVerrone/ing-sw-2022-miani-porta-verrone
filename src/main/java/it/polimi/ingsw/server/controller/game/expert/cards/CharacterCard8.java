package it.polimi.ingsw.server.controller.game.expert.cards;

import it.polimi.ingsw.server.controller.game.expert.CharacterCard;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.controller.game.expert.card_states.UseCharacterCard8State;

/**
 * Class to implement card 8 to calculate the influence without counting a chosen student color
 */
public class CharacterCard8 extends CharacterCard {
    /**
     * Game class
     */
    private final ExpertGame game;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     */
    CharacterCard8(ExpertGame game) {
        super(CharacterCardsType.CARD8);
        this.game = game;
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard8State(game, game.getState(), this));
    }
}
