package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.MotherNatureLimitPlusTwo;

/**
 * Class to implement card 3 to add two additional movements to mother nature
 */
public class CharacterCard3 extends CharacterCard {

    /**
     * Game class of the game
     */
    private final Game game;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     */
    CharacterCard3(Game game) {
        super(1, "Move mother nature up to two additional islands");
        this.game = game;
    }

    @Override
    public void effect() {
        game.getModel().setMotherNatureLimitStrategy(new MotherNatureLimitPlusTwo());
        game.effectEpilogue(this);
        this.setAsUsed();    }
}
