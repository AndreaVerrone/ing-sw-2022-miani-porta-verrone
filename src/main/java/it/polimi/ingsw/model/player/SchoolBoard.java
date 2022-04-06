package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

class SchoolBoard {
    private final int maxNumStudentsInEntrance;
    private final int maxNumTowers;

    private final StudentList entrance = new StudentList();
    private final DiningRoom diningRoom = new DiningRoom();
    private final Set<PawnType> professorTable = new HashSet<>();
    private int towers;
    private int coins = 1;

    /**
     * The constructor for the school board of a player. This is used to store information about the students
     * each player has, as well as the number of towers and coins. Based on the number of players, the initial
     * number of towers and the maximum number of student in the entrance changes.
     * @param isThreePlayerGame if the game played is a match between three players or not. According to this,
     *                          the number of towers and student in entrance changes.
     */
    protected SchoolBoard(boolean isThreePlayerGame){
        if (isThreePlayerGame) {
            maxNumStudentsInEntrance = 9;
            maxNumTowers = towers = 6;
        }
        else {
            maxNumStudentsInEntrance = 7;
            maxNumTowers = towers = 8;
        }
    }

    protected StudentList getStudentsInEntrance() {
        return entrance.clone();
    }

    protected Collection<PawnType> getProfessors(){
        return new HashSet<>(professorTable);
    }

    protected int getTowersNumber(){
        return towers;
    }

    /**
     * Gets the number of student of a particular type that the player has in his dining room.
     * @param type the type of student to check the number
     * @return the number of student of that type
     */
    protected int getNumStudentsOf(PawnType type){
        return diningRoom.getNumStudentsOf(type);
    }

    protected int getCoins(){
        return coins;
    }

    protected void addStudentToEntrance(PawnType type) throws ReachedMaxStudentException {
        if (entrance.numAllStudents() == maxNumStudentsInEntrance) throw new ReachedMaxStudentException();
        try {
            entrance.changeNumOf(type, 1);
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();
        }
    }

    protected void removeStudentFromEntrance(PawnType type) throws NotEnoughStudentException {
        entrance.changeNumOf(type, -1);
    }

    protected void addProfessor(PawnType type){
        professorTable.add(type);
    }

    protected void removeProfessor(PawnType type){
        professorTable.remove(type);
    }

    /**
     * Add a student of a particular type in the corresponding table in the dining room. If the student
     * ends on a coin icon, this will also add a coin to the player.
     * @param type the type of student to add
     * @throws ReachedMaxStudentException if the table of that type is full
     */
    protected void addStudentToDiningRoom(PawnType type) throws ReachedMaxStudentException {
        boolean needCoin = diningRoom.addStudentOf(type);
        if (needCoin)
            coins++;
    }

    protected void removeStudentFromDiningRoom(PawnType type) throws NotEnoughStudentException {
        diningRoom.removeStudentOf(type);
    }

    /**
     * Remove the specified amount of coins from the player's reserve.
     * The {@code cost} must be grater than zero.
     * @param cost the amount of coins to remove
     * @throws NotEnoughCoinsException if there aren't enough coins to fulfill the request
     */
    protected void removeCoin(int cost) throws NotEnoughCoinsException {
        assert cost > 0 : "The cost can't be negative";
        if (cost > coins)
            throw new NotEnoughCoinsException();
        coins -= cost;
    }

    protected void changeTowerNumber(int delta){
        assert towers + delta <= maxNumTowers : "The towers added are too much";
        towers += delta;
    }

}
