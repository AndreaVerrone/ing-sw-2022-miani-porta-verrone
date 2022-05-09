package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.MotherNatureLimitPlusTwo;

public class CharacterCard3 extends CharacterCard {

    private final Game game;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     * @param cost              the initial cost of the card
     * @param effectDescription the description of the effect of this card
     */
    CharacterCard3(int cost, String effectDescription, Game game) {
        super(cost, effectDescription);
        this.game = game;
    }

    @Override
    public void effect() {
        game.getModel().setMotherNatureLimitStrategy(new MotherNatureLimitPlusTwo());
        this.effectEpilogue();
    }
}
