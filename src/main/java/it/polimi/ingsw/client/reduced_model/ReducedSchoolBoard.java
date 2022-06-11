package it.polimi.ingsw.client.reduced_model;

import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.Collection;

/**
 * A simplified version of a school board, used to store and use the information client side
 * @param owner the owner of the school board
 * @param studentsInEntrance the students at the entrance
 * @param professors the professors in the school board
 * @param studentsInDiningRoom the students in the dining room
 * @param towerType the color of the tower
 * @param towerNumber the number of the tower
 * @param coinNumber the number of coins in the school board
 */
public record ReducedSchoolBoard(
        String owner,
        StudentList studentsInEntrance,
        Collection<PawnType> professors,
        StudentList studentsInDiningRoom,
        TowerType towerType,
        int towerNumber,
        int coinNumber) {
}
