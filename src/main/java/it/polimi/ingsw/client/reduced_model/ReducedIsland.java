package it.polimi.ingsw.client.reduced_model;

import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.io.Serializable;

/**
 * A simplified version of an island, used to store and use the information client side
 * @param ID the unique ID of the island
 * @param studentList the students on the island
 * @param tower the tower on the island
 * @param size the size of the island
 * @param ban the number of bans on the island, if any
 */
public record ReducedIsland(int ID, StudentList studentList, TowerType tower, int size, int ban) implements Serializable {
}
