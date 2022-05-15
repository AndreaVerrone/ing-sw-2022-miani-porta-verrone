package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.ReachedMaxStudentException;

/**
 * Class that implements the state where the current player can use the card 9
 */
public class UseCharacterCard9State extends UseCharacterCardState implements State{

    /**
     * Model of the game
     */
    private final GameModel model;

    /**
     * Card to use in this state
     */
    private final CharacterCard9 card;

    /**
     * Student taken from the card
     */
    private PawnType studentFromCard = null;

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
    public UseCharacterCard9State(Game game, State originState, CharacterCard9 card){
        super(game, originState, card);
        this.model = game.getModel();
        this.card = card;
    }

    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition) throws NotValidOperationException, NotValidArgumentException {
        if(originPosition.isLocation(Location.NONE)){
            //Send position NONE to stop swapping
            // EPILOGUE
            finalizeCardUsed();
            returnBack();
            return;
        }
        if(originPosition.isLocation(Location.CHARACTER_CARD_9)){
            //Take from card
            takeFromCard(color);
        }
        else{
            if ((originPosition.isLocation(Location.ENTRANCE))){
                //Take from entrance
                takeFromEntrance(color);
            }
            else{
                throw new NotValidOperationException("Wrong operation!");
            }
        }
        if(studentFromEntrance!=null && studentFromCard!=null){
            //Both students have been chosen
            swapStudent();
        }
    }

    /**
     * Swaps the students chosen from the card and from the entrance of the current player
     */
    private void swapStudent(){
        card.addStudentToCard(studentFromEntrance);
        try {
            model.getCurrentPlayer().addStudentToEntrance(studentFromCard);
        } catch (ReachedMaxStudentException e) {
            e.printStackTrace();//Not possible
        }
        numberOfStudentsSwapped ++;
        studentFromCard = null;
        studentFromEntrance = null;

        //If three students have been swapped stop
        if(numberOfStudentsSwapped == 3){
            // EPILOGUE
            finalizeCardUsed();
            returnBack();
        }
    }

    /**
     * Method to take a student from the card
     * @param color color of the student to be taken
     * @throws NotValidArgumentException If the student is not present on the card
     */
    private void takeFromCard(PawnType color) throws NotValidArgumentException {
        try {
            card.takeStudentFromCard(color);
        } catch (NotEnoughStudentException e) {
            throw new NotValidArgumentException("The student is not in the entrance");
        }
        studentFromCard = color;
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
            throw new NotValidArgumentException("The student is not in the entrance");
        }
        studentFromEntrance = color;
    }

}
