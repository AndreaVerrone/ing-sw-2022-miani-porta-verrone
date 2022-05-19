package it.polimi.ingsw.server.model.strategies.check_professor;

import it.polimi.ingsw.server.model.PawnType;

/**
 * This is the interface that allow to implement the strategy pattern for
 * {@code checkProfessor(studentColor)} method
 */
public interface CheckProfessorStrategy {

    /**
     * This method check if after the adding of a student, of the {@code PawnType} specified in the parameter,
     * in the table of the dining room of the current player, the professor must be given to the player.
     * If it is yes the professor will be given to the current player and removed from the previous owner
     * if there was one, otherwise the professor will be just added to the current player.
     *
     * @param studentColor the {@code PawnType} of the student that has been added in the dining room
     */
    public void checkProfessor(PawnType studentColor);
}
