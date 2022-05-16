package it.polimi.ingsw.controller;

/**
 * Enum class with all the cards of the game, with cost and description
 */
public enum CharacterCardsType {
    CARD1(1,"allow to move one student from card to island"),
    CARD2(2, "allow a player to control a professor even if he has the same number " +
                    "of student of the player that controls it in that moment."),
    CARD3(1, "Move mother nature up to two additional islands"),
    CARD4(3, "allow to choose an island on which compute influence"),
    CARD5(2, "allow to choose an island on which put the ban"),
    CARD6(3, "Allows to  calculate the influence without counting the towers on the island"),
    CARD7(2, "Allows to to calculate the influence with to additional points to the current player"),
    CARD8(3, "Allows to calculate the influence without counting a chosen student color"),
    CARD9(1, "Allows to swap up to three chosen students from this card with the entrance of the player"),
    CARD10(1, "Allows to swap up to two chosen students from the entrance with the dining room"),
    CARD11(2, "allow to take one student from the card and put in the school board"),
    CARD12(3, "allow to remove 3 student of one color from the dining room of all players");

    /**
     * Cost of the card
     */
    private final int cost;

    /**
     * Description of the card
     */
    private final String description;

    /**
     * Constructor of the class
     * @param cost cost of the card
     * @param description description of the card
     */
    CharacterCardsType(int cost, String description) {
        this.cost = cost;
        this.description = description;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }
}
