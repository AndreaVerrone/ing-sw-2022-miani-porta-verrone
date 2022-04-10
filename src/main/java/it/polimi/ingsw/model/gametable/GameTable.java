package it.polimi.ingsw.model.gametable;

import it.polimi.ingsw.model.LastRoundException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;
import it.polimi.ingsw.model.gametable.exceptions.*;

import java.util.ArrayList;
import java.util.List;

public class GameTable {
    /**
     * Maximum number of students per cloud. It depends on the number of players
     */
    private final int maxStudentPerCloud;
    /**
     * Mother nature position on the board represented by the index of an island in {@code islands}. At every game starts
     * from position zero
     */
    private int motherNaturePosition  = 0;
    private final List<Island> islands;
    private final List<Cloud> clouds;
    private final StudentsBag studentsBag;
    // TODO add private List<TableObserver> observers = new ArrayList<TableObserver>

    /**
     * Constructor of the class. Creates a list of clouds and a list of islands. The number of clouds is given and must not be greater than four, while the number of islands starts
     * always from 12. Moreover, the maximum number of students per cloud is saved in a final attribute as it depends on the number of clouds and cannot change
     * @param numberOfClouds number of clouds on the table. It depends on the number of players
     */
    public GameTable(int numberOfClouds){
        int initialNumberOfIslands = 12;
        assert (numberOfClouds>=2 && numberOfClouds<=4): "Wrong number of clouds!";
        if(numberOfClouds == 3) maxStudentPerCloud = 4;
        else maxStudentPerCloud = 3;
        islands = new ArrayList<>();
        clouds = new ArrayList<>();
        studentsBag = new StudentsBag();

        for (int i = 0; i < initialNumberOfIslands; i++){
            islands.add(new Island(i));
        }
        for (int i=0; i < numberOfClouds; i++){
            clouds.add(new Cloud(i));
        }
    }

    /**
     * Gives the number of islands on the board
     * @return the size of the {@code islands} list
     */
    public int getNumberOfIslands() {
        return islands.size();
    }

    /**
     * Give the number of clouds on the board
     * @return the size of the {@code clouds} list
     */
    public int getNumberOfClouds(){ return clouds.size();}

    public int getMaxStudentPerCloud(){
        return maxStudentPerCloud;
    }

    public int getMotherNaturePosition(){
        return motherNaturePosition;
    }

    /**
     * Returns the island with the ID given. If the island doesn't exist because it has been merged or the ID is wrong,
     * throws an exception
     * @param islandID ID of the island searched
     * @return island with the given ID
     * @throws IslandNotFoundException if the island doesn't exist in the list
     */
    public Island getIsland(int islandID) throws IslandNotFoundException{
        Island islandRequested = null;
        for (Island island: islands){
            if(island.getID() == islandID){
                islandRequested = island;
                break;
            }
        }
        if (islandRequested == null) throw new IslandNotFoundException(islandID);
        return islandRequested;
    }

    /**
     * Gives all the students on a cloud with a given ID, leaving the cloud empty. If the ID is greater than the number of clouds
     * throws an exception
     * @param cloudID the ID of the cloud from which we want to get the students
     * @return a {@code StudentList} of the students on the cloud
     * @throws CloudNotFoundException if the cloud doesn't exist, therefore if the ID is greater than the number of clouds
     * and saves {@code cloudID}
     */
    public StudentList getFromCloud(int cloudID) throws CloudNotFoundException {
        if (cloudID >= clouds.size() ) throw new CloudNotFoundException(cloudID);
        StudentList students = null;
        for (Cloud cloud : clouds){
            if(cloud.getID() == cloudID) students = cloud.getAllStudents();
        }
        return students;
    }

    /**
     * Moves mother nature of a number of islands given. The new position is calculated as the rest of the division between
     * the new position and the number of islands, so it goes back to the first position every time it surpasses the number of islands
     * @param numberOfIslands number of islands on which mother nature moves
     */
    public void moveMotherNature(int numberOfIslands){
        assert (numberOfIslands>=0): "Movements cannot be negative!";
        motherNaturePosition = (numberOfIslands + motherNaturePosition) % getNumberOfIslands();
    }

    /**
     * Adds a given student to the island with {@code islandID} as ID
     * @param student type of student to add to the island
     * @param islandID island's ID to which add the student
     * @throws IslandNotFoundException if the island doesn't exist and saves {@code islandID}
     */
    public void addToIsland(PawnType student, int islandID) throws IslandNotFoundException {
        Island island = getIsland(islandID);
        island.addStudentOf(student);
    }

    /**
     * Fills all the clouds with the maximum number of students taken from the bag
     * @throws EmptyBagException if the bag is empty and there are no more students
     */
    public void fillClouds() throws EmptyBagException, LastRoundException {
        PawnType student;
        for (Cloud cloud : clouds){
            for (int i = 0; i < maxStudentPerCloud; i++){
                student = studentsBag.draw();
                cloud.addStudent(student);
            }
        }
    }

    /**
     * Unifies tWO islands using the {@code unifyWith} method of the island with {@code islandIDToKeep} ID and removes
     * the island with {@code islandIDToRemove} ID from {@code islands} list, in order to eliminate duplicates.
     * <p>Also it controls the two islands given are not adjacent, it finds their indexes in the list and controls
     * their difference is either one or the number of islands minus one, since it would mean they are near each other</p>
     * @param islandIDToKeep ID of the island to unify and keep
     * @param islandIDToRemove ID of the island to unify and remove
     * @throws IslandNotFoundException if either one of the IDs doesn't exist
     * @throws IslandsNotAdjacentException if the two given islands are not adjacent, therefore if  their indexes in the {@code islands} list are near
     */
    public void unify(int islandIDToKeep, int islandIDToRemove) throws IslandNotFoundException, IslandsNotAdjacentException {
        Island islandToKeep = getIsland(islandIDToKeep);
        Island islandToRemove = getIsland(islandIDToRemove);
        int indexIslandToKeep = islands.indexOf(islandToKeep);
        int indexIslandToRemove = islands.indexOf(islandToRemove);
        if(!(Math.abs(indexIslandToKeep - indexIslandToRemove) == 1 | Math.abs(indexIslandToKeep - indexIslandToRemove) == getNumberOfIslands() - 1)){
            throw new IslandsNotAdjacentException(islandIDToKeep, islandIDToRemove);
        }
        islandToKeep.unifyWith(islandToRemove);
        islands.remove(islandToRemove);
    }

    /**
     * Fill the {@code studentsBag} with the {@code StudentList} given
     * @param students {@code StudentList} to add to the bag
     */
    public void fillBag(StudentList students){
        studentsBag.fillWith(students);
    }

    // TODO: add these three classes
    /*
    public void addObserver(TableObserver observer){
        observers.add(observer);
     }

     public void removeObserver(TableObserver observer){
        observers.remove(observer);
      }

      public void notifyObservers(){
        for(TableObserver observer : observers) observer.update();ìì
      }
      */
}

