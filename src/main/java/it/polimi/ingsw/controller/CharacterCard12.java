package it.polimi.ingsw.controller;

public class CharacterCard12 extends CharacterCard{

    /**
     * This is the Game class
     * @see Game
     */
    private final Game game;

    /**
     * Creates a new character card.
     * @param game the Game class
     */
    CharacterCard12(Game game) {
        super(3, "allow to remove 3 student of one color from the dining room of all players");
        this.game=game;
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard12State(game,game.getState(),this));
    }
}
