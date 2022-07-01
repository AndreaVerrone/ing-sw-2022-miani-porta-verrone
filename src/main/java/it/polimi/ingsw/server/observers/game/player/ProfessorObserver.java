package it.polimi.ingsw.server.observers.game.player;

import it.polimi.ingsw.server.model.utils.PawnType;

import java.util.Collection;

/**
 * Interface to implement the observer pattern.
 */
public interface ProfessorObserver {

    /**
     * Notifies that the professors controlled by a player has changed.
     * @param nickname the nickname of the player
     * @param actualProfessors the actual professors
     */
    void professorObserverUpdate(String nickname, Collection<PawnType> actualProfessors);
}
