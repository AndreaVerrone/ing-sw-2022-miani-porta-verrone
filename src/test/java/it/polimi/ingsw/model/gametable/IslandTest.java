package it.polimi.ingsw.model.gametable;

import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.TowerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IslandTest {

    private Island island1 = null;
    private Island island2 = null;

    @BeforeEach
    void setUp() {
        island1 = new Island(1);
        island1.setTower(TowerType.BLACK);
        island2 = new Island(2);
        island2.setTower(TowerType.BLACK);
    }

    @AfterEach
    void tearDown() {
        island1 = null;
        island2 = null;
    }

    @Test
    public void unifyWith_CorrectIsland_ShouldIncreaseSize(){
        Island island3 = new Island(3);
        island3.setTower(TowerType.BLACK);
        island2.unifyWith(island3);
        assertEquals(2, island2.getSize());

        island1.unifyWith(island2);
        assertEquals(3, island1.getSize());
    }

    @Test
    public void unifyWith_CorrectIsland_ShouldAddStudents(){
        // add students on islands
        island1.addStudentOf(PawnType.GREEN_FROGS);
        island2.addStudentOf(PawnType.GREEN_FROGS);

        island1.unifyWith(island2);
        assertEquals(2, island1.numStudentsOf(PawnType.GREEN_FROGS));
    }

    @Test
    public void unifyWith_CorrectIslandDisabled_ShouldDisable(){
        // disable island2
        island2.setAsDisabled(true);

        island1.unifyWith(island2);
        assertTrue(island1.isDisabled());
    }

    @Test
    public void unifyWith_SameIsland_ShouldThrow(){
        assertThrows(AssertionError.class,
                () -> island1.unifyWith(island1));
    }

    @Test
    public void unifyWith_IslandWithDifferentTowers_ShouldThrow(){
        // change tower of island2
        island2.setTower(TowerType.WHITE);

        assertThrows(AssertionError.class,
                () -> island1.unifyWith(island2));
    }
}