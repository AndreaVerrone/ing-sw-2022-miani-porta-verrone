package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.Assistant;

/**
 * This interface collects all the action that can be made by the current player during its turn
 * both in the planning and in the action phase.
 */
public interface State {

    /**
     * This method allows the current player to use the assistant card specified
     * in the parameter.
     * <p>
     * Note that the player cannot use the same assistant card played by another player during the
     * same round.
     * This can be done only in the rare situation in which it has in hand only cards played by another one.
     * In this case this player will play after the player has played the same card before.
     * @param assistant is the assistant card to be played by player
     */
    public void useAssistant(Assistant assistant);

    /**
     * This method allows the current player to move one student
     * (of the specified type in the parameters) from the entrance of its school board
     * to the island passed as input.
     * @param student is the color of the student to move
     * @param islandID is ID of the island on which put the student
     */
    public void moveStudentToIsland(PawnType student, int islandID);

    /**
     * This method allows the current player to move one student
     * (of the specified type in the parameters) from the entrance of its school board
     * to its dining room.
     * @param student is the color of the student to move
     */
    public void moveStudentToDiningRoom(PawnType student);

    /**
     * This method allows the current player to move mother nature of a {@code position} number of steps forward.
     * @param positions is the number of step to move mother nature
     */
    public void moveMotherNature(int positions);

    /**
     * This method allows the current player to move the students
     * from the cloud specified in input to the entrance of its school board.
     * @param cloudID is the ID of the cloud from which take the students
     */
    public void takeFromCloud(int cloudID);
}
