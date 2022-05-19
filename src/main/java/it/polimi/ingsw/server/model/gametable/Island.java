package it.polimi.ingsw.server.model.gametable;

import it.polimi.ingsw.server.model.NotEnoughStudentException;
import it.polimi.ingsw.server.model.PawnType;
import it.polimi.ingsw.server.model.StudentList;
import it.polimi.ingsw.server.model.TowerType;
import it.polimi.ingsw.server.observers.BanOnIslandObserver;
import it.polimi.ingsw.server.observers.IslandUnificationObserver;
import it.polimi.ingsw.server.observers.StudentsOnIslandObserver;
import it.polimi.ingsw.server.observers.TowerOnIslandObserver;

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
    protected void addStudentOf(PawnType type) {
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
    protected void unifyWith(Island island){
        assert ID != island.ID : "You can't unify the same island";
        assert tower == island.tower : "You're trying to unify two islands that have different towers on them";

        students.add(island.students);
        size += island.size;
        ban += island.ban;

        // notify the changes
        notifyUnificationIslandObservers(island.ID, this.size);
        notifyBanOnIslandObservers(island.ID,this.ban);
        notifyStudentsOnIslandObservers(island.ID,this.students.clone());

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
     * @param islandIDWithBan the island on which a ban has been put or removed
     * @param actualNumOfBans the actual num of bans on the island
     */
    private void notifyBanOnIslandObservers(int islandIDWithBan, int actualNumOfBans){
        for(BanOnIslandObserver observer : banOnIslandObservers)
            observer.banOnIslandObserverUpdate(islandIDWithBan, actualNumOfBans);
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
     * @param islandID the islandID of the island on which the students has been changed
     * @param actualStudents the actual student list on island
     */
    private void notifyStudentsOnIslandObservers(int islandID, StudentList actualStudents){
        for(StudentsOnIslandObserver observer : studentsOnIslandObservers)
            observer.studentsOnIslandObserverUpdate(islandID, actualStudents);
    }

    // MANAGEMENT OF OBSERVERS ON UNIFICATION OF ISLANDS
    /**
     * List of the observer on unification of island
     */
    private final List<IslandUnificationObserver> unificationIslandObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the unification of islands.
     * @param observer the observer to be added
     */
    public void addUnificationIslandObserver(IslandUnificationObserver observer){
        unificationIslandObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the unification of islands.
     * @param observer the observer to be removed
     */
    public void removeUnificationIslandObserver(IslandUnificationObserver observer){
        unificationIslandObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers a change involving the unification of islands.
     * @param islandRemovedID ID of the island that has been removed
     * @param finalSize the size of the island after unification
     */
    private void notifyUnificationIslandObservers(int islandRemovedID, int finalSize){
        for(IslandUnificationObserver observer : unificationIslandObservers)
            observer.islandUnificationObserverUpdate(islandRemovedID,finalSize);
    }

    // MANAGEMENT OF OBSERVERS ON TOWER ON ISLAND
    /**
     * List of the observer on tower on island
     */
    private final List<TowerOnIslandObserver> towerOnIslandObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on tower on island.
     * @param observer the observer to be added
     */
    public void addTowerOnIslandObserver(TowerOnIslandObserver observer){
        towerOnIslandObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on tower on island.
     * @param observer the observer to be removed
     */
    public void removeTowerOnIslandObserver(TowerOnIslandObserver observer){
        towerOnIslandObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on tower on island.
     * @param islandIDWithChange the island on which a tower has been put or removed
     * @param actualTower the actual tower on the island
     */
    private void notifyTowerOnIslandObservers(int islandIDWithChange, TowerType actualTower){
        for(TowerOnIslandObserver observer : towerOnIslandObservers)
            observer.towerOnIslandObserverUpdate(islandIDWithChange, actualTower);
    }
}
