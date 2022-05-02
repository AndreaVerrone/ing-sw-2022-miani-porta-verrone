package it.polimi.ingsw.controller;

/**
 * Thrown if an operation is called in the wrong state of the game
 */
public class NotValidOperationException extends Exception{
    public NotValidOperationException() {
        super();
    }
    public NotValidOperationException(String message) {
        super(message);
    }
}
