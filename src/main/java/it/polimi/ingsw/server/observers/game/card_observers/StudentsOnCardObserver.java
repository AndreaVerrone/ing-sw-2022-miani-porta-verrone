package it.polimi.ingsw.server.observers.game.card_observers;

import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.utils.StudentList;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsOnCardObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param characterCardType the character card type on which the student has been changed
     * @param actualStudents the actual student list on character card
     */
    void studentsOnCardObserverUpdate(CharacterCardsType characterCardType, StudentList actualStudents);

}
