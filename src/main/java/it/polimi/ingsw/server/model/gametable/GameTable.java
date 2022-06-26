package it.polimi.ingsw.server.model.gametable;

import it.polimi.ingsw.client.reduced_model.ReducedCloud;
import it.polimi.ingsw.client.reduced_model.ReducedIsland;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.CloudNotFoundException;
import it.polimi.ingsw.server.model.utils.exceptions.EmptyBagException;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;
import it.polimi.ingsw.server.observers.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class GameTable {
    /**
     * Maximum number of students per cloud. It depends on the number of players
     */
    private final int maxStudentPerCloud;
    /**
     * Mother nature position on the board represented by the id of an island in {@code islands}.
     */
    private int motherNaturePosition  = 0;
    /**
     * A list of all the ids of the islands present in the game
     */
    private final List<Integer> islandsID = new ArrayList<>();
    /**
     * Islands on the game table
     */
    private final List<Island> islands;
    /**
     * Clouds on the game table
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
            islandsID.add(i);
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
     * This method allow to take one student from the student bag and removing it.
     * @return the PawnType of the student extracted
     * @throws EmptyBagException if the bag is empty
     */
    public PawnType getStudentFromBag() throws EmptyBagException {
        return studentsBag.draw();
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
        return students;
    }

    /**
     * Moves mother nature of a number of islands given. Goes back to the first island after surpassing the last one
     * @param numberOfIslands number of islands on which mother nature moves
     */
    public void moveMotherNature(int numberOfIslands){
        assert (numberOfIslands>=0): "Movements cannot be negative!";

        int index = islandsID.indexOf(motherNaturePosition);
        index = (numberOfIslands + index) % islandsID.size();
        motherNaturePosition = islandsID.get(index);
        notifyMotherNaturePositionObservers(motherNaturePosition);
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
            while (cloud.numOfStudents() < maxStudentPerCloud){
                try {
                    student = studentsBag.draw();
                    cloud.addStudent(student);
                    if (studentsBag.studentsRemaining() == 0)
                        return;
                } catch (EmptyBagException e) {
                    e.printStackTrace();
                }

            }
        }
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

    }

    private void unify(Island island, Island islandAdjacent){
        int indexDistance = Math.abs(islands.indexOf(island) - islands.indexOf(islandAdjacent));
        assert indexDistance == 1 || indexDistance == islands.size() - 1 : "Islands not adjacent";

        if (island.getTower() == islandAdjacent.getTower()){
            island.unifyWith(islandAdjacent);
            islands.remove(islandAdjacent);
            islandsID.remove(islandAdjacent.getID());
            notifyIslandNumberObservers(getNumberOfIslands());
        }
    }

    /**
     * Fill the {@code studentsBag} with the {@code StudentList} given
     * @param students {@code StudentList} to add to the bag
     */
    public void fillBag(StudentList students){
        studentsBag.fillWith(students);
    }

    // MANAGEMENT OF OBSERVERS ON NUMBER OF ISLANDS
    /**
     * List of the observer on the number of islands.
     */
    private final List<IslandNumberObserver> islandNumberObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the number of islands.
     * @param observer the observer to be added
     */
    public void addIslandNumberObserver(IslandNumberObserver observer){
        islandNumberObservers.add(observer);
     }


    /**
     * This method notify all the attached observers a change on the number of islands.
     * @param actualNumOfIslands the actual number of island on the game table
     */
      private void notifyIslandNumberObservers(int actualNumOfIslands){
        for(IslandNumberObserver observer : islandNumberObservers)
            observer.islandNumberObserverUpdate(actualNumOfIslands);
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
     * This method notify all the attached observers that a change has been happened on mother nature position.
     * @param actualMotherNaturePosition the actual islandID on which mother nature is
     */
    private void notifyMotherNaturePositionObservers(int actualMotherNaturePosition){
        for(MotherNaturePositionObserver observer : motherNaturePositionObservers)
            observer.motherNaturePositionObserverUpdate(actualMotherNaturePosition);
    }

    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON STUDENTS ON CLOUD OBSERVERS
    /**
     * This method allows to add the observer, passed as a parameter, on students on cloud.
     * @param observer the observer to be added
     */
    public void addStudentsOnCloudObserver(StudentsOnCloudObserver observer){
        for (Cloud cloud : clouds) {
            cloud.addStudentsOnCloudObserver(observer);
        }
    }

    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON BAN ON ISLAND

    /**
     * This method allows to add the observer, passed as a parameter, on ban on island.
     * @param observer the observer to be added
     */
    public void addBanOnIslandObserver(BanOnIslandObserver observer){
        for (Island island : islands) {
            island.addBanOnIslandObserver(observer);
        }
    }


    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON STUDENTS ON ISLAND

    /**
     * This method allows to add the observer, passed as a parameter, on the students on island.
     * @param observer the observer to be added
     */
    public void addStudentsOnIslandObserver(StudentsOnIslandObserver observer){
        for (Island island : islands) {
            island.addStudentsOnIslandObserver(observer);
        }
    }


    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON UNIFICATION OF ISLANDS

    /**
     * This method allows to add the observer, passed as a parameter, on the unification of islands.
     * @param observer the observer to be added
     */
    public void addUnificationIslandObserver(IslandUnificationObserver observer){
        for (Island island : islands) {
            island.addUnificationIslandObserver(observer);
        }
    }


    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON TOWER ON ISLAND

    /**
     * This method allows to add the observer, passed as a parameter, on tower on island.
     * @param observer the observer to be added
     */
    public void addTowerOnIslandObserver(TowerOnIslandObserver observer){
        for (Island island : islands) {
            island.addTowerOnIslandObserver(observer);
        }
    }


    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON EMPTY STUDENT BAG

    /**
     * This method allows to add the observer, passed as a parameter, on tower on empty student bag.
     * @param observer the observer to be added
     */
    public void addEmptyStudentBagObserver(EmptyStudentBagObserver observer){
        studentsBag.addEmptyStudentBagObserver(observer);
    }

    // CREATION OF THE REDUCED VERSIONS

    /**
     * Creates a reduced version of the set of clouds
     * it is used to represent it client side.
     * @return a reduced version of this set of clouds
     */
    public Collection<ReducedCloud> createReducedSetOfClouds(){
        Collection<ReducedCloud> reducedClouds = new ArrayList<>();
        for(Cloud cloud: clouds){
            reducedClouds.add(cloud.createCloudReduction());
        }
        return reducedClouds;
    }

    // CREATION OF THE REDUCED VERSION
    /**
     * Creates a reduced version of the set of islands
     * it is used to represent it client side.
     * @return a reduced version of this set of islands
     */
    public Collection<ReducedIsland> createReducedSetOfIslands(){
        Collection<ReducedIsland> reducedIslands = new ArrayList<>();
        for(Island island: islands){
            reducedIslands.add(island.createReduction());
        }
        return reducedIslands;
    }

}

