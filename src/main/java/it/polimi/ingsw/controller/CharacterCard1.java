package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;
import it.polimi.ingsw.model.gametable.exceptions.EmptyBagException;

public class CharacterCard1 extends CharacterCard{

    /**
     * the students that are present on the card
     */
    private final StudentList studentList = new StudentList();

    /**
     * This is the Game class
     * @see Game
     */
    private Game game;

    /**
     * The constructor of the class creates a new character card with the specified initial cost,
     * the description and the game class passed as a parameter.
     * @param cost              the initial cost of the card
     * @param effectDescription the description of the effect of this card
     * @param game              the Game class
     */
    CharacterCard1(int cost, String effectDescription, Game game) {

        super(cost, effectDescription);

        // add students to the card
        for(int i=0;i<4;i++) {
            try {
                studentList.changeNumOf(game.getModel().getStudentFromBag(),1);
            } catch (EmptyBagException e) {
                // todo: how to manage ?
                // it is impossible that happen since the card is built at the begging of the game
                e.printStackTrace();
            } catch (NotEnoughStudentException e) {
                // todo: how to manage ?
                // it is impossible that is thrown since the delta is positive.
                e.printStackTrace();
            }
        }
    }

    @Override
    public void effect() {
        // go to UseCharacterCard1State
        this.game.setState(new UseCharacterCard1State(game, game.getState(),this));
    }

    /**
     * This method will add a student on the card.
     * @param pawnType color of the student to add on the card
     */
    public void addStudentToCard(PawnType pawnType){
        try {
            studentList.changeNumOf(pawnType, 1);
        } catch (NotEnoughStudentException e) {
            // todo: how to manage ?
            // it is impossible that is thrown since the delta is positive.
            e.printStackTrace();
        }
    }

    /**
     * This method will remove a student from the card.
     * @param pawnType color of the student to remove
     * @throws NotEnoughStudentException if there is not a student of that color to remove
     */
    public void removeStudentFromCard(PawnType pawnType) throws NotEnoughStudentException {
        studentList.changeNumOf(pawnType, -1);
    }
}
