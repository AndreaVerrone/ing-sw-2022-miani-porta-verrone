package it.polimi.ingsw.server.controller.game.expert.card_states;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.controller.game.expert.cards.CharacterCard9;
import it.polimi.ingsw.server.controller.game.states.State;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;

/**
 * Class that implements the state where the current player can use the card 9
 */
public class UseCharacterCard9State extends UseCharacterCardState {

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
    public UseCharacterCard9State(ExpertGame game, State originState, CharacterCard9 card){
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
        try {
            //Remove students from card and entrance
            card.takeStudentFromCard(studentFromCard);
            model.getCurrentPlayer().removeStudentFromEntrance(studentFromEntrance);
            //Swap the students and add them to card and entrance
            card.addStudentToCard(studentFromEntrance);
            model.getCurrentPlayer().addStudentToEntrance(studentFromCard);
        } catch (ReachedMaxStudentException | NotEnoughStudentException e) {
            e.printStackTrace();//Not possible, already controlled
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
     * Controls the student chosen is on the card and saves it
     * @param color color of the student to be taken
     * @throws NotValidArgumentException If the student is not present on the card
     */
    private void takeFromCard(PawnType color) throws NotValidArgumentException {
        if(card.getStudents().getNumOf(color) <= 0){
            throw new NotValidArgumentException("The student is not on the card");
        }
        studentFromCard = color;
    }

    /**
     * Controls the student chosen is in the entrance and saves it
     * @param color color of the student to be taken
     * @throws NotValidArgumentException If the student is not present in the entrance
     */
    private void takeFromEntrance(PawnType color) throws NotValidArgumentException {
        if (model.getCurrentPlayer().getStudentsInEntrance().getNumOf(color) <= 0) {
            throw new NotValidArgumentException("Student not in the entrance!");
        }
        studentFromEntrance = color;
    }

}
