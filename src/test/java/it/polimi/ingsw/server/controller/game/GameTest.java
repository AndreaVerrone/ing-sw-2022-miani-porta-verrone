package it.polimi.ingsw.server.controller.game;

import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    Game game = null;

    @BeforeEach
    void setUp() {

        PlayerLoginInfo playerLoginInfo1 = new PlayerLoginInfo("player 1");
        PlayerLoginInfo playerLoginInfo2 = new PlayerLoginInfo("player 2");
        PlayerLoginInfo playerLoginInfo3 = new PlayerLoginInfo("player 3");

        game = new Game(List.of(playerLoginInfo1,playerLoginInfo2,playerLoginInfo3));
    }

    @AfterEach
    void tearDown() {
        game = null;
    }

    /**
     * This method will return the number of student on the islands specified in the parameter
     * @param islandID island on which count student
     * @return the number of students on the island.
     */
    private int countStudentsOnIsland(int islandID) {
        int numOfStudents = 0;
        for(PawnType color : PawnType.values()){
            try {
                numOfStudents += game.getModel().getGameTable().getIsland(islandID).numStudentsOf(color);
            } catch (IslandNotFoundException e) {
                fail("Island " + islandID + " not present");
            }
        }
        return numOfStudents;
    }

    @Test
    public void Game_ShouldAddStudentsOnIsland() {

        int motherNaturePos = game.getModel().getGameTable().getMotherNaturePosition();
        int oppositeMotherNaturePosition = (motherNaturePos+6)%12;

        for(int i=0;i<12;i++) {
            int expectedStudents = 1;
            if (i == motherNaturePos || i == oppositeMotherNaturePosition)
                expectedStudents = 0;
            assertEquals(expectedStudents, countStudentsOnIsland(i));
        }

    }


    @Test
    public void Game_3players_ShouldAdd9StudentsInEntrance(){

        // check that if there are 3 player, at the entrance there are 9 students

        for(Player player : game.getModel().getPlayerList()){
            assertEquals(9,player.getStudentsInEntrance().numAllStudents());
        }
    }

    @Test
    public void Game_2players_ShouldAdd7StudentsInEntrance(){

        // heck that if there are 2 player, at the entrance there are 7 students

        PlayerLoginInfo playerLoginInfo1 = new PlayerLoginInfo("player 1");
        PlayerLoginInfo playerLoginInfo2 = new PlayerLoginInfo("player 2");

        game = new Game(List.of(playerLoginInfo1,playerLoginInfo2));

        for(Player player : game.getModel().getPlayerList()){
            assertEquals(7,player.getStudentsInEntrance().numAllStudents());
        }
    }
}