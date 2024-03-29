package it.polimi.ingsw.server.controller.game.expert.cards;

import it.polimi.ingsw.client.reduced_model.ReducedCharacter;
import it.polimi.ingsw.server.controller.game.expert.CharacterCard;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.controller.game.expert.card_states.UseCharacterCard1State;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.EmptyBagException;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.observers.game.card_observers.StudentsOnCardObserver;

import java.util.ArrayList;
import java.util.List;

public class CharacterCard1 extends CharacterCard {

    /**
     * the students that are present on the card
     */
    private final StudentList studentList = new StudentList();

    /**
     * This is the Game class
     * @see ExpertGame
     */
    private final ExpertGame game;

    /**
     * The constructor of the class.
     * @param game the Game class
     */
    CharacterCard1(ExpertGame game) {

        super(CharacterCardsType.CARD1);

        this.game=game;

        // add students to the card
        for(int i=0;i<4;i++) {
            try {
                studentList.changeNumOf(game.getModel().getGameTable().getStudentFromBag(),1);
            } catch (EmptyBagException e) {
                // it is impossible that happen since the card is built at the begging of the game
                e.printStackTrace();
            } catch (NotEnoughStudentException e) {
                // it is impossible that is thrown since the delta is positive.
                e.printStackTrace();
            }
        }
    }

    @Override
    protected ReducedCharacter reduce() {
        return new ReducedCharacter(getCardType(), isUsed(), studentList);
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
            // it is impossible that is thrown since the delta is positive.
            e.printStackTrace();
        }
        notifyStudentsOnCardObservers(studentList.clone());
    }

    /**
     * This method will remove a student from the card.
     * @param pawnType color of the student to remove
     * @throws NotEnoughStudentException if there is not a student of that color to remove
     */
    public void removeStudentFromCard(PawnType pawnType) throws NotEnoughStudentException {
        studentList.changeNumOf(pawnType, -1);
        notifyStudentsOnCardObservers(studentList.clone());
    }

    /**
     * This method allow to get a copy of the student that are present on the card.
     * @return the studentList of the student on the card
     */
    public StudentList getStudentList() {
        return studentList.clone();
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
