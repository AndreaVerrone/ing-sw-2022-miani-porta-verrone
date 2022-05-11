package it.polimi.ingsw.messages.responses;

/**
 * The error code associated with an error message
 */
public enum ErrorCode{
    NONE,
    GENERIC_INVALID_ARGUMENT,
    GENERIC_INVALID_OPERATION,
    GAME_NOT_EXIST,
    GAME_IS_FULL,
    NICKNAME_TAKEN,
    ASSISTANT_NOT_EXIST,
    ASSISTANT_NOT_USABLE,
    STUDENT_NOT_PRESENT,
    ISLAND_NOT_EXIST,
    DININGROOM_FULL,
    CLOUD_EMPTY,
    CLOUD_NOT_EXIST
}
