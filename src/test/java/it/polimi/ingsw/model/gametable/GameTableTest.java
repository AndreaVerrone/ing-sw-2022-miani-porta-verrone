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
        } catch (TooManyCloudsException | NotEnoughClouds e) {
            e.printStackTrace();
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
        assertThrows(NotEnoughClouds.class, () -> new GameTable(1));
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
                e.printStackTrace();
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
    void unify_TwoValidIslands() throws IslandNotFoundException {
        gameTable.addToIsland(PawnType.RED_DRAGONS, 1);
        gameTable.getIsland(0).setTower(TowerType.BLACK);
        gameTable.getIsland(1). setTower(TowerType.BLACK);
        gameTable.unify(0,1);
        // TODO: control on list merging in island 0
        assertEquals(11, gameTable.getNumberOfIslands());
        assertThrows(IslandNotFoundException.class, () -> gameTable.getIsland(1));

    }
}