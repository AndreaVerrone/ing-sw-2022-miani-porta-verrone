package it.polimi.ingsw.model.player;

import it.polimi.ingsw.controller.PlayerLoginInfo;
import it.polimi.ingsw.model.*;

import java.util.Collection;

/**
 * This class represent the player of the game.
 */
public class Player {

    /**
     * the {@code nickname} identifies the player.
     */
    private final String nickName;

    /**
     * the {@code towerType} is the color of the tower that the player is using.
     */
    private final TowerType towerType;

    /**
     * this is the deck containing all the assistant cards in the deck's player.
     */
    private final AssistantDeck assistantDeck;

    /**
     * this is the last assistant card used by the player.
     */
    private Assistant lastUsed;

    /**
     * this is a reference to the school board of the player.
     */
    private final SchoolBoard schoolBoard;

    /**
     * The constructor of the class takes in input 3 parameters:
     * <ul>
     *     <li> the corresponding PlayerLoginInfo that was created before the starting of the game </li>
     *
     *     <li> a boolean to indicate if the player is starting a game that involves
     *     3 players or not </li>
     *
     *     <li> the coins bag that collects all the coins that are present the game </li>
     * </ul>
     *
     * @param playerLoginInfo is the corresponding playerLoginInfo that was created before the starting of the game
     * @param isThreePlayerGame is {@code true} if the game involves 3 players, {@code false} otherwise
     * @param coinsBag  the coins bag of the game
     */
    public Player(PlayerLoginInfo playerLoginInfo, Boolean isThreePlayerGame, CoinsBag coinsBag) {
        this.nickName=playerLoginInfo.getNickname();
        this.towerType=playerLoginInfo.getTowerType();
        this.assistantDeck = new AssistantDeck(playerLoginInfo.getWizard());
        this.schoolBoard = new SchoolBoard(isThreePlayerGame, coinsBag);
    }

    /**
     * This method will use the {@code assistant} passed as parameter.
     * Note that after the calling of this method, the {@code assistant} will not be available anymore from the player's deck.
     *
     * @param assistant that will be used
     */
    public void useAssistant(Assistant assistant) {
        assistantDeck.removeAssistant(assistant);
        lastUsed = assistant;
    }

    /**
     * This method will move the student (of the specified type passed as parameter) from the entrance to
     * the dining room of the school board of the player.
     *
     * @param student type of the student to move
     * @throws NotEnoughStudentException  if there is not any student to remove from entrance
     * @throws ReachedMaxStudentException if the dining room is full
     */
    public void moveFromEntranceToDiningRoom(PawnType student)
            throws NotEnoughStudentException, ReachedMaxStudentException{
        schoolBoard.removeStudentFromEntrance(student);
        try {
            schoolBoard.addStudentToDiningRoom(student);
        } catch (ReachedMaxStudentException e) {
            schoolBoard.addStudentToEntrance(student);
            throw e;
        }
    }

    /**
     * This method gets the current deck of the player
     * Note: this should be used only to observe the content.
     * To modify it, use the appropriate methods of {@code Player}
     *
     * @return the current deck of the player
     * @see #useAssistant(Assistant)
     */
    public Collection<Assistant> getHand() {
        return assistantDeck.getCards();
    }

    public String getNickname() {
        return nickName;
    }

    public TowerType getTowerType() {
        return towerType;
    }


    public Assistant getLastAssistant() {
        return lastUsed;
    }

    /**
     * This method will return the number of students each type in entrance.
     *
     * @return the number of student of each type in entrance
     */
    public StudentList getStudentsInEntrance() {
        return schoolBoard.getStudentsInEntrance();
    }

    /**
     * This method will return the collection of professors that are in the School Board of the player.
     * <p>
     * Note: this method should be used only to observe the content.
     *
     * @return the professors in the School Board
     */
    public Collection<PawnType> getProfessors() {
        return schoolBoard.getProfessors();
    }

    /**
     * This method will return the number of towers that are in the school board of the player.
     *
     * @return the number of towers in the school board
     */
    public int getTowerNumbers() {
        return schoolBoard.getTowersNumber();
    }

    /**
     * This method will return the number of students of the specified type passed as parameter
     * that are in the dining room of the school board of the player.
     *
     * @param type of the student to check the number
     * @return number of students of the specified type passed as a parameter
     */
    public int getNumStudentOf(PawnType type) {
        return schoolBoard.getNumStudentsOf(type);
    }

    /**
     * This method will return the number of coins that are available in the school board of the player.
     *
     * @return the number of coins
     */
    public int getCoins() {
        return schoolBoard.getCoins();
    }

    /**
     * This method will add the student (of the specified type passed as a parameter) to the entrance of
     * the school board of the player.
     *
     * @param type of the student to add to entrance
     * @throws ReachedMaxStudentException if the entrance is full
     */
    public void addStudentToEntrance(PawnType type) throws ReachedMaxStudentException {
        schoolBoard.addStudentToEntrance(type);
    }

    /**
     * This method will remove one student (of the specified type in the parameter) from the
     * entrance of the school board of the player.
     *
     * @param type type of the student to remove
     * @throws NotEnoughStudentException if there isn't any student of that type to remove
     */
    public void removeStudentFromEntrance(PawnType type) throws NotEnoughStudentException {
        schoolBoard.removeStudentFromEntrance(type);
    }

    /**
     * This method will add the professor (of the type specified in the parameter) to the school board
     * of the player.
     *
     * @param professor is the type of the professor
     */
    public void addProfessor(PawnType professor) {
        schoolBoard.addProfessor(professor);
    }

    /**
     * This method will remove the professor (of the type specified in the parameter) from the
     * school board of the player.
     *
     * @param professor is the type of the professor to remove
     */
    public void removeProfessor(PawnType professor) {
        schoolBoard.removeProfessor(professor);
    }

    /**
     * This method will modify the number of tower that are in the school board of the player by adding
     * the parameter {@code delta} (taken in input) on it.
     * Based on the sign of delta this could be an increase (if positive) or a decrease (if negative).
     * <p>
     * Note that the number of towers can't exceed the maximum supported
     *
     * @param delta the number of tower to add (if {@code delta} is positive) or remove
     *              (if {@code delta} is negative)
     */
    public void changeTowerNumber(int delta) {
        schoolBoard.changeTowerNumber(delta);
    }

    /**
     * This method will add one student (of the specified type passed
     * as parameter) to the dining room.
     *
     * @param student type of student to add
     * @throws ReachedMaxStudentException if the table of that type is full
     */
    public void addStudentToDiningRoom(PawnType student)
            throws ReachedMaxStudentException {
        schoolBoard.addStudentToDiningRoom(student);
    }

    /**
     * This method will remove one student (of the specified type passed
     * as parameter) from the dining room.
     *
     * @param student type of the student to remove
     * @throws NotEnoughStudentException if there is not any student to remove of the specified type
     */
    public void removeStudentFromDiningRoom(PawnType student) throws NotEnoughStudentException {
        schoolBoard.removeStudentFromDiningRoom(student);
    }

    /**
     * This method will remove the number of coins passed as parameter from the school board of the player.
     * The {@code cost} must be greater that zero.
     *
     * @param cost number of coin to remove
     * @throws NotEnoughCoinsException if there are no enough coins in the {@code SchoolBoard}
     *                                 of the player to fulfil the request
     */
    public void removeCoins(int cost) throws NotEnoughCoinsException {
        schoolBoard.removeCoin(cost);
    }
}
