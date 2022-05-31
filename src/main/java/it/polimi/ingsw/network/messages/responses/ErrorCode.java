package it.polimi.ingsw.network.messages.responses;

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
     * The selected tower is not available
     */
    TOWER_NOT_AVAILABLE,
    /**
     * The selected wizard is not available
     */
    WIZARD_NOT_AVAILABLE,
    /**
     * The assistant chosen is not in the deck
     */
    ASSISTANT_NOT_EXIST,
    /**
     * The assistant chosen cannot be used
     */
    ASSISTANT_NOT_USABLE,
    /**
     * The student selected is not in the location chosen
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
     * The movement of MN is out of bounds (i.e. negative or more than the maximum value)
     */
    MN_MOVEMENT_WRONG,
    /**
     * The cloud chosen is empty
     */
    CLOUD_EMPTY,
    /**
     * The cloud chosen does not exist
     */
    CLOUD_NOT_EXIST,
    /**
     * The character card selected doesn't exist or is not available
     */
    CHARACTER_CARD_NOT_EXIST,
    /**
     * The player hasn't enough money to use the selected character card
     */
    CHARACTER_CARD_EXPENSIVE,
    /**
     * The player has already used a character card in his turn
     */
    CHARACTER_CARD_ALREADY_USED

}
