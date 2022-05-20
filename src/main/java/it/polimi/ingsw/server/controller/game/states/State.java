package it.polimi.ingsw.server.controller.game.states;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.CharacterCard;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.utils.PawnType;

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
    default void useAssistant(Assistant assistant)throws NotValidOperationException,NotValidArgumentException{
        throw new NotValidOperationException();
    }

    /**
     * This method allows the current player to move mother nature of a {@code position} number of steps forward.
     * @param positions is the number of step to move mother nature
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the position is negative, or it is not compliant with the rules of the game
     */
    default void moveMotherNature(int positions)throws NotValidOperationException,NotValidArgumentException{
        throw new NotValidOperationException();
    }

    /**
     * This method allows the current player to move the students
     * from the cloud specified in input to the entrance of its school board.
     * @param cloudID is the ID of the cloud from which take the students
     * @throws NotValidOperationException if this method has been invoked in a state in which this operation is not supported
     * @throws NotValidArgumentException if the cloud passed as a parameter is empty
     */
    default void takeFromCloud(int cloudID)throws NotValidOperationException,NotValidArgumentException{
        throw new NotValidOperationException();
    }

    /**
     * This method allows to select a student (of the PawnType specified in the parameter) that comes from the position
     * (also specified in the parameters).
     * @param color the {@code PawnType} of the student
     * @param originPosition the {@code Position} from where take the student
     * @throws NotValidOperationException if the position is not the one that was supposed to be in the considered state
     * @throws NotValidArgumentException if the student is not present in the specified location
     */
    default void choseStudentFromLocation(PawnType color, Position originPosition)throws NotValidOperationException, NotValidArgumentException{
        throw new NotValidOperationException();
    }

    /**
     * This method allows to choose a destination on which operate based on the state.
     * @param destination the Position
     * @throws NotValidOperationException if the position is not the one that was supposed to be in the considered state
     * @throws NotValidArgumentException if the
     */
    default void chooseDestination(Position destination)throws NotValidOperationException,NotValidArgumentException{
        throw new NotValidOperationException();
    }

    /**
     * This method allow to use the character card passed as a parameter.
     * @param characterCard the character card to use
     * @throws NotValidOperationException if the character card cannot be used because the player cannot pay
     * for the usage, the state of the game do not allow the usage or the player has already used a character card
     * during its turn.
     */
    default void useCharacterCard(CharacterCard characterCard) throws NotValidOperationException {
        characterCard.effect();
    }
}
