package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.ReachedMaxStudentException;

/**
 * Class that implements the state where the current player can use the card 9
 */
public class UseCharacterCard9State implements State{

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
     * Constructor of the class. Saves the game, the state before this one and the card used
     * @param game game class of the game
     * @param originState the state from which the character card has been used
     * @param card card used in the state
     */
    public UseCharacterCard9State(Game game, State originState, CharacterCard9 card){
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
        if(originPosition.isLocation(Location.CHARACTER_CARD_9)){
            try {
                card.takeStudentFromCard(color);
            } catch (NotEnoughStudentException e) {
                throw new NotValidArgumentException("This student is not on the card");
            }
            studentFromCard = color;
        }
        else{
            if ((originPosition.isLocation(Location.ENTRANCE))){
                try {
                    game.getModel().getCurrentPlayer().removeStudentFromEntrance(color);
                } catch (NotEnoughStudentException e) {
                    throw new NotValidArgumentException("The student is not in the entrance");
                }
                studentFromEntrance = color;
            }
            else{
                throw new NotValidOperationException("Wrong operation!");
            }
        }
        if(studentFromEntrance!=null && studentFromCard!=null){
            swapStudent();
        }
    }

    /**
     * Swaps the students chosen from the card and from the entrance of the current player
     */
    private void swapStudent(){
        card.addStudentToCard(studentFromEntrance);
        try {
            game.getModel().getCurrentPlayer().addStudentToEntrance(studentFromCard);
        } catch (ReachedMaxStudentException e) {
            e.printStackTrace();//Not possible
        }
        studentFromCard = null;
        studentFromEntrance = null;
    }



}
