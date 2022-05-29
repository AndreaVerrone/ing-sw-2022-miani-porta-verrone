package it.polimi.ingsw.server.controller.game;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.model.gametable.GameTable;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
     * this method will return the number of student on the islands specified in the parameter
     * @param islandID island on which count student
     * @param gameTable the table on which the island is present
     * @throws IslandNotFoundException if the ID of the island passed as a parameter doe not exits
     * @return the number of students on the island.
     */
    private int countStudentsOnIsland(int islandID, GameTable gameTable) throws IslandNotFoundException {
        int numOfStudents=0;
        for(PawnType color : PawnType.values()){
            numOfStudents = numOfStudents + gameTable.getIsland(islandID).numStudentsOf(color);
        }
        return numOfStudents;
    }

    @Test
    public void Game_ShouldAddStudentsOnIsland() {

        int motherNaturePos = game.getModel().getGameTable().getMotherNaturePosition();
        int oppositeMotherNaturePosition = (motherNaturePos+6)%12;

        // 1. check that on the island on which there is mother nature and in the opposite one there are no students on 1

        // islands on which there is mother nature and the opposite one
        List<Integer> islandsWithNoStudents = new ArrayList<>(List.of(motherNaturePos,oppositeMotherNaturePosition));
        for (Integer i : islandsWithNoStudents) {
            try {
                //assertEquals(0, game.getModel().getGameTable().getIsland(i).getStudentList().numAllStudents());
                assertEquals(0, countStudentsOnIsland(i,game.getModel().getGameTable()));
            } catch (IslandNotFoundException e) {
                fail();
            }
        }

        // 2. check that in all the other islands there is one student on it

        // all the other islands
        List<Integer> islandsWithStudents = new ArrayList<>();
        for(int i=0;i<12;i++){
            if(i!=motherNaturePos && i!=oppositeMotherNaturePosition){
                islandsWithStudents.add(i);
            }
        }

        for (Integer i : islandsWithStudents) {
            try {
                assertEquals(1, countStudentsOnIsland(i,game.getModel().getGameTable()));
            } catch (IslandNotFoundException e) {
                fail();
            }
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