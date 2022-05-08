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
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if has been passed an assistant card that cannot be used,
     *                                   or it is not present in the player's deck
     */
    default public void useAssistant(Assistant assistant)throws NotValidOperationException,NotValidArgumentException{
        throw new NotValidOperationException();
    }

    /**
     * This method allows the current player to move one student
     * (of the specified type in the parameters) from the entrance of its school board
     * to the island passed as input.
     * @param student is the color of the student to move
     * @param islandID is ID of the island on which put the student
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the student to move is not present in entrance
     */
    default public void moveStudentToIsland(PawnType student, int islandID)throws NotValidOperationException,NotValidArgumentException{
        throw new NotValidOperationException();
    }

    /**
     * This method allows the current player to move one student
     * (of the specified type in the parameters) from the entrance of its school board
     * to its dining room.
     * @param student is the color of the student to move
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the student is not present in entrance or it is present, but the the table for the
     *                                   students of that color is full
     */
    default public void moveStudentToDiningRoom(PawnType student)throws NotValidOperationException,NotValidArgumentException {
        throw new NotValidOperationException();
    }

    /**
     * This method allows the current player to move mother nature of a {@code position} number of steps forward.
     * @param positions is the number of step to move mother nature
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the position is negative, or it is not compliant with the rules of the game
     */
    default public void moveMotherNature(int positions)throws NotValidOperationException,NotValidArgumentException{
        throw new NotValidOperationException();
    }

    /**
     * This method allows the current player to move the students
     * from the cloud specified in input to the entrance of its school board.
     * @param cloudID is the ID of the cloud from which take the students
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the cloud passed as a parameter is empty
     */
    default public void takeFromCloud(int cloudID)throws NotValidOperationException,NotValidArgumentException{
        throw new NotValidOperationException();
    }

    /**
     * This method allow to use the character card passed as a parameter.
     * @param characterCard the character card to use
     * @throws NotValidArgumentException if the character card cannot be used because the player cannot pay
     * for the usage, the state of the game do not allow the usage or the player has already used a character card
     * during its turn.
     */
    default void useCharacterCard(CharacterCard characterCard) throws NotValidOperationException{
        characterCard.effect();
    }

    /**
     * This method allow to move one student from the character card 1
     * to the island passed as a parameter.
     * After the calling of the method, if the student bag is not empty,
     * a student (taken from the bag) will be added on the character card.
     * @param pawnType color of the student to move to island
     * @param islandID island on which put the student
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the student or the island does not exist
     */
    default public void moveFromCardToIsland(PawnType pawnType, int islandID) throws NotValidOperationException, NotValidArgumentException{
        throw new NotValidOperationException();
    }

}
