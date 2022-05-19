package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.CheckProfessorCharacter;

public class CharacterCard2 extends CharacterCard{

    /**
     * This is the Game class
     * @see ExpertGame
     */
    ExpertGame game;

    /**
     * Creates a new character card
     * @param game the Game class
     */
    CharacterCard2(ExpertGame game) {
        super(CharacterCardsType.CARD2);
        this.game=game;
    }

    @Override
    public void effect() {
        game.getModel().setCheckProfessorStrategy(new CheckProfessorCharacter(game.getModel()));
        game.effectEpilogue(this);
        this.setAsUsed();
    }
}
