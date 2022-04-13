package it.polimi.ingsw.model.player;

/**
 * This Enumeration represent the possible types of assistants cards
 */
public enum Assistant {

    CARD_1(1,1),
    CARD_2(2,1),
    CARD_3(3,2),
    CARD_4(4,2),
    CARD_5(5,3),
    CARD_6(6,3),
    CARD_7(7,4),
    CARD_8(8,4),
    CARD_9(9,5),
    CARD_10(10,5);

    /**
     * the attribute {@code value} contributes to determine
     * the order of the turn during the next phase action and
     * the first player in the next round
     *
     */
    private final int value;

    /**
     * the {@code movement} represent the maximum number of steps that mother nature can do
     */
    private final int movement;

    Assistant(int value, int movement) {
        this.value = value;
        this.movement = movement;
    }

    public int getValue(){
        return value;
    }

    public int getRangeOfMotion(){
        return movement;
    }
}