package it.polimi.ingsw.server.observers.game.player;

/**
 * An observer for all the changes of a player that needs to be notified to the client
 */
public interface PlayerObserver extends SchoolBoardObserver, ChangeAssistantDeckObserver, LastAssistantUsedObserver {}

