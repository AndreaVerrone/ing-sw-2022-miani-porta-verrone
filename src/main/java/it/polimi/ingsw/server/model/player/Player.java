package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.model.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        this.schoolBoard = new SchoolBoard(isThreePlayerGame, coinsBag, playerLoginInfo.getNickname());
    }

    /**
     * This method will use the {@code assistant} passed as parameter.
     * Note that after the calling of this method, the {@code assistant} will not be available anymore from the player's deck.
     *
     * @param assistant that will be used
     */
    public void useAssistant(Assistant assistant) {

        assistantDeck.removeAssistant(assistant);
        notifyChangeAssistantDeckObservers(this.nickName,getHand());

        lastUsed = assistant;
        notifyLastAssistantUsedObservers(this.nickName,getLastAssistant());
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

        // notify change in students in entrance
        notifyStudentsOnEntranceObservers(this.nickName,getStudentsInEntrance());
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
        notifyStudentsOnEntranceObservers(this.nickName, getStudentsInEntrance());
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
        notifyStudentsOnEntranceObservers(this.nickName,getStudentsInEntrance());
    }

    /**
     * This method will add the professor (of the type specified in the parameter) to the school board
     * of the player.
     *
     * @param professor is the type of the professor
     */
    public void addProfessor(PawnType professor) {
        schoolBoard.addProfessor(professor);
        notifyProfessorObservers(this.nickName,getProfessors());
    }

    /**
     * This method will remove the professor (of the type specified in the parameter) from the
     * school board of the player.
     *
     * @param professor is the type of the professor to remove
     */
    public void removeProfessor(PawnType professor) {
        schoolBoard.removeProfessor(professor);
        notifyProfessorObservers(this.nickName,getProfessors());
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
        notifyChangeTowerNumberObservers(this.nickName,getTowerNumbers());
    }

    /**
     * This method will add one student (of the specified type passed
     * as parameter) to the dining room.
     *
     * @param student type of student to add
     * @throws ReachedMaxStudentException if the table of that type is full
     */
    public void addStudentToDiningRoom(PawnType student) throws ReachedMaxStudentException {
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
     * @param putInBag is true if all the coins should be put in the general reserve of coins,
     *                 false if one of them should be used to indicate that
     *                 the cost of the character card should be increased
     * @throws NotEnoughCoinsException if there are no enough coins in the {@code SchoolBoard}
     *                                 of the player to fulfil the request
     */
    public void removeCoins(int cost,boolean putInBag) throws NotEnoughCoinsException {
        schoolBoard.removeCoin(cost,putInBag);
    }

    // MANAGEMENT OF OBSERVERS ON TOWER NUMBER
    /**
     * List of the observer on the tower number.
     */
    private final List<ChangeTowerNumberObserver> changeTowerNumberObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the tower number.
     * @param observer the observer to be added
     */
    public void addChangeTowerNumberObserver(ChangeTowerNumberObserver observer){
        changeTowerNumberObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the tower number.
     * @param observer the observer to be removed
     */
    public void removeChangeTowerNumberObserver(ChangeTowerNumberObserver observer){
        changeTowerNumberObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on the tower number.
     * @param nickName the nickname of the player that has the school board on which the changes have been happened.
     * @param numOfActualTowers the actual number of towers
     */
    private void notifyChangeTowerNumberObservers(String nickName, int numOfActualTowers){
        for(ChangeTowerNumberObserver observer : changeTowerNumberObservers)
            observer.changeTowerNumberUpdate(nickName, numOfActualTowers);
    }

    // MANAGEMENT OF OBSERVERS ON STUDENTS ON ENTRANCE
    /**
     * List of the observer on the students on entrance.
     */
    private final List<StudentsOnEntranceObserver> studentsOnEntranceObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the students on entrance.
     * @param observer the observer to be added
     */
    public void addStudentsOnEntranceObserver(StudentsOnEntranceObserver observer){
        studentsOnEntranceObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the students on entrance.
     * @param observer the observer to be removed
     */
    public void removeStudentsOnEntranceObserver(StudentsOnEntranceObserver observer){
        studentsOnEntranceObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on the students on entrance.
     * @param nickName the nickname of the player that has the school board on which the changes have been happened
     * @param actualStudents the actual student list in entrance
     */
    private void notifyStudentsOnEntranceObservers(String nickName, StudentList actualStudents){
        for(StudentsOnEntranceObserver observer : studentsOnEntranceObservers)
            observer.studentsOnEntranceObserverUpdate(nickName, actualStudents);
    }

    // MANAGEMENT OF OBSERVERS ON PROFESSOR
    /**
     * List of the observer on the assistant deck.
     */
    private final List<ProfessorObserver> professorObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the assistant deck.
     * @param observer the observer to be added
     */
    public void addProfessorObserver(ProfessorObserver observer){
        professorObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the assistant deck.
     * @param observer the observer to be removed
     */
    public void removeProfessorObserver(ProfessorObserver observer){
        professorObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on the assistant deck.
     * @param nickName that has the school board on which the change of professors have been happened
     * @param actualProfessors the actual professor list in dining room
     */
    private void notifyProfessorObservers(String nickName, Collection<PawnType> actualProfessors){
        for(ProfessorObserver observer : professorObservers)
            observer.professorObserverUpdate(nickName,actualProfessors);
    }

    // MANAGEMENT OF OBSERVERS ON ASSISTANT DECK
    /**
     * List of the observer on the assistant deck.
     */
    private final List<ChangeAssistantDeckObserver> changeAssistantDeckObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the assistant deck.
     * @param observer the observer to be added
     */
    public void addChangeAssistantDeckObserver(ChangeAssistantDeckObserver observer){
        changeAssistantDeckObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the assistant deck.
     * @param observer the observer to be removed
     */
    public void removeChangeAssistantDeckObserver(ChangeAssistantDeckObserver observer){
        changeAssistantDeckObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on last assistant.
     * @param nickName the nickname of the player that has the deck that has been changed
     * @param actualDeck the actual deck
     */
    private void notifyChangeAssistantDeckObservers(String nickName, Collection<Assistant> actualDeck){
        for(ChangeAssistantDeckObserver observer : changeAssistantDeckObservers)
            observer.changeAssistantDeckObserverUpdate(nickName,actualDeck);
    }

    // MANAGEMENT OF OBSERVERS ON LAST ASSISTANT USED
    /**
     * List of the observer on the assistant deck.
     */
    private final List<LastAssistantUsedObserver> lastAssistantUsedObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on last assistant.
     * @param observer the observer to be added
     */
    public void addLastAssistantUsedObserver(LastAssistantUsedObserver observer){
        lastAssistantUsedObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on last assistant.
     * @param observer the observer to be removed
     */
    public void removeLastAssistantUsedObserver(LastAssistantUsedObserver observer){
        lastAssistantUsedObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on last assistant.
     * @param nickName the nickname of the player that has the deck that has been changed
     * @param actualLastAssistant the actual last assistant
     */
    private void notifyLastAssistantUsedObservers(String nickName, Assistant actualLastAssistant){
        for(LastAssistantUsedObserver observer : lastAssistantUsedObservers)
            observer.lastAssistantUsedObserverUpdate(nickName,actualLastAssistant);
    }

    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON COINS

    /**
     * This method allows to add the observer, passed as a parameter, on the coin number.
     * @param observer the observer to be added
     */
    public void addChangeCoinNumberObserver(ChangeCoinNumberObserver observer){
        schoolBoard.addChangeCoinNumberObserver(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the coin number.
     * @param observer the observer to be removed
     */
    public void removeChangeCoinNumberObserver(ChangeCoinNumberObserver observer){
        schoolBoard.removeChangeCoinNumberObserver(observer);
    }

    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON STUDENTS IN DINING ROOM

    /**
     * This method allows to add the observer, passed as a parameter, on the students in dining room.
     * @param observer the observer to be added
     */
    public void addStudentsInDiningRoomObserver(StudentsInDiningRoomObserver observer){
        schoolBoard.addStudentsInDiningRoomObserver(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the students dining room.
     * @param observer the observer to be removed
     */
    public void removeStudentsInDiningRoomObserver(StudentsInDiningRoomObserver observer){
        schoolBoard.removeStudentsInDiningRoomObserver(observer);
    }
}
