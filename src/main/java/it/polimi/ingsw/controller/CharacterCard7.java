package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ComputeInfluenceWithTwoAdditional;

/**
 * Class to implement the card 7 to calculate the influence with to additional points to the current player
 */
public class CharacterCard7 extends CharacterCard{

    private final Game game;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     * @param cost              the initial cost of the card
     * @param effectDescription the description of the effect of this card
     */
    CharacterCard7(int cost, String effectDescription, Game game) {
        super(cost, effectDescription);
        this.game = game;
    }

    @Override
    public void effect() {
        game.getModel().setComputeInfluenceStrategy(new ComputeInfluenceWithTwoAdditional(game.getModel()));
        this.effectEpilogue();
    }
}
