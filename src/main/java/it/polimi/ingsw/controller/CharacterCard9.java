package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;
import it.polimi.ingsw.model.gametable.exceptions.EmptyBagException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to implement the card 9 to swap up to three chosen students from this card with the entrance of the player
 */
public class CharacterCard9 extends CharacterCard{

    /**
     * Game class
     */
    private final ExpertGame game;

    /**
     * Students on the card
     */
    private final StudentList studentsOnCard;
    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     */
    CharacterCard9(ExpertGame game) {
        super(CharacterCardsType.CARD9.getCost(), CharacterCardsType.CARD9.getDescription());
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
        notifyStudentsOnCardObservers(CharacterCardsType.CARD9,studentsOnCard.clone());
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
        notifyStudentsOnCardObservers(CharacterCardsType.CARD9,studentsOnCard.clone());
    }

    /**
     * Gives back a copy of the students on the card
     * @return a copy of the student on the card
     */
    public StudentList getStudents(){
        return studentsOnCard.clone();
    }

}
