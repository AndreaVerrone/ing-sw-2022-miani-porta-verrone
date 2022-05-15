package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.ReachedMaxStudentException;

/**
 * Class that implements the state where the current player can use the card 10
 */
public class UseCharacterCard10State extends UseCharacterCardState implements State {

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
    public UseCharacterCard10State(Game game, State originState, CharacterCard10 card){
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
    private void swapStudent(){
        try {
            model.getCurrentPlayer().addStudentToDiningRoom(studentFromEntrance);
            model.getCurrentPlayer().addStudentToEntrance(studentFromDiningRoom);
        } catch (ReachedMaxStudentException e) {
            e.printStackTrace();//Not possible
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
     * Method to take a student from the entrance
     * @param color color of the student to be taken
     * @throws NotValidArgumentException If the student is not present in the entrance
     */
    private void takeFromEntrance(PawnType color) throws NotValidArgumentException {
        try {
            model.getCurrentPlayer().removeStudentFromEntrance(color);
        } catch (NotEnoughStudentException e) {
            throw new NotValidArgumentException("Student not in the entrance!");
        }
        studentFromEntrance = color;
    }

    /**
     * Method to take a student from the dining room
     * @param color student taken
     * @throws NotValidArgumentException if the student is not present in the dining room
     */
    private void takeFromDiningRoom(PawnType color) throws NotValidArgumentException{
        try {
            model.getCurrentPlayer().removeStudentFromDiningRoom(color);
        } catch (NotEnoughStudentException e) {
            throw new NotValidArgumentException("Student not in the dining room!");
        }
        studentFromDiningRoom = color;
    }
}
