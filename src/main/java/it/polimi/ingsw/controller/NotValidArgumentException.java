package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.messages.responses.ErrorCode;

/**
 * Thrown if the argument given to an operation done by the controller is invalid
 */
public class NotValidArgumentException extends Exception{

    /**
     * The error associated with this exception
     */
    private ErrorCode errorCode = ErrorCode.GENERIC_INVALID_ARGUMENT;

    /**
     * Constructs a new exception with {@link ErrorCode#GENERIC_INVALID_ARGUMENT} as the error code.
     */
    public NotValidArgumentException(){
        super();
    }
    public NotValidArgumentException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception assigning the provided {@link ErrorCode}.
     * @param errorCode the code of the error that caused this exception
     */
    public NotValidArgumentException(ErrorCode errorCode){
        super();
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
