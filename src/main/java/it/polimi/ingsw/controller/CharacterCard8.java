package it.polimi.ingsw.controller;

public class CharacterCard8 extends  CharacterCard{
    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     * @param cost              the initial cost of the card
     * @param effectDescription the description of the effect of this card
     */
    CharacterCard8(int cost, String effectDescription, Game game) {
        super(cost, effectDescription);
        this.game = game;
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard8State(game, game.getState(), this));
    }
}