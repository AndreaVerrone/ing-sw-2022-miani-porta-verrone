package it.polimi.ingsw.server.model.gametable;

import it.polimi.ingsw.client.reduced_model.ReducedIsland;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.observers.game.table.island.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
     * List of the observer on island
     */
    private final Set<IslandObserver> islandObservers = new HashSet<>();

    /**
     * List of the observer on ban on island
     */
    private final Set<BanOnIslandObserver> banOnIslandObservers = new HashSet<>();


    /**
     * A class representing the island in the game.
     * @param ID the unique ID of this island
     */
    protected Island(int ID){
        this.ID = ID;
    }

    /**
     * Adds the observer passed as a parameter to the list of ones observing this island
     * @param islandObserver the observer to add
     */
    void addIslandObserver(IslandObserver islandObserver) {
        islandObservers.add(islandObserver);
    }

    /**
     * This method allows to add the observer, passed as a parameter, on ban on island.
     * @param observer the observer to be added
     */
    void addBanOnIslandObserver(BanOnIslandObserver observer){
        banOnIslandObservers.add(observer);
    }

    int getID(){
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
        notifyTowerOnIslandObservers(this.ID,this.tower);
    }

    /**
     * This method will add one ban card on the island.
     */
    public void addBan(){
        ban = ban + 1;
        notifyBanOnIslandObservers(this.ID, ban);
    }

    /**
     * This method will remove one ban from the island.
     */
    public void removeBan(){
        if(ban>0){
            ban = ban - 1;
            notifyBanOnIslandObservers(this.ID, ban);
        }
    }

    /**
     * Add a student of the type passed as argument on this island
     * @param type the type of student to add
     */
    void addStudentOf(PawnType type) {
        try {
            students.changeNumOf(type, 1);
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();
        }
        notifyStudentsOnIslandObservers(this.ID,students.clone());
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
    void unifyWith(Island island){
        assert ID != island.ID : "You can't unify the same island";
        assert tower == island.tower : "You're trying to unify two islands that have different towers on them";

        students.add(island.students);
        size += island.size;
        ban += island.ban;

        // notify the changes
        notifyUnificationIslandObservers(ID, island.ID, island.size);
        notifyBanOnIslandObservers(ID,this.ban);
        notifyStudentsOnIslandObservers(ID,this.students.clone());

    }

    // MANAGEMENT OF OBSERVERS

    /**
     * This method notify all the attached observers that a change has been happened on ban on island.
     * @param islandIDWithBan the island on which a ban has been put or removed
     * @param actualNumOfBans the actual num of bans on the island
     */
    private void notifyBanOnIslandObservers(int islandIDWithBan, int actualNumOfBans){
        for(BanOnIslandObserver observer : banOnIslandObservers)
            observer.banOnIslandObserverUpdate(islandIDWithBan, actualNumOfBans);
    }

    /**
     * This method notify all the attached observers that a change has been happened on the students on island.
     * @param islandID the islandID of the island on which the students have been changed
     * @param actualStudents the actual student list on island
     */
    private void notifyStudentsOnIslandObservers(int islandID, StudentList actualStudents){
        for(StudentsOnIslandObserver observer : islandObservers)
            observer.studentsOnIslandObserverUpdate(islandID, actualStudents);
    }

    /**
     * This method notify all the attached observers a change involving the unification of islands.
     * @param islandID  the ID of the island kept
     * @param islandRemovedID ID of the island that has been removed
     * @param sizeIslandRemoved the size of the island to remove
     */
    private void notifyUnificationIslandObservers(int islandID, int islandRemovedID, int sizeIslandRemoved){
        for(IslandUnificationObserver observer : islandObservers)
            observer.islandUnificationObserverUpdate(islandID, islandRemovedID,sizeIslandRemoved);
    }

    /**
     * This method notify all the attached observers that a change has been happened on tower on island.
     * @param islandIDWithChange the island on which a tower has been put or removed
     * @param actualTower the actual tower on the island
     */
    private void notifyTowerOnIslandObservers(int islandIDWithChange, TowerType actualTower){
        for(TowerOnIslandObserver observer : islandObservers)
            observer.towerOnIslandObserverUpdate(islandIDWithChange, actualTower);
    }

    /**
     * Creates a reduced version of this island used to represent it client side.
     * @return a reduced version of this island
     * @see ReducedIsland
     */
    ReducedIsland createReduction(){
        return new ReducedIsland(ID, students, tower, size, ban);
    }
}
