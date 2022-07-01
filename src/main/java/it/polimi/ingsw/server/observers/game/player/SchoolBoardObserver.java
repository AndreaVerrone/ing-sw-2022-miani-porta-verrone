package it.polimi.ingsw.server.observers.game.player;

/**
 * An observer for all the changes of a school board that needs to be notified to the client
 */
public interface SchoolBoardObserver extends
        ChangeTowerNumberObserver, ProfessorObserver, StudentsInDiningRoomObserver, StudentsOnEntranceObserver{
}
