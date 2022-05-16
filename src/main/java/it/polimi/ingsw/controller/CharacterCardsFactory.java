package it.polimi.ingsw.controller;

/**
 * Class to implement factory pattern to create new character cards
 */
public class CharacterCardsFactory {

    /**
     * @see ExpertGame
     */
    private final ExpertGame game;

    /**
     * Constructor of the class
     */
    public CharacterCardsFactory(ExpertGame game) {
        this.game = game;
    }

    /**
     * Returns an instance of the card asked
     * @param card card typed asked
     * @return new instance of the card asked
     */
    public CharacterCard chooseCard(CharacterCardsType card) {
        return switch (card) {
            case CARD1 -> new CharacterCard1(game);
            case CARD2 -> new CharacterCard2(game);
            case CARD3 -> new CharacterCard3(game);
            case CARD4 -> new CharacterCard4(game);
            case CARD5 -> new CharacterCard5(game);
            case CARD6 -> new CharacterCard6(game);
            case CARD7 -> new CharacterCard7(game);
            case CARD8 -> new CharacterCard8(game);
            case CARD9 -> new CharacterCard9(game);
            case CARD10 -> new CharacterCard10(game);
            case CARD11 -> new CharacterCard11(game);
            case CARD12 -> new CharacterCard12(game);
        };
    }
}