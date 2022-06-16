package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.utils.StudentList;

/**
 * A message sent from server to all client connected to a game to indicate that the students
 * on an island has been changed
 */
public class StudentsOnIslandChanged extends ServerCommandNetMsg {

    /**
     * The id of the island on which the change happened.
     */
    private final int ID;

    /**
     * The actual student list on the island.
     */
    private final StudentList studentList;

    /**
     * the constructor of the class
     * @param ID The id of the island on which the change happened
     * @param studentList The actual student list on the island
     */
    public StudentsOnIslandChanged(int ID, StudentList studentList) {
        this.ID =ID;
        this.studentList = studentList;
    }

    /**
     * A method used to process this message.
     * <p>
     * This method runs in the client.
     *
     * @param client the client that receives this message
     */
    @Override
    public void processMessage(ClientController client) {
        client.updateStudents(ID,studentList);
    }
}
