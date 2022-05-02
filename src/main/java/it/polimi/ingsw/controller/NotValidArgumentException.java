package it.polimi.ingsw.controller;

/**
 * Thrown if the argument given to an operation done by the controller is invalid
 */
public class NotValidArgumentException extends Exception{
    public NotValidArgumentException(){
        super();
    }
    public NotValidArgumentException(String message) {
        super(message);
    }
}
