package it.polimi.ingsw.server.controller;

/**
 * Class to implement the card 10 to swap up to two chosen students from the entrance with the dining room
 */
public class CharacterCard10 extends CharacterCard{

    /**
     * Game class
     */
    private final ExpertGame game;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     */
    CharacterCard10(ExpertGame game) {
        super(CharacterCardsType.CARD10);
        this.game = game;
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard10State(game, game.getState(), this));
    }
}
