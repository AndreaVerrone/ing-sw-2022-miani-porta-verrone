package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.Assistant;

/**
 * The interface of the Game class
 */
public interface IGame {

    /**
     * Method to use an assistant card
     *
     * @param assistant is the assistant card to be played
     * @throws NotValidOperationException if this method has been invoked in a state in which
     *                                    this operation is not supported
     * @throws NotValidArgumentException  if has been passed an assistant card that cannot be used,
     *                                    or it is not present in the player's deck
     */
    void useAssistant(Assistant assistant) throws NotValidOperationException, NotValidArgumentException;

    /**
     * Method to move a student from the entrance to an island
     *
     * @param student  student color to move
     * @param islandID island ID to where move the student
     * @throws NotValidOperationException if this method has been invoked in a state in which
     *                                    this operation is not supported
     * @throws NotValidArgumentException  if the student to move is not present in entrance
     *                                    or the island doesn't exist
     */
    void moveStudentToIsland(PawnType student, int islandID)
            throws NotValidOperationException, NotValidArgumentException;

    /**
     * Method to move a student from the entrance to the dining room
     *
     * @param student student color to move
     * @throws NotValidOperationException if this method has been invoked in a state in which
     *                                    this operation is not supported
     * @throws NotValidArgumentException  if the student is not present in entrance or it is present,
     *                                    but the table for the students of that color is full
     */
    void moveStudentToDiningRoom(PawnType student)
            throws NotValidOperationException, NotValidArgumentException;

    /**
     * Method to move mother nature of a certain number of islands
     *
     * @param positions number of islands to move on mother nature
     * @throws NotValidOperationException if this method has been invoked in a state in which
     *                                    this operation is not supported
     * @throws NotValidArgumentException  if the position is not positive, or it is not
     *                                    compliant with the rules of the game
     */
    void moveMotherNature(int positions) throws NotValidOperationException, NotValidArgumentException;

    /**
     * Method to get all the students from a chosen cloud and put them in the entrance
     *
     * @param cloudID ID of the cloud from which get the students
     * @throws NotValidOperationException if this method has been invoked in a state in which
     *                                    this operation is not supported
     * @throws NotValidArgumentException  if the cloud passed as a parameter is empty
     */
    void takeFromCloud(int cloudID) throws NotValidOperationException, NotValidArgumentException;
}
