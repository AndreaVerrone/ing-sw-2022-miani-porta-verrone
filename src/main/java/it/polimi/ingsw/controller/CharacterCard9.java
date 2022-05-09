package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;
import it.polimi.ingsw.model.gametable.exceptions.EmptyBagException;

public class CharacterCard9 extends CharacterCard{

    private final Game game;

    private final StudentList studentsOnCard;
    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param game Game class of the game
     * @param cost              the initial cost of the card
     * @param effectDescription the description of the effect of this card
     */
    CharacterCard9(int cost, String effectDescription, Game game) {
        super(cost, effectDescription);
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

    public void takeStudentFromCard(PawnType pawnType) throws NotEnoughStudentException {
        studentsOnCard.changeNumOf(pawnType, -1);
    }

    public void addStudentToCard(PawnType pawnType){
        try {
            studentsOnCard.changeNumOf(pawnType, 1);
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();//Never happens
        }
    }


}
