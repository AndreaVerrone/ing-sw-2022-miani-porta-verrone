package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.strategies.influence.ComputeInfluenceWithTwoAdditional;

/**
 * Class to implement the card 7 to calculate the influence with to additional points to the current player
 */
public class CharacterCard7 extends CharacterCard{

    /**
     * Game class pf the game
     */
    private final ExpertGame game;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     */
    CharacterCard7(ExpertGame game) {
        super(CharacterCardsType.CARD7);
        this.game = game;
    }

    @Override
    public void effect() {
        game.getModel().setComputeInfluenceStrategy(new ComputeInfluenceWithTwoAdditional(game.getModel()));
        game.effectEpilogue(this);
        this.setAsUsed();    }
}
