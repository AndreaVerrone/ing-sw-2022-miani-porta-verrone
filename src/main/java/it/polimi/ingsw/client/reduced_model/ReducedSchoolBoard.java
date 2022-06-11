package it.polimi.ingsw.client.reduced_model;

import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simplified version of a school board, used to store and use the information client side
 */
public class ReducedSchoolBoard{

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
     * the number of coins in the school board.
     */
    private int coinNumber;

    /**
     * it will create a simplified version of a school board, used to store and use the information client side
     * @param owner the owner of the school board
     * @param studentsInEntrance the students at the entrance
     * @param professors the professors in the school board
     * @param studentsInDiningRoom the students in the dining room
     * @param towerType the color of the tower
     * @param towerNumber the number of the tower
     * @param coinNumber the number of coins in the school board
     */
    public ReducedSchoolBoard(
            String owner,
            StudentList studentsInEntrance,
            Collection<PawnType> professors,
            StudentList studentsInDiningRoom,
            TowerType towerType,
            int towerNumber,
            int coinNumber) {
        this.owner = owner;
        this.studentsInEntrance = studentsInEntrance;
        this.professors = professors;
        this.studentsInDiningRoom = studentsInDiningRoom;
        this.towerType = towerType;
        this.towerNumber = towerNumber;
        this.coinNumber = coinNumber;
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

    public int getCoinNumber() {
        return coinNumber;
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

    public void setCoinNumber(int coinNumber) {
        this.coinNumber = coinNumber;
    }
}
