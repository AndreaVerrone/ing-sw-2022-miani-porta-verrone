package it.polimi.ingsw.server.model.gametable;

import it.polimi.ingsw.server.model.PawnType;
import it.polimi.ingsw.server.model.TowerType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    public void addStudentOf_Blue_ShouldAdd(){
        island1.addStudentOf(PawnType.BLUE_UNICORNS);
        assertEquals(1, island1.numStudentsOf(PawnType.BLUE_UNICORNS));
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
    public void unifyWith_CorrectIslandWith2BanCards_ShouldAdd2BanCard(){
        // add 2 ban to island 2
        island2.addBan();
        island2.addBan();

        island1.unifyWith(island2);
        assertEquals(2,island1.getBan());
    }

    @Test
    public void unifyWith_CorrectIslandWithNoBan_BanShouldBeZero(){
        island1.unifyWith(island2);
        assertEquals(0,island1.getBan());
    }

    @Test
    public void unifyWith_CorrectIslandWithBothIslandsHaveOneBan_BanShouldBeTwo(){
        // add ban to island 1
        island1.addBan();

        // add ban to island 2
        island2.addBan();

        island1.unifyWith(island2);
        assertEquals(2,island1.getBan());
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