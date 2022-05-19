package it.polimi.ingsw.controller;

public class CharacterCard12 extends CharacterCard{

    /**
     * This is the Game class
     * @see ExpertGame
     */
    private final ExpertGame game;

    /**
     * Creates a new character card.
     * @param game the Game class
     */
    CharacterCard12(ExpertGame game) {
        super(CharacterCardsType.CARD12);
        this.game=game;
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard12State(game,game.getState(),this));
    }
}
