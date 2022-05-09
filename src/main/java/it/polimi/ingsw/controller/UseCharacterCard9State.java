package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.ReachedMaxStudentException;

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

    private PawnType studentFromCard = null;

    private PawnType studentFromEntrance = null;

    private boolean firstStudent = true;

    public UseCharacterCard9State(Game game, State originState, CharacterCard9 card){
        this.game = game;
        this.originState = originState;
        this. card = card;
    }

    @Override
    public void chooseStudent(PawnType pawnType) throws NotValidArgumentException {
        if (firstStudent) {
            if(pawnType == null){
                card.effectEpilogue();
                game.setState(originState);
                return;
            }
            try {
                card.takeStudentFromCard(pawnType);
            } catch (NotEnoughStudentException e) {
                throw new NotValidArgumentException("This student is not on the card");
            }
            studentFromCard = pawnType;
            firstStudent = false;
        }
        else {
            try {
                game.getModel().getCurrentPlayer().removeStudentFromEntrance(pawnType);
            } catch (NotEnoughStudentException e) {
                card.addStudentToCard(studentFromCard);//Put again the student on the card
                throw new NotValidArgumentException("The student is not in the entrance");
            }
            studentFromEntrance = pawnType;
            firstStudent = true;
        }
        swapStudent();
    }

    private void swapStudent(){
        card.addStudentToCard(studentFromEntrance);
        try {
            game.getModel().getCurrentPlayer().addStudentToEntrance(studentFromCard);
        } catch (ReachedMaxStudentException e) {
            e.printStackTrace();//Not possible
        }
    }


}
