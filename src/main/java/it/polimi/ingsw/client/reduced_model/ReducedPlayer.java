package it.polimi.ingsw.client.reduced_model;

import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * A simplified version of a school board, used to store and use the information client side
 */
public class ReducedPlayer implements Serializable {

    /**
     * the owner of the school board.
     */
    private final String owner;

    /**
     * the students at the entrance.
     */
    private StudentList studentsInEntrance;

    /**
     * the professors in the school board.
     */
    private Collection<PawnType> professors;

    /**
     * the students in the dining room.
     */
    private StudentList studentsInDiningRoom;

    /**
     * the color of the tower.
     */
    private final TowerType towerType;

    /**
     * the number of the tower.
     */
    private int towerNumber;

    /**
     * The last assistant used by this player
     */
    private Assistant lastAssistantUsed;

    /**
     * the number of coins in the school board.
     */
    private int coinNumber;

    /**
     * Wizard chosen by the player
     */
    private final Wizard wizard;

    /**
     * it will create a simplified version of a school board, used to store and use the information client side
     * @param owner the owner of the school board
     * @param studentsInEntrance the students at the entrance
     * @param professors the professors in the school board
     * @param studentsInDiningRoom the students in the dining room
     * @param towerType the color of the tower
     * @param towerNumber the number of the tower
     * @param lastAssistantUsed the last assistant used by this player
     * @param coinNumber the number of coins in the school board
     * @param wizard wizard chosen by the player
     */
    public ReducedPlayer(
            String owner,
            StudentList studentsInEntrance,
            Collection<PawnType> professors,
            StudentList studentsInDiningRoom,
            TowerType towerType,
            int towerNumber,
            Assistant lastAssistantUsed,
            int coinNumber, Wizard wizard) {
        this.owner = owner;
        this.studentsInEntrance = studentsInEntrance;
        this.professors = new ArrayList<>(professors);
        this.studentsInDiningRoom = studentsInDiningRoom;
        this.towerType = towerType;
        this.towerNumber = towerNumber;
        this.lastAssistantUsed = lastAssistantUsed;
        this.coinNumber = coinNumber;
        this.wizard = wizard;
    }

    // GETTER

    public String getOwner() {
        return owner;
    }
    public StudentList getStudentsInEntrance() {
        return studentsInEntrance.clone();
    }

    public Collection<PawnType> getProfessors() {
        return new ArrayList<>(professors);
    }

    public StudentList getStudentsInDiningRoom() {
        return studentsInDiningRoom.clone();
    }

    public TowerType getTowerType() {
        return towerType;
    }

    public int getTowerNumber() {
        return towerNumber;
    }

    public Assistant getLastAssistantUsed() {
        return lastAssistantUsed;
    }

    public int getCoinNumber() {
        return coinNumber;
    }

    public Wizard getWizard() {
        return wizard;
    }

    // SETTER

    public void setStudentsInEntrance(StudentList studentsInEntrance) {
        this.studentsInEntrance = studentsInEntrance;
    }

    public void setProfessors(Collection<PawnType> professors) {
        this.professors = professors;
    }

    public void setStudentsInDiningRoom(StudentList studentsInDiningRoom) {
        this.studentsInDiningRoom = studentsInDiningRoom;
    }

    public void setTowerNumber(int towerNumber) {
        this.towerNumber = towerNumber;
    }

    public void setLastAssistantUsed(Assistant lastAssistantUsed) {
        this.lastAssistantUsed = lastAssistantUsed;
    }

    public void setCoinNumber(int coinNumber) {
        this.coinNumber = coinNumber;
    }
}
