package it.polimi.ingsw.server.observers;

import it.polimi.ingsw.server.model.utils.PawnType;

import java.util.Collection;

/**
 * Interface to implement the observer pattern.
 */
public interface ProfessorObserver {

    /**
     * this method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     * @param nickName that has the school board on which the change of professors have been happened
     * @param actualProfessors the actual professor list in dining room
     */
    void professorObserverUpdate(String nickName, Collection<PawnType> actualProfessors);
}
