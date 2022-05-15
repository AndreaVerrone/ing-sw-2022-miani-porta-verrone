package it.polimi.ingsw.model.gametable;

import it.polimi.ingsw.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing the island in the game.
 */
public class Island {

    /**
     * The unique ID of this island
     */
    private final int ID;

    /**
     * The students on this island
     */
    private final StudentList students = new StudentList();

    /**
     * The type of tower on this island, or null if not present
     */
    private TowerType tower = null;

    /**
     * The number of island tile that form this island. A value of one correspond on a single island,
     * a value of two correspond to an island composed by two island tiles that were merged and so on.
     */
    private int size = 1;

    /**
     * The number of ban on the island.
     * If the island has at least 1 ban, it must be ignored when mother nature lands on it.
     */
    private int ban;

    /**
     * A class representing the island in the game.
     * @param ID the unique ID of this island
     */
    protected Island(int ID){
        this.ID = ID;
    }


    public int getID(){
        return ID;
    }

    public int getSize(){
        return size;
    }

    public TowerType getTower(){
        return tower;
    }

    /**
     * Gets the number of students of a particular type on this island.
     * @param type the type of student to look for
     * @return the number of students of that type
     */
    public int numStudentsOf(PawnType type){
        return students.getNumOf(type);
    }

    public int getBan(){
        return ban;
    }

    /**
     * Replace the tower on this island with the one passed as argument
     * @param tower the new tower to place on this island
     */
    public void setTower(TowerType tower){
        assert tower != null;
        this.tower = tower;
    }

    /**
     * This method will add one ban card on the island.
     */
    public void addBan(){
        ban = ban + 1;
        notifyBanOnIslandObservers();
    }

    /**
     * This method will remove one ban from the island.
     */
    public void removeBan(){
        if(ban>0){
            ban = ban - 1;
            notifyBanOnIslandObservers();
        }
    }

    /**
     * Add a student of the type passed as argument on this island
     * @param type the type of student to add
     */
    protected void addStudentOf(PawnType type) {
        try {
            students.changeNumOf(type, 1);
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();
        }
        notifyStudentsOnIslandObservers();
    }

    /**
     * Unify this island with the one passed as argument. After this call, this island will contain students
     * from both island and will see its size increased by one. Also, if {@code island} was disabled
     * this will also be disabled.
     * <p>
     * After this call, remember to remove the passed island from the game in order to not have duplicates.
     * </p>
     * @param island the island that need to be unified with this one.
     */
    protected void unifyWith(Island island){
        assert ID != island.ID : "You can't unify the same island";
        assert tower == island.tower : "You're trying to unify two islands that have different towers on them";

        students.add(island.students);
        size += island.size;
        ban += island.ban;

    }

    // MANAGEMENT OF OBSERVERS ON BAN ON ISLAND
    /**
     * List of the observer on ban on island
     */
    private final List<BanOnIslandObserver> banOnIslandObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on ban on island.
     * @param observer the observer to be added
     */
    public void addBanOnIslandObserver(BanOnIslandObserver observer){
        banOnIslandObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on ban on island.
     * @param observer the observer to be removed
     */
    public void removeBanOnIslandObserver(BanOnIslandObserver observer){
        banOnIslandObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on ban on island.
     */
    public void notifyBanOnIslandObservers(){
        for(BanOnIslandObserver observer : banOnIslandObservers)
            observer.banOnIslandObserverUpdate();
    }

    // MANAGEMENT OF OBSERVERS ON STUDENTS ON ISLAND
    /**
     * List of the observer on the students on island
     */
    private final List<StudentsOnIslandObserver> studentsOnIslandObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the students on island.
     * @param observer the observer to be added
     */
    public void addStudentsOnIslandObserver(StudentsOnIslandObserver observer){
        studentsOnIslandObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the students on island.
     * @param observer the observer to be removed
     */
    public void removeStudentsOnIslandObserver(StudentsOnIslandObserver observer){
        studentsOnIslandObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on the students on island.
     */
    public void notifyStudentsOnIslandObservers(){
        for(StudentsOnIslandObserver observer : studentsOnIslandObservers)
            observer.studentsOnIslandObserverUpdate();
    }
}
