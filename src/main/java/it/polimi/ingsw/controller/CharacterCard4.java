package it.polimi.ingsw.controller;

public class CharacterCard4 extends CharacterCard{

    /**
     * This is the Game class
     * @see ExpertGame
     */
    private final ExpertGame game;

    /**
     * Creates a new character card.
     * @param game the Game class
     */
    CharacterCard4(ExpertGame game) {
        super(CharacterCardsType.CARD4.getCost(), CharacterCardsType.CARD4.getDescription());
        this.game=game;
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard4State(game,game.getState(),this));
    }
}
