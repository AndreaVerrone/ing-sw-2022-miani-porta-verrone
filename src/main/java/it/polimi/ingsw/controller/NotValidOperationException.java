package it.polimi.ingsw.controller;

import it.polimi.ingsw.network.messages.responses.ErrorCode;

/**
 * Thrown if an operation is called in the wrong state of the game
 */
public class NotValidOperationException extends Exception{

    /**
     * The error associated with this exception
     */
    private ErrorCode errorCode = ErrorCode.GENERIC_INVALID_OPERATION;

    /**
     * Constructs a new exception with {@link ErrorCode#GENERIC_INVALID_OPERATION} as the error code.
     */
    public NotValidOperationException() {
        super();
    }
    public NotValidOperationException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception assigning the provided {@link ErrorCode}.
     * @param errorCode the code of the error that caused this exception
     */
    public NotValidOperationException(ErrorCode errorCode){
        super();
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
