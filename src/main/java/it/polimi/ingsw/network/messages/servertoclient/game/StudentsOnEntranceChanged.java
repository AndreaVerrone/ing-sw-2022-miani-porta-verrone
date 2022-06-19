package it.polimi.ingsw.network.messages.servertoclient.game;

import it.polimi.ingsw.client.ClientView;
import it.polimi.ingsw.network.messages.servertoclient.ServerCommandNetMsg;
import it.polimi.ingsw.server.model.utils.StudentList;

/**
 * A message sent from server to all client connected to a game to indicate that the students
 * at the entrance has been changed
 */
public class StudentsOnEntranceChanged extends ServerCommandNetMsg {

    /**
     * the player that has the school board on which the change has been happened.
     */
    private final String player;

    /**
     * the actual student list at the entrance.
     */
    private final StudentList studentList;

    /**
     * the constructor of the class
     * @param player the player that has the school board on which the change has been happened.
     * @param studentList the actual student list at the entrance.
     */
    public StudentsOnEntranceChanged(String player, StudentList studentList) {
        this.player = player;
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
    public void processMessage(ClientView client) {
        client.studentsOnEntranceChanged(player,studentList);

    }
}
