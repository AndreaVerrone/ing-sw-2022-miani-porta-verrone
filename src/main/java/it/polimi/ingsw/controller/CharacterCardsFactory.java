package it.polimi.ingsw.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Class to implement factory pattern to create new character cards
 */
public class CharacterCardsFactory {

    /**
     * Returns an instance of the card asked
     * @param card card typed asked
     * @return new instance of the card asked
     */
    private static CharacterCard chooseCard(CharacterCardsType card, ExpertGame game) {
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

    /**
     * Create a map of character cards of the specified number
     * @param numberOfRandomCards number of cards of the set
     * @param game expert game
     */
    public static Map<CharacterCardsType, CharacterCard> createRandomCards(int numberOfRandomCards, ExpertGame game){
        Map<CharacterCardsType, CharacterCard> cards = new HashMap<>(numberOfRandomCards,1);
        while (cards.size() < numberOfRandomCards){
            int random = new Random().nextInt(CharacterCardsType.values().length);
            CharacterCardsType cardType = CharacterCardsType.values()[random];
            cards.putIfAbsent(cardType, chooseCard(cardType, game));
        }
        return cards;
    }
}