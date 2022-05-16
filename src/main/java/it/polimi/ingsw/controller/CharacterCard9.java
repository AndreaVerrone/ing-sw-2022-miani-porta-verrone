package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;
import it.polimi.ingsw.model.gametable.exceptions.EmptyBagException;

/**
 * Class to implement the card 9 to swap up to three chosen students from this card with the entrance of the player
 */
public class CharacterCard9 extends CharacterCard{

    /**
     * Game class
     */
    private final Game game;

    /**
     * Students on the card
     */
    private final StudentList studentsOnCard;
    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     */
    CharacterCard9(Game game) {
        super(1, "Allows to swap up to three chosen students from this card with the entrance of the player");
        this.game = game;
        this.studentsOnCard = new StudentList();

        for (int i=0; i<6; i++){
            try {
                addStudentToCard(game.getModel().getStudentFromBag());
            } catch (EmptyBagException e) {
                e.printStackTrace();//Cannot happen
            }
        }
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard9State(game, game.getState(), this));
    }

    /**
     * Take a student from a card
     * @param pawnType student color taken from the card
     * @throws NotEnoughStudentException if the student is not present on the card
     */
    public void takeStudentFromCard(PawnType pawnType) throws NotEnoughStudentException {
        studentsOnCard.changeNumOf(pawnType, -1);
    }

    /**
     * Add student to the card
     * @param pawnType student color added to the card
     */
    public void addStudentToCard(PawnType pawnType){
        try {
            studentsOnCard.changeNumOf(pawnType, 1);
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();//Never happens
        }
    }

    /**
     * Gives back a copy of the students on the card
     * @return a copy of the student on the card
     */
    public StudentList getStudents(){
        return studentsOnCard.clone();
    }


}
