package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.MotherNatureLimitPlusTwo;

/**
 * Class to implement card 3 to add two additional movements to mother nature
 */
public class CharacterCard3 extends CharacterCard {

    /**
     * Game class of the game
     */
    private final ExpertGame game;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     */
    CharacterCard3(ExpertGame game) {
        super(CharacterCardsType.CARD3.getCost(), CharacterCardsType.CARD3.getDescription());
        this.game = game;
    }

    @Override
    public void effect() {
        game.getModel().setMotherNatureLimitStrategy(new MotherNatureLimitPlusTwo());
        game.effectEpilogue(this);
        this.setAsUsed();    }
}
