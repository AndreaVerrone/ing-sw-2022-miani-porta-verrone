package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CheckProfessorCharacter;

public class CharacterCard2 extends CharacterCard{

    /**
     * This is the Game class
     * @see Game
     */
    Game game;

    /**
     * Creates a new character card
     * @param game the Game class
     */
    CharacterCard2(Game game) {
        super(
                2,
                "allow a player to control a professor even if he has the same number " +
                                "of student of the player that controls it in that moment."
        );
        this.game=game;
    }

    @Override
    public void effect() {
        game.getModel().setCheckProfessorStrategy(new CheckProfessorCharacter(game.getModel()));
        effectEpilogue();
    }
}
