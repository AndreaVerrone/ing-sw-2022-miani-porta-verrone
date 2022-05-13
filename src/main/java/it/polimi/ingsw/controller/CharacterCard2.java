package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CheckProfessorCharacter;

public class CharacterCard2 extends CharacterCard{

    /**
     * This is the Game class
     * @see Game
     */
    Game game;

    /**
     * Creates a new character card with the specified initial cost, the description
     * and the game passed as a parameter.
     * @param cost              the initial cost of the card
     * @param effectDescription the description of the effect of this card
     * @param game              the Game class
     */
    CharacterCard2(int cost, String effectDescription, Game game) {
        super(cost, effectDescription);
        this.game=game;
    }

    @Override
    public void effect() {
        game.getModel().setCheckProfessorStrategy(new CheckProfessorCharacter(game.getModel()));
        effectEpilogue();
    }
}
