package it.polimi.ingsw.controller;

public class CharacterCard4 extends CharacterCard{

    /**
     * This is the Game class
     * @see Game
     */
    private final Game game;

    /**
     * Creates a new character card with the specified initial cost, the description
     * and the game passed as a parameter.
     * @param cost              the initial cost of the card
     * @param effectDescription the description of the effect of this card
     * @param game              the Game class
     */
    CharacterCard4(int cost, String effectDescription, Game game) {
        super(cost, effectDescription);
        this.game=game;
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard4State(game,game.getState(),this));
    }
}
