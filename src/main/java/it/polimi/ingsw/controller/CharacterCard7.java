package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ComputeInfluenceWithTwoAdditional;

/**
 * Class to implement the card 7 to calculate the influence with to additional points to the current player
 */
public class CharacterCard7 extends CharacterCard{

    /**
     * Game class pf the game
     */
    private final Game game;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     */
    CharacterCard7(Game game) {
        super(2, "Allows to to calculate the influence with to additional points to the current player");
        this.game = game;
    }

    @Override
    public void effect() {
        game.getModel().setComputeInfluenceStrategy(new ComputeInfluenceWithTwoAdditional(game.getModel()));
        game.effectEpilogue(this);
        this.setAsUsed();    }
}
