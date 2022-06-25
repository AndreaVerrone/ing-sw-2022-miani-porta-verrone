package it.polimi.ingsw.server.controller.game.expert.cards;

import it.polimi.ingsw.client.reduced_model.ReducedCharacter;
import it.polimi.ingsw.server.controller.game.expert.CharacterCard;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.controller.game.expert.card_observers.StudentsOnCardObserver;
import it.polimi.ingsw.server.controller.game.expert.card_states.UseCharacterCard9State;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.EmptyBagException;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to implement the card 9 to swap up to three chosen students from this card with the entrance of the player
 */
public class CharacterCard9 extends CharacterCard {

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
        super(CharacterCardsType.CARD9);
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
    protected ReducedCharacter reduce() {
        return new ReducedCharacter(getCardType(), isUsed(), studentsOnCard);
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
        notifyStudentsOnCardObservers(studentsOnCard.clone());
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
        notifyStudentsOnCardObservers(studentsOnCard.clone());
    }

    /**
     * Gives back a copy of the students on the card
     * @return a copy of the student on the card
     */
    public StudentList getStudents(){
        return studentsOnCard.clone();
    }

    // MANAGEMENT OF OBSERVERS ON STUDENTS ON CHARACTER CARD
    /**
     * List of the observer on the students on character card.
     */
    private final List<StudentsOnCardObserver> studentsOnCardObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the students on character card.
     * @param observer the observer to be added
     */
    @Override
    public void addStudentsOnCardObserver(StudentsOnCardObserver observer){
        studentsOnCardObservers.add(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on the students on character card.
     * @param actualStudents the actual student list on card
     */
    private void notifyStudentsOnCardObservers(StudentList actualStudents){
        for(StudentsOnCardObserver observer : studentsOnCardObservers)
            observer.studentsOnCardObserverUpdate(this.getCardType(), actualStudents);
    }

}
