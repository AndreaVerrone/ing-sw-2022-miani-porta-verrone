package it.polimi.ingsw.controller;

public class CharacterCard4 extends CharacterCard{

    /**
     * This is the Game class
     * @see Game
     */
    private final Game game;

    /**
     * Creates a new character card.
     * @param game the Game class
     */
    CharacterCard4(Game game) {
        super(3, "allow the player to choose an island on which compute influence");
        this.game=game;
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard4State(game,game.getState(),this));
    }
}
