package it.polimi.ingsw.client.reduced_model;

import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.utils.StudentList;

import java.io.Serializable;

/**
 * A reduced version of a character card
 */
public class ReducedCharacter implements Serializable {

    /**
     * The type of this character card
     */
    private final CharacterCardsType type;

    /**
     * The cost of this character card
     */
    private int cost;
    /**
     * If this character card has been used before
     */
    private boolean used;
    /**
     * The students on this character card, if any.
     * If this card does not support students, default immutable value is {@code null}
     */
    private StudentList studentList = null;
    /**
     * The number of bans on this character card.
     * If this card does not support bans, default immutable value is {@code null}
     */
    private Integer bans = null;

    /**
     * Creates a reduced version of a character card that cannot contain students or bans using the provided parameters
     * @param type the type of the character card
     * @param used if the character card has been used
     */
    public ReducedCharacter(CharacterCardsType type, boolean used) {
        this.type = type;
        this.used = used;
        this.cost = type.getCost();
        if (used)
            this.cost++;
    }

    /**
     * Creates a reduced version of a character card that contain students but not support bans using the provided parameters
     * @param type the type of the character card
     * @param used if the character card has been used
     * @param studentList the students on the card
     */
    public ReducedCharacter(CharacterCardsType type, boolean used, StudentList studentList) {
        this(type, used);
        this.studentList = studentList;
    }

    /**
     * Creates a reduced version of a character card that contain bans but not support students using the provided parameters
     * @param type the type of the character card
     * @param used if the character card has been used
     * @param bans the number of bans on the card
     */
    public ReducedCharacter(CharacterCardsType type, boolean used, Integer bans) {
        this(type, used);
        this.bans = bans;
    }

    public CharacterCardsType getType() {
        return type;
    }

    public String getDescription() {
        return type.getDescription();
    }

    public int getCost() {
        return cost;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed() {
        if (used)
            return;
        this.used = true;
        cost++;
    }

    public StudentList getStudentList() {
        return studentList;
    }

    public void setStudentList(StudentList studentList) {
        if (this.studentList != null)
            this.studentList = studentList;
    }

    public Integer getBans() {
        return bans;
    }

    public void setBans(int bans) {
        if (this.bans != null)
            this.bans = bans;
    }
}
