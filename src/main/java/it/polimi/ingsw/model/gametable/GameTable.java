package it.polimi.ingsw.model.gametable;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.gametable.exceptions.CloudNotFoundException;
import it.polimi.ingsw.model.gametable.exceptions.EmptyBagException;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;

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
    /**
     * Islands on the gametable
     */
    private final List<Island> islands;
    /**
     * Clouds on the gametable
     */
    private final List<Cloud> clouds;
    /**
     * Bag with the students inside
     */
    private final StudentsBag studentsBag;

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
        if (islandRequested == null) throw new IslandNotFoundException();
        return islandRequested;
    }

    /**
     * Gives all the students on a cloud with a given ID, leaving the cloud empty. If the ID doesn't exist throws an exception
     * @param cloudID the ID of the cloud from which we want to get the students
     * @return a {@code StudentList} of the students on the cloud
     * @throws CloudNotFoundException if the cloud doesn't exist
     */
    public StudentList getFromCloud(int cloudID) throws CloudNotFoundException {
        if (cloudID <0 || cloudID >= clouds.size() ) throw new CloudNotFoundException();
        StudentList students = null;
        for (Cloud cloud : clouds){
            if(cloud.getID() == cloudID) students = cloud.getAllStudents();
        }
        notifyStudentsOnCloudObservers();
        return students;
    }

    /**
     * Moves mother nature of a number of islands given. Goes back to the first island after surpassing the last one
     * @param numberOfIslands number of islands on which mother nature moves
     */
    public void moveMotherNature(int numberOfIslands){
        assert (numberOfIslands>=0): "Movements cannot be negative!";
        motherNaturePosition = (numberOfIslands + motherNaturePosition) % getNumberOfIslands();
        notifyMotherNaturePositionObservers();
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
     * Fills all the clouds with the maximum number of students taken from the bag. If the bag is empty do nothing
     */
    public void fillClouds() {
        PawnType student;
        if (studentsBag.studentsRemaining() == 0) return;
        for (Cloud cloud : clouds){
            for (int i = 0; i < maxStudentPerCloud; i++){
                try {
                    student = studentsBag.draw();
                    cloud.addStudent(student);
                    if (studentsBag.studentsRemaining() == 0) {
                        //TODO: notify observer to end the game
                        return;
                    }
                } catch (EmptyBagException e) {
                    e.printStackTrace();
                }

            }
        }
        notifyStudentsOnCloudObservers();
    }

    /**
     * Checks if the passed island needs to be unified with the ones immediately before and after,
     * and if so it does it.
     * @param island the island to check for unification
     */
    public void checkForUnify(Island island){
        assert islands.contains(island) : "The island is not present on the table";

        int index = islands.indexOf(island);
        int lastIndex = islands.size() - 1;
        int indexBefore = index == 0 ? lastIndex : index - 1;
        int indexAfter = index == lastIndex ? 0 : index + 1;
        Island islandBefore = islands.get(indexBefore);
        Island islandAfter = islands.get(indexAfter);
        unify(island, islandBefore);
        unify(island, islandAfter);

        notifyUnificationIslandObservers();
    }

    private void unify(Island island, Island islandAdjacent){
        int indexDistance = Math.abs(islands.indexOf(island) - islands.indexOf(islandAdjacent));
        assert indexDistance == 1 || indexDistance == islands.size() - 1 : "Islands not adjacent";

        if (island.getTower() == islandAdjacent.getTower()){
            island.unifyWith(islandAdjacent);
            islands.remove(islandAdjacent);
        }
    }

    /**
     * Fill the {@code studentsBag} with the {@code StudentList} given
     * @param students {@code StudentList} to add to the bag
     */
    public void fillBag(StudentList students){
        studentsBag.fillWith(students);
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
     */
      public void notifyUnificationIslandObservers(){
        for(IslandUnificationObserver observer : unificationIslandObservers)
            observer.islandUnificationObserverUpdate();
      }

    // MANAGEMENT OF OBSERVERS ON MOTHER NATURE POSITION
    /**
     * List of the observer on mother nature position
     */
    private final List<MotherNaturePositionObserver> motherNaturePositionObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on mother nature position.
     * @param observer the observer to be added
     */
    public void addMotherNaturePositionObserver(MotherNaturePositionObserver observer){
        motherNaturePositionObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on mother nature position.
     * @param observer the observer to be removed
     */
    public void removeMotherNaturePositionObserver(MotherNaturePositionObserver observer){
        motherNaturePositionObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on mother nature position.
     */
    public void notifyMotherNaturePositionObservers(){
        for(MotherNaturePositionObserver observer : motherNaturePositionObservers)
            observer.motherNaturePositionObserverUpdate();
    }

    // MANAGEMENT OF OBSERVERS ON STUDENTS ON CLOUD
    /**
     * List of the observer on students on cloud
     */
    private final List<StudentsOnCloudObserver> studentsOnCloudObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on students on cloud.
     * @param observer the observer to be added
     */
    public void addStudentsOnCloudObserver(StudentsOnCloudObserver observer){
        studentsOnCloudObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on students on cloud.
     * @param observer the observer to be removed
     */
    public void removeStudentsOnCloudObserver(StudentsOnCloudObserver observer){
        studentsOnCloudObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on students on cloud.
     */
    public void notifyStudentsOnCloudObservers(){
        for(StudentsOnCloudObserver observer : studentsOnCloudObservers)
            observer.studentsOnCloudObserverUpdate();
    }
}

