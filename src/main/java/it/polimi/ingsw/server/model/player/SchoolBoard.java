package it.polimi.ingsw.server.model.player;

import it.polimi.ingsw.server.model.CoinsBag;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughCoinsException;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;
import it.polimi.ingsw.server.observers.game.player.ChangeCoinNumberObserver;
import it.polimi.ingsw.server.observers.game.player.SchoolBoardObserver;
import it.polimi.ingsw.server.observers.game.player.StudentsInDiningRoomObserver;

import java.util.*;

/**
 * A class representing the school board of a player.
 * This is used to store information about the students
 * each player has, as well as the number of towers and coins.
 */
class SchoolBoard {
    private final int maxNumStudentsInEntrance;
    private final int maxNumTowers;

    /**
     * The entrance of this school board. This can contain students of any type, in a maximum of
     * 7 students in games between two or four players and 9 in games between three players.
     */
    private final StudentList entrance = new StudentList();

    /**
     * The dining room of this school board.
     * @see DiningRoom
     */
    private final DiningRoom diningRoom;

    /**
     * Table with the professors in the dining room
     */
    private final Set<PawnType> professorTable = new HashSet<>();

    /**
     * The number of towers remaining in this school board.
     */
    private int towers;

    /**
     * The number of coins that the player who owns this school board has.
     */
    private int coins = 0;

    /**
     * Bag with all the coins
     */
    private final CoinsBag coinsBag;

    /**
     * This is the nickname of the player to which this school board is associated to.
     */
    private final String nickNameOfPlayer;

    /**
     * List of the observers ont this school board
     */
    private final Set<SchoolBoardObserver> schoolBoardObservers = new HashSet<>();

    /**
     * List of the observer on the coin number.
     */
    private final Set<ChangeCoinNumberObserver> changeCoinNumberObservers = new HashSet<>();


    /**
     * The constructor for the school board of a player. Based on the number of players, the initial
     * number of towers and the maximum number of student in the entrance changes.
     * @param isThreePlayerGame if the game played is a match between three players or not. According to this,
     *                          the number of towers and student in entrance changes.
     * @param coinsBag the coins bag
     * @param nickNameOfPlayer the nickname of the player to which this school board is associated to
     */
    SchoolBoard(boolean isThreePlayerGame, CoinsBag coinsBag, String nickNameOfPlayer){

        this.nickNameOfPlayer=nickNameOfPlayer;

        diningRoom = new DiningRoom(nickNameOfPlayer);

        if (isThreePlayerGame) {
            maxNumStudentsInEntrance = 9;
            maxNumTowers = towers = 6;
        }
        else {
            maxNumStudentsInEntrance = 7;
            maxNumTowers = towers = 8;
        }
        this.coinsBag = coinsBag;
        takeCoin();
    }

    private void takeCoin(){
        coinsBag.takeCoin();
        coins++;
    }

    /**
     * This method allows to add the observer, passed as a parameter, on this school board.
     * @param observer the observer to be added
     */
    void addSchoolBoardObserver(SchoolBoardObserver observer) {
        schoolBoardObservers.add(observer);
        diningRoom.addStudentsInDiningRoomObserver(observer);
    }

    /**
     * This method allows to add the observer, passed as a parameter, on the coin number.
     * @param observer the observer to be added
     */
    void addChangeCoinNumberObserver(ChangeCoinNumberObserver observer){
        changeCoinNumberObservers.add(observer);
    }

    /**
     * Gets the students in this school board entrance.
     * <p>
     * Note: this should be used only to observe the content. To modify it, use the
     * appropriate methods of {@code SchoolBoard}.
     * @return the students in the entrance
     * @see #addStudentToEntrance(PawnType)
     * @see #removeStudentFromEntrance(PawnType)
     */
    StudentList getStudentsInEntrance() {
        return entrance.clone();
    }

    /**
     * Gets the professors in this school board.
     * <p>
     * Note: this should be used only to observe the content. To modify it, use the
     * appropriate methods of {@code SchoolBoard}.
     * @return the professors in this school board
     * @see #addProfessor(PawnType)
     * @see #removeProfessor(PawnType)
     */
    Collection<PawnType> getProfessors(){
        return new HashSet<>(professorTable);
    }

    int getTowersNumber(){
        return towers;
    }

    /**
     * Gets the number of student of a particular type that the player has in his dining room.
     * @param type the type of student to check the number
     * @return the number of student of that type
     */
    int getNumStudentsOf(PawnType type){
        return diningRoom.getNumStudentsOf(type);
    }

    int getCoins(){
        return coins;
    }

    /**
     * Add a student of the given type to the entrance.
     * @param type the type of student to add
     * @throws ReachedMaxStudentException if the entrance already has the maximum number of students
     */
    void addStudentToEntrance(PawnType type) throws ReachedMaxStudentException {
        if (entrance.numAllStudents() == maxNumStudentsInEntrance) throw new ReachedMaxStudentException();
        try {
            entrance.changeNumOf(type, 1);
            notifyStudentsOnEntranceObservers();
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();
        }
    }

    /**
     * Remove a student of the given type from the entrance.
     * @param type the type of student to remove
     * @throws NotEnoughStudentException if there aren't student of the given type
     */
    void removeStudentFromEntrance(PawnType type) throws NotEnoughStudentException {
        entrance.changeNumOf(type, -1);
        notifyStudentsOnEntranceObservers();
    }

    /**
     * Add a professor of a particular type in the professor table of this school board
     * @param type the type of professor to add
     */
    void addProfessor(PawnType type){
        professorTable.add(type);
        notifyProfessorObservers();
    }

    /**
     * Remove a professor of a particular type in the professor table of this school board
     * @param type the type of professor to remove
     */
    void removeProfessor(PawnType type){
        professorTable.remove(type);
        notifyProfessorObservers();
    }

    /**
     * Add a student of the given type in the corresponding table in the dining room. If the student
     * ends on a coin icon, this will also add a coin to the player.
     * @param type the type of student to add
     * @throws ReachedMaxStudentException if the table of that type is full
     */
    void addStudentToDiningRoom(PawnType type) throws ReachedMaxStudentException {
        boolean needCoin = diningRoom.addStudentOf(type);
        if (needCoin) {
            takeCoin();
            notifyChangeCoinNumberObservers();
        }
    }

    /**
     * Remove a student of the given type from the corresponding table in the dining room.
     * @param type the type of student to remove
     * @throws NotEnoughStudentException if the table of the given type is empty
     */
    void removeStudentFromDiningRoom(PawnType type) throws NotEnoughStudentException {
        diningRoom.removeStudentOf(type);
    }

    /**
     * Remove the specified amount of coins from the player's reserve.
     * The {@code cost} must be grater than zero.
     * @param cost the amount of coins to remove
     * @param putInBag is true if all the coins should be put in the general reserve of coins,
     *                 false if one of them should be used to indicate that
     *                 the cost of the character card should be increased
     * @throws NotEnoughCoinsException if there aren't enough coins to fulfill the request
     */
    void removeCoin(int cost, boolean putInBag) throws NotEnoughCoinsException {
        assert cost > 0 : "The cost can't be negative";
        if (cost > coins) {
            throw new NotEnoughCoinsException();
        }
        coins -= cost;
        if(putInBag){
            // put all the coins in the bag
            coinsBag.addCoins(cost);
        }else{
            // put all the coins except one in the bag
            // the other should be put on the card used
            coinsBag.addCoins(cost-1);
        }
        notifyChangeCoinNumberObservers();
    }

    /**
     * Change the number of towers in this school board by adding {@code delta} on it.
     * Based on the sign of {@code delta} this could be an increase (if positive) or a decrease
     * (if negative).
     * <p>
     * The number of towers can't exceed the maximum supported.
     * @param delta the amount to change
     */
    void changeTowerNumber(int delta){
        assert towers + delta <= maxNumTowers : "The towers added are too much";
        towers += delta;
        notifyChangeTowerNumberObservers();
    }

    // MANAGEMENT OF OBSERVERS

    /**
     * This method notify all the attached observers that a change has been happened on the students on entrance.
     */
    private void notifyStudentsOnEntranceObservers(){
        for(SchoolBoardObserver observer : schoolBoardObservers)
            observer.studentsOnEntranceObserverUpdate(nickNameOfPlayer, entrance.clone());
    }

    /**
     * This method notify all the attached observers that a change has been happened on the assistant deck.
     */
    private void notifyProfessorObservers(){
        for(SchoolBoardObserver observer : schoolBoardObservers)
            observer.professorObserverUpdate(nickNameOfPlayer, new HashSet<>(professorTable));
    }

    /**
     * This method notify all the attached observers that a change has been happened on the tower number.
     */
    private void notifyChangeTowerNumberObservers(){
        for(SchoolBoardObserver observer : schoolBoardObservers)
            observer.changeTowerNumberUpdate(nickNameOfPlayer, towers);
    }

    /**
     * This method notify all the attached observers that a change has been happened on the coin number.
     */
    private void notifyChangeCoinNumberObservers(){
        for(ChangeCoinNumberObserver observer : changeCoinNumberObservers)
            observer.changeCoinNumberObserverUpdate(nickNameOfPlayer, coins);
    }

}
