package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.ReachedMaxStudentException;

/**
 * Class that implements the state where the current player can use the card 10
 */
public class UseCharacterCard10State implements State {

    /**
     * Game class of the game
     */
    private final Game game;

    /**
     * State of the game before going to the state to use a card
     */
    private final State originState;

    /**
     * Card to use in this state
     */
    private final CharacterCard card;

    /**
     * Student taken from the dining room of the current player
     */
    private PawnType studentFromDiningRoom = null;

    /**
     * Student taken from the entrance of the current player
     */
    private PawnType studentFromEntrance = null;

    /**
     * Constructor of the class. Saves the game, the state before this one and the card used
     * @param game game class of the game
     * @param originState the state from which the character card has been used
     * @param card card used in the state
     */
    public UseCharacterCard10State(Game game, State originState, CharacterCard card){
        this.game = game;
        this.originState = originState;
        this. card = card;
    }

    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition) throws NotValidOperationException, NotValidArgumentException {
        if(originPosition.isLocation(Location.NONE)){
            card.effectEpilogue();
            game.setState(originState);
            return;
        }
        if(originPosition.isLocation(Location.ENTRANCE)){
            try {
                game.getModel().getCurrentPlayer().removeStudentFromEntrance(color);
            } catch (NotEnoughStudentException e) {
                throw new NotValidArgumentException("Student not in the entrance!");
            }
            studentFromEntrance = color;
        }
        else {
            if(originPosition.isLocation(Location.DINING_ROOM)){
                try {
                    game.getModel().getCurrentPlayer().removeStudentFromDiningRoom(color);
                } catch (NotEnoughStudentException e) {
                    throw new NotValidArgumentException("Student not in the dining room!");
                }
                studentFromDiningRoom = color;
            }
            else{
                throw new NotValidOperationException("Choose a student from the entrance or the dining room");
            }
        }
        if(studentFromEntrance!=null && studentFromDiningRoom!=null){
            swapStudent();
        }
    }

    /**
     * Swaps the students chosen from the dining room and from the entrance of the current player
     */
    private void swapStudent(){
        try {
            game.getModel().getCurrentPlayer().addStudentToDiningRoom(studentFromEntrance);
            game.getModel().getCurrentPlayer().addStudentToEntrance(studentFromDiningRoom);
        } catch (ReachedMaxStudentException e) {
            e.printStackTrace();//Not possible
        }
        studentFromDiningRoom = null;
        studentFromEntrance = null;
    }
}
