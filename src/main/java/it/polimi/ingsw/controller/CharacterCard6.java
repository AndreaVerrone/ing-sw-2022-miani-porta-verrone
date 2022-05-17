package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ComputeInfluenceWithNoTowers;

/**
 * Class to implement card 6 to calculate the influence without counting the towers
 */
public class CharacterCard6 extends CharacterCard{

    /**
     * Game class of the game
     */
    private final ExpertGame game;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     */
    CharacterCard6(ExpertGame game) {
        super(CharacterCardsType.CARD6.getCost(), CharacterCardsType.CARD6.getDescription());
        this.game = game;
    }

    @Override
    public void effect() {
        game.getModel().setComputeInfluenceStrategy(new ComputeInfluenceWithNoTowers());
        game.effectEpilogue(this);
        this.setAsUsed();
    }
}
