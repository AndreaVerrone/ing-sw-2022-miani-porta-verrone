package it.polimi.ingsw.model.gametable;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;
import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.gametable.exceptions.CloudNotFoundException;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTableTest {
    private GameTable gameTable;

    @BeforeEach
    void setUp() {
        gameTable = new GameTable(2);
    }

    @AfterEach
    void tearDown() {
        gameTable  = null;
    }

    @Test
    void gameTableConstructor_fiveClouds_shouldThrowException(){
        assertThrows(AssertionError.class, () -> new GameTable(5));
    }

    @Test
    void gameTableConstructor_oneCloud_shouldThrowException(){
        assertThrows(AssertionError.class, () -> new GameTable(1));
    }

    @Test
    void getIsland_IDNotPresent_ShouldThrowException() {
        assertThrows(IslandNotFoundException.class, () ->gameTable.getIsland(13));
    }

    @Test
    void getIsland_IDPresent(){
        Island island = null;
        try {
            island = gameTable.getIsland(6);
        } catch (IslandNotFoundException e) {
            fail();
        }
        assertEquals(6, island.getID());
    }

    @Test
    void getFromCloud_IDNotPresent_ShouldThrow(){
        int IDTooLow = -1;
        int IDTooBig = gameTable.getNumberOfClouds();
        assertThrows(CloudNotFoundException.class, () -> gameTable.getFromCloud(IDTooLow));
        assertThrows(CloudNotFoundException.class, () -> gameTable.getFromCloud(IDTooBig));
    }


    @Test
    void moveMotherNature_differentMovements() {
        gameTable.moveMotherNature(25);
        assertEquals(1, gameTable.getMotherNaturePosition());
        gameTable.moveMotherNature(16);
        assertEquals(5, gameTable.getMotherNaturePosition());
        gameTable.moveMotherNature(3);
        assertEquals(8, gameTable.getMotherNaturePosition());
        gameTable.moveMotherNature(9);
        assertEquals(5, gameTable.getMotherNaturePosition());
        gameTable.moveMotherNature(0);
        assertEquals(5, gameTable.getMotherNaturePosition());
    }

    @Test
    void addToIsland_IslandExists_ShouldAdd() {
        PawnType type = PawnType.RED_DRAGONS;
        try {
            gameTable.addToIsland(type, 3);
            Island islandTest = gameTable.getIsland(3);
            assertEquals(1, islandTest.numStudentsOf(type));
        } catch (IslandNotFoundException e)
        {
            fail();
        }
    }

    @Test
    void addToIsland_IslandNotExists_ShouldThrow() {
        PawnType type = PawnType.RED_DRAGONS;
        assertThrows(IslandNotFoundException.class, () -> gameTable.addToIsland(type, 13));
    }


    @Test
    void fillClouds_StudentsBagFull_ShouldAdd() {
        StudentList studentsForBag = new StudentList();
        try {
            studentsForBag.changeNumOf(PawnType.RED_DRAGONS, 40);
            studentsForBag.changeNumOf(PawnType.PINK_FAIRIES, 40);
            studentsForBag.changeNumOf(PawnType.BLUE_UNICORNS, 40);
            studentsForBag.changeNumOf(PawnType.GREEN_FROGS, 40);
        } catch (NotEnoughStudentException e) {
            fail();
        }
        gameTable.fillBag(studentsForBag);
        gameTable.fillClouds();
        for (int ID = 0; ID < gameTable.getNumberOfClouds(); ID++){
            try {
                assertEquals(3, gameTable.getFromCloud(ID).numAllStudents());
            } catch (CloudNotFoundException e) {
                fail();
            }
        }
    }

    @Test
    public void fillClouds_StudentsBagAlmostEmpty_ShouldPutAllRemaining(){
        StudentList studentList = new StudentList();
        try {
            studentList.changeNumOf(PawnType.RED_DRAGONS, 2);
        } catch (NotEnoughStudentException e) {
            throw new RuntimeException(e);
        }
        gameTable.fillBag(studentList);
        gameTable.fillClouds();
        try {
            assertEquals(2, gameTable.getFromCloud(0).numAllStudents());
        } catch (CloudNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void fillClouds_StudentsBagEmpty_ShouldDoNothing() {
        StudentList studentsForBag = new StudentList();
        //Fill the bag with an empty students list
        gameTable.fillBag(studentsForBag);
        try {
            int studentsOnCloud = gameTable.getFromCloud(0).numAllStudents();
            assertEquals(0, studentsOnCloud);
        } catch (CloudNotFoundException e) {
            fail();
        }
    }

    @Test
    public void checkForUnify_WithIslandDifferentTowers_ShouldDoNothing(){
        Island island;
        try {
            island = gameTable.getIsland(3);
        } catch (IslandNotFoundException e) {
            fail();
            return;
        }
        island.setTower(TowerType.BLACK);
        int previousIslands = gameTable.getNumberOfIslands();
        int previousSize = island.getSize();
        gameTable.checkForUnify(island);
        assertEquals(previousIslands, gameTable.getNumberOfIslands());
        assertEquals(previousSize, island.getSize());
    }

    @Test
    public void checkForUnify_WithIslandBeforeSameTower_ShouldUnify(){
        Island island;
        Island islandBefore;
        try {
            island = gameTable.getIsland(3);
            islandBefore = gameTable.getIsland(2);
        } catch (IslandNotFoundException e) {
            fail();
            return;
        }
        island.setTower(TowerType.BLACK);
        islandBefore.setTower(TowerType.BLACK);
        int previousSize = island.getSize();
        gameTable.checkForUnify(island);
        assertEquals(previousSize + 1, island.getSize());
    }

    @Test
    public void checkForUnify_WithIslandBeforeSameTower_ShouldRemoveIt(){
        Island island;
        Island islandBefore;
        try {
            island = gameTable.getIsland(3);
            islandBefore = gameTable.getIsland(2);
        } catch (IslandNotFoundException e) {
            fail();
            return;
        }
        island.setTower(TowerType.BLACK);
        islandBefore.setTower(TowerType.BLACK);
        int previousIslands = gameTable.getNumberOfIslands();
        gameTable.checkForUnify(island);
        assertEquals(previousIslands - 1, gameTable.getNumberOfIslands());
        assertThrows(IslandNotFoundException.class,
                () -> gameTable.getIsland(islandBefore.getID()));
    }

    @Test
    public void checkForUnify_WithIslandsAdjacentSameTower_ShouldUnify(){
        Island island;
        Island islandBefore;
        Island islandAfter;
        try {
            island = gameTable.getIsland(11);
            islandBefore = gameTable.getIsland(10);
            islandAfter = gameTable.getIsland(0);
        } catch (IslandNotFoundException e) {
            fail();
            return;
        }
        island.setTower(TowerType.BLACK);
        islandBefore.setTower(TowerType.BLACK);
        islandAfter.setTower(TowerType.BLACK);
        int previousSize = island.getSize();
        gameTable.checkForUnify(island);
        assertEquals(previousSize + 2, island.getSize());
    }

    @Test
    public void checkForUnify_WithIslandsAdjacentSameTower_ShouldRemoveThem(){
        Island island;
        Island islandBefore;
        Island islandAfter;
        try {
            island = gameTable.getIsland(11);
            islandBefore = gameTable.getIsland(10);
            islandAfter = gameTable.getIsland(0);
        } catch (IslandNotFoundException e) {
            fail();
            return;
        }
        island.setTower(TowerType.BLACK);
        islandBefore.setTower(TowerType.BLACK);
        islandAfter.setTower(TowerType.BLACK);
        int previousIslands = gameTable.getNumberOfIslands();
        gameTable.checkForUnify(island);
        assertEquals(previousIslands - 2, gameTable.getNumberOfIslands());
        assertThrows(IslandNotFoundException.class,
                () -> gameTable.getIsland(islandBefore.getID()));
        assertThrows(IslandNotFoundException.class,
                () -> gameTable.getIsland(islandAfter.getID()));
    }

    @Test
    public void checkForUnify_WithIslandNotPresent_ShouldThrow(){
        Island island;
        Island islandBefore;
        try {
            island = gameTable.getIsland(3);
            islandBefore = gameTable.getIsland(2);
        } catch (IslandNotFoundException e) {
            fail();
            return;
        }
        island.setTower(TowerType.BLACK);
        islandBefore.setTower(TowerType.BLACK);
        gameTable.checkForUnify(island);

        assertThrows(AssertionError.class,
                () -> gameTable.checkForUnify(islandBefore));
    }
}