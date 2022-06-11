package it.polimi.ingsw.client.reduced_model;

import it.polimi.ingsw.server.model.utils.StudentList;

/**
 * A simplified version of a cloud, used to store and use the information client side
 * @param ID the unique ID of the cloud
 * @param students the students on the cloud
 */
public record ReducedCloud(int ID, StudentList students) {
}
