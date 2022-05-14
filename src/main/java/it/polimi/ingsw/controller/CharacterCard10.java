package it.polimi.ingsw.controller;

/**
 * Class to implement the card 10 to swap up to two chosen students from the entrance with the dining room
 */
public class CharacterCard10 extends CharacterCard{

    /**
     * Game class
     */
    private final Game game;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     * @param cost              the initial cost of the card
     * @param effectDescription the description of the effect of this card
     */
    CharacterCard10(int cost, String effectDescription, Game game) {
        super(cost, effectDescription);
        this.game = game;
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard10State(game, game.getState(), this));
    }
}
