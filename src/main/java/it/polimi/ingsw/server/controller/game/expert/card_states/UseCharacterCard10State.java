package it.polimi.ingsw.server.controller.game.expert.card_states;

import it.polimi.ingsw.network.messages.responses.ErrorCode;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.controller.game.expert.cards.CharacterCard10;
import it.polimi.ingsw.server.controller.game.states.GameState;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;

/**
 * Class that implements the state where the current player can use the card 10
 */
public class UseCharacterCard10State extends UseCharacterCardState {

    /**
     * Model of the game
     */
    private final GameModel model;

    /**
     * Student taken from the dining room of the current player
     */
    private PawnType studentFromDiningRoom = null;

    /**
     * Student taken from the entrance of the current player
     */
    private PawnType studentFromEntrance = null;

    /**
     * Number of students that have been swapped
     */
    private int numberOfStudentsSwapped = 0;

    /**
     * Constructor of the class. Saves the game, the state before this one and the card used
     * @param game game class of the game
     * @param card card used in the state
     */
    public UseCharacterCard10State(ExpertGame game, GameState originState, CharacterCard10 card){
        super(game, originState, card);
        this.model = game.getModel();
    }

    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition) throws NotValidOperationException, NotValidArgumentException {
        if(originPosition.isLocation(Location.NONE)){
            //Send location NONE to stop swapping students
            // EPILOGUE
            finalizeCardUsed();
            returnBack();
            return;
        }
        if(originPosition.isLocation(Location.ENTRANCE)){
            takeFromEntrance(color);
        }
        else {
            if(originPosition.isLocation(Location.DINING_ROOM)){
                takeFromDiningRoom(color);
            }
            else{
                throw new NotValidOperationException("Choose a student from the entrance or the dining room");
            }
        }

        if(studentFromEntrance!=null && studentFromDiningRoom!=null){
            //Both students have been chosen
            swapStudent();
        }
    }

    /**
     * Swaps the students chosen from the dining room and from the entrance of the current player
     */
    private void swapStudent() {
        try {
            //Remove students from dining room and entrance
            model.getCurrentPlayer().removeStudentFromEntrance(studentFromEntrance);
            model.getCurrentPlayer().removeStudentFromDiningRoom(studentFromDiningRoom);
            //Swap students and add them to dining room and entrance
            model.getCurrentPlayer().addStudentToDiningRoom(studentFromEntrance);
            model.getCurrentPlayer().addStudentToEntrance(studentFromDiningRoom);
        } catch (NotEnoughStudentException | ReachedMaxStudentException e) {
            e.printStackTrace();// Should not happen as it is controlled before
        }

        numberOfStudentsSwapped ++;
        studentFromDiningRoom = null;
        studentFromEntrance = null;

        if (numberOfStudentsSwapped == 2){
            // EPILOGUE
            finalizeCardUsed();
            returnBack();
        }
    }

    /**
     * Controls the student chosen is in the entrance and that the table of that color in the dining room is not full
     * and saves the student
     * @param color color of the student to be taken
     * @throws NotValidArgumentException If the student is not present in the entrance or if the table in the dining room
     * of the chosen color is full already
     */
    private void takeFromEntrance(PawnType color) throws NotValidArgumentException {
        if (model.getCurrentPlayer().getStudentsInEntrance().getNumOf(color) <= 0) {
            throw new NotValidArgumentException(ErrorCode.STUDENT_NOT_PRESENT);
        }
        if (model.getCurrentPlayer().getNumStudentOf(color) == 10){
            throw new NotValidArgumentException(ErrorCode.DININGROOM_FULL);
        }
        studentFromEntrance = color;
    }

    /**
     * Controls the student chosen is in the dining room and saves it
     * @param color student taken
     * @throws NotValidArgumentException if the student is not present in the dining room
     */
    private void takeFromDiningRoom(PawnType color) throws NotValidArgumentException{
        if(model.getCurrentPlayer().getNumStudentOf(color) <= 0) {
            throw new NotValidArgumentException(ErrorCode.STUDENT_NOT_PRESENT);
        }
        studentFromDiningRoom = color;
    }

    @Override
    public StateType getType() {
        return StateType.USE_CHARACTER_CARD10_STATE;
    }
}
