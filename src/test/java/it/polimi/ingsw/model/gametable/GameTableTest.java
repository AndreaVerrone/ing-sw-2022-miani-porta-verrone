package it.polimi.ingsw.model.gametable;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.StudentList;
import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.gametable.exceptions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTableTest {
    private GameTable gameTable;

    @BeforeEach
    void setUp() {
        try {
            gameTable = new GameTable(2);
        } catch (TooManyCloudsException | NotEnoughCloudsException e) {
            fail();
        }
    }

    @AfterEach
    void tearDown() {
        gameTable  = null;
    }

    @Test
    void gameTableConstructor_fiveClouds_shouldThrowException(){
        assertThrows(TooManyCloudsException.class, () -> new GameTable(5));
    }

    @Test
    void gameTableConstructor_oneCloud_shouldThrowException(){
        assertThrows(NotEnoughCloudsException.class, () -> new GameTable(1));
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
    void getFromCloud() {
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
        try {
            gameTable.fillClouds();
        } catch (EmptyBagException e) {
            fail();
        }
        for (int ID = 0; ID < gameTable.getNumberOfClouds(); ID++){
            try {
                assertEquals(gameTable.getMaxStudentPerCloud(), gameTable.getFromCloud(ID).numAllStudents());
            } catch (CloudNotFoundException e) {
                fail();
            }
        }
        assertEquals(160 - gameTable.getMaxStudentPerCloud() * gameTable.getNumberOfClouds(), gameTable.getFromBag().numAllStudents());
    }

    @Test
    void fillClouds_StudentsBagEmpty_ShouldThrow() {
        StudentList studentsForBag = new StudentList();
        gameTable.fillBag(studentsForBag);
        assertThrows(EmptyBagException.class, () -> gameTable.fillClouds());
    }

    @Test
    void unify_ThreeAdjacentIslands_ShouldMerge(){
        int ID1 = 0;
        int ID2 = 11;
        int ID3 = 1;
        try {
            gameTable.addToIsland(PawnType.RED_DRAGONS, ID2);
            gameTable.addToIsland(PawnType.GREEN_FROGS, ID3);
            gameTable.addToIsland(PawnType.RED_DRAGONS, ID3);
            gameTable.getIsland(ID1).setTower(TowerType.BLACK);
            gameTable.getIsland(ID2).setTower(TowerType.BLACK);
            gameTable.getIsland(ID3).setTower(TowerType.BLACK);
            gameTable.unify(ID1,ID2);
            // TODO: control on list merging in island 0
            //assertEquals(1, gameTable.getIsland(ID1).numStudentsOf(PawnType.RED_DRAGONS));
            assertEquals(2, gameTable.getIsland(ID1).getSize());
            assertEquals(11, gameTable.getNumberOfIslands());
            assertThrows(IslandNotFoundException.class, () -> gameTable.getIsland(ID2));
            gameTable.unify(ID3, ID1);
            // TODO: control on list merging in island 3
            //assertEquals(2, gameTable.getIsland(ID3).numStudentsOf(PawnType.RED_DRAGONS));
            //assertEquals(1, gameTable.getIsland(ID3).numStudentsOf(PawnType.GREEN_FROGS));
            assertEquals(3, gameTable.getIsland(ID3).getSize());
            assertEquals(10, gameTable.getNumberOfIslands());
            assertThrows(IslandNotFoundException.class, () -> gameTable.getIsland(ID1));
        } catch (IslandNotFoundException | IslandsNotAdjacentException e) {
            fail();
        }
    }
    @Test
    void unify_TwoNotAdjacentIslands_ShouldThrow(){
        int ID1 = 3;
        int ID2 = 5;
        try {
            gameTable.addToIsland(PawnType.RED_DRAGONS, ID2);
            gameTable.getIsland(ID1).setTower(TowerType.BLACK);
            gameTable.getIsland(ID2).setTower(TowerType.BLACK);
        } catch (IslandNotFoundException e) {
            fail();
        }
        assertThrows(IslandsNotAdjacentException.class, () -> gameTable.unify(ID1, ID2));
    }
}