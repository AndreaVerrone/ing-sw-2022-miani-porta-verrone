package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ComputeInfluenceWithNoTowers;

/**
 * Class to implement card 6 to calculate the influence without counting the towers
 */
public class CharacterCard6 extends CharacterCard{

    private final Game game;
    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     * @param cost              the initial cost of the card
     * @param effectDescription the description of the effect of this card
     */
    CharacterCard6(int cost, String effectDescription, Game game) {
        super(cost, effectDescription);
        this.game = game;
    }

    @Override
    public void effect() {
        game.getModel().setComputeInfluenceStrategy(new ComputeInfluenceWithNoTowers());
        this.effectEpilogue();
    }
}
