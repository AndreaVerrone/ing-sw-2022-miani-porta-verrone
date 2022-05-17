package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.StudentList;

/**
 * Interface to implement the observer pattern.
 */
public interface StudentsOnCardObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param characterCardType the character card type on which the student has been changed
     * @param actualStudents the actual student list on island
     */
    public void studentsOnIslandObserverUpdate(CharacterCardsType characterCardType, StudentList actualStudents);

}
