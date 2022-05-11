package it.polimi.ingsw.messages.responses;

/**
 * The error code associated with an error message
 */
public enum ErrorCode{
    /**
     * No error occurred
     */
    NONE,
    /**
     * A generic error regarding the argument passed
     */
    GENERIC_INVALID_ARGUMENT,
    /**
     * A generic error regarding the execution of an operation
     */
    GENERIC_INVALID_OPERATION,
    /**
     * The game requested doesn't exist or the client is not in any game
     */
    GAME_NOT_EXIST,
    /**
     * The game requested reached the maximum number of players
     */
    GAME_IS_FULL,
    /**
     * The chosen nickname is already taken
     */
    NICKNAME_TAKEN,
    /**
     * The number of player is not one of the supported (2 or 3 players)
     */
    NUMBER_PLAYERS_NOT_SUPPORTED,
    /**
     * The player making a request is not in turn
     */
    PLAYER_NOT_IN_TURN,
    /**
     * The assistant chosen is not in the deck
     */
    ASSISTANT_NOT_EXIST,
    /**
     * The assistant chosen cannot be used
     */
    ASSISTANT_NOT_USABLE,
    /**
     * The student selected is not in the entrance
     */
    STUDENT_NOT_PRESENT,
    /**
     * The island selected does not exist
     */
    ISLAND_NOT_EXIST,
    /**
     * The table of the type is full
     */
    DININGROOM_FULL,
    /**
     * The cloud chosen is empty
     */
    CLOUD_EMPTY,
    /**
     * The cloud chosen does not exist
     */
    CLOUD_NOT_EXIST
}
