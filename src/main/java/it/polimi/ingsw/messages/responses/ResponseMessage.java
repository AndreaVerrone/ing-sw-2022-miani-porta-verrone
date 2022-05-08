package it.polimi.ingsw.messages.responses;

import it.polimi.ingsw.messages.NetworkMessage;

import java.util.UUID;

/**
 * A class representing a response message sent. This can be sent from the client to the server or
 * the other way round.
 */
public class ResponseMessage extends NetworkMessage {

    /**
     * The result of this response.
     */
    private final Result result;

    /**
     * The possible error code of this response.
     */
    private final ErrorCode errorCode;

    /**
     * The unique identifier of the message this response is for.
     */
    private final UUID parentMessage;

    /**
     * Creates a new response for the provided message with the corresponding result and error code.
     * @param parentMessage the message that generated this response
     * @param result the response of the request made
     * @param errorCode the error code associated with this response
     */
    public ResponseMessage(NetworkMessage parentMessage, Result result, ErrorCode errorCode){
        this.parentMessage = parentMessage.getIdentifier();
        this.result = result;
        this.errorCode = errorCode;
    }

    /**
     * Creates a new success response message associated with the provided message
     * @param parentMessage the message that generated this response
     * @return a new successful {@code ResponseMessage}
     */
    public static ResponseMessage newSuccess(NetworkMessage parentMessage){
        return new ResponseMessage(parentMessage, Result.SUCCESS, ErrorCode.NONE);
    }

    public Result getResult() {
        return result;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public UUID getParentMessage(){
        return parentMessage;
    }

    /**
     * Returns if this response indicates a success in the request made.
     * @return {@code true} if the request was successfully, {@code false} otherwise
     */
    public boolean isSuccess(){
        return result == Result.SUCCESS;
    }

}
