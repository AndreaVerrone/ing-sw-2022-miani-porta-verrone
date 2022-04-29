package it.polimi.ingsw.model;

import it.polimi.ingsw.controller.PlayerLoginInfo;
import it.polimi.ingsw.model.gametable.Island;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Player;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    GameModel gameModel;

    PlayerLoginInfo playerLoginInfo1;

    PlayerLoginInfo playerLoginInfo2;

    PlayerLoginInfo playerLoginInfo3;

    Player player1;
    Player player2;
    Player player3;

    @BeforeEach
    void setUp() {

        playerLoginInfo1=new PlayerLoginInfo("player 1");
        playerLoginInfo1.setWizard(Wizard.W1);
        playerLoginInfo1.setTowerType(TowerType.BLACK);

        playerLoginInfo2 = new PlayerLoginInfo("player 2");
        playerLoginInfo2.setWizard(Wizard.W2);
        playerLoginInfo2.setTowerType(TowerType.WHITE);

        playerLoginInfo3 = new PlayerLoginInfo("player 3");
        playerLoginInfo3.setWizard(Wizard.W3);
        playerLoginInfo3.setTowerType(TowerType.GREY);

        gameModel = new GameModel(List.of(playerLoginInfo1,playerLoginInfo2,playerLoginInfo3));

        List<Player> players = gameModel.getPlayerList();
        player1 = players.get(0);
        player2 = players.get(1);
        player3 = players.get(2);

    }

    @AfterEach
    void tearDown() {
        player1 = null;
        playerLoginInfo1 = null;
        player2 = null;
        playerLoginInfo2 = null;
        player3 = null;
        playerLoginInfo3 = null;
        gameModel = null;
    }

    @Test
    public void calculatePlayersOrder_WithPlayer3UsingLowestAssistant_ShouldBeFirstPlayer() {
        player1.useAssistant(Assistant.CARD_9);
        player2.useAssistant(Assistant.CARD_5);
        player3.useAssistant(Assistant.CARD_1);

        gameModel.calculatePlayersOrder();

        assertEquals("player 3", gameModel.getCurrentPlayer().getNickname());
    }

    @Test
    public void nextPlayerTurn_WithPlayerOrder123_ShouldSetCurrentPlayerAs2() {
        gameModel.nextPlayerTurn();
        assertEquals("player 2", gameModel.getCurrentPlayer().getNickname());
    }

    @Nested
    @DisplayName("conquerIsland method")
    class ConquerIslandMethod {

        final int islandID3 = 3;
        Island island3;

        @BeforeEach
        public void setUp() {
            try {
                island3 = gameModel.getGameTable().getIsland(islandID3);
            } catch (IslandNotFoundException e) {
                fail();
            }
        }

        @AfterEach
        public void tearDown() {
            island3 = null;
        }


        /**
         * A class encapsulating the test to check if the conquerIsland method has no effect
         */
        class DoNothingBehaviourTest {

            @Test
            public void shouldNotChangeTowersOfPlayers() {
                int towerNumber1 = player1.getTowerNumbers();
                int towerNumber2 = player2.getTowerNumbers();
                int towerNumber3 = player3.getTowerNumbers();
                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }
                assertEquals(towerNumber1, player1.getTowerNumbers());
                assertEquals(towerNumber2, player2.getTowerNumbers());
                assertEquals(towerNumber3, player3.getTowerNumbers());
            }

            @Test
            public void shouldNotChangeTowerOnIsland() {
                TowerType towerBefore = island3.getTower();
                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }
                TowerType towerAfter = island3.getTower();
                assertEquals(towerBefore, towerAfter);
            }

            /**
             * A method that sets the tower on an island near the one tested in order to see the different
             * behaviour of the method. This is executed only before test for the unification of the island,
             * as the towers on nearby islands are relevant only in that situation.
             */
            private void beforeEachUnifyTest(){
                try {
                    gameModel.getGameTable().getIsland(islandID3 - 1).setTower(TowerType.BLACK);
                } catch (IslandNotFoundException e) {
                    fail();
                }
            }

            @Test
            public void shouldNotRemoveIslands() {
                beforeEachUnifyTest();

                int numberOfIslands = gameModel.getGameTable().getNumberOfIslands();

                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }

                assertEquals(numberOfIslands, gameModel.getGameTable().getNumberOfIslands());
            }

            @Test
            public void shouldNotChangeIslandSize(){
                beforeEachUnifyTest();

                int prevSize = island3.getSize();

                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }

                assertEquals(prevSize, island3.getSize());
            }
        }

        /**
         * A class encapsulating the test to check if the conquerIsland method unify island correctly.
         * This test the behaviour if there are no island nearby with the same tower, if there is only one
         * or both.
         */
        abstract class UnifyIslandBehaviourTest{

            /**
             * The tower type to set on the island nearby the one tested. This should be the same as the one
             * expected to be found after the calling of the method conquerIsland in order to see the
             * correct unification behaviour
             */
            TowerType expectedTower;

            abstract void setExpectedTower();

            @BeforeEach
            public void beforeEach(){
                setExpectedTower();
            }

            @AfterEach
            public void afterEach(){
                expectedTower = null;
            }

            private void setPreviousIslandSameTower(){
                try {
                    gameModel.getGameTable().getIsland(islandID3-1).setTower(expectedTower);
                }catch (IslandNotFoundException e){
                    fail();
                }
            }

            private void setNextIslandSameTower(){
                try {
                    gameModel.getGameTable().getIsland(islandID3+1).setTower(expectedTower);
                }catch (IslandNotFoundException e){
                    fail();
                }
            }

            @Test
            public void withNoIslandsSameTower_ShouldNotChangeIslandNumber(){
                int numberOfIslands = gameModel.getGameTable().getNumberOfIslands();

                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }

                assertEquals(numberOfIslands, gameModel.getGameTable().getNumberOfIslands());
            }

            @Test
            public void withNoIslandsSameTower_ShouldNotChangeIslandSize(){

                int prevSize = island3.getSize();

                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }

                assertEquals(prevSize, island3.getSize());
            }

            @Test
            public void withPreviousIslandSameTower_ShouldRemoveOneIsland(){
                setPreviousIslandSameTower();

                int numberOfIslands = gameModel.getGameTable().getNumberOfIslands();

                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }

                assertEquals(numberOfIslands-1, gameModel.getGameTable().getNumberOfIslands());
            }

            @Test
            public void withPreviousIslandSameTower_ShouldRemoveIsland(){
                setPreviousIslandSameTower();

                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }

                assertThrows(IslandNotFoundException.class,
                        () -> gameModel.getGameTable().getIsland(islandID3-1));
            }

            @Test
            public void withPreviousIslandsSameTower_ShouldIncreaseIslandSize(){
                setPreviousIslandSameTower();

                int prevSize = island3.getSize();

                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }

                assertEquals(prevSize+1, island3.getSize());
            }

            @Test
            public void withBothIslandSameTower_ShouldRemoveTwoIsland(){
                setPreviousIslandSameTower();
                setNextIslandSameTower();

                int numberOfIslands = gameModel.getGameTable().getNumberOfIslands();

                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }

                assertEquals(numberOfIslands-2, gameModel.getGameTable().getNumberOfIslands());
            }

            @Test
            public void withBothIslandSameTower_ShouldRemoveIslands(){
                setPreviousIslandSameTower();
                setNextIslandSameTower();

                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }

                assertThrows(IslandNotFoundException.class,
                        () -> gameModel.getGameTable().getIsland(islandID3-1));
                assertThrows(IslandNotFoundException.class,
                        () -> gameModel.getGameTable().getIsland(islandID3+1));
            }
            @Test
            public void withBothIslandsSameTower_ShouldIncreaseIslandSize(){
                setPreviousIslandSameTower();
                setNextIslandSameTower();

                int prevSize = island3.getSize();

                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }

                assertEquals(prevSize+2, island3.getSize());
            }
        }


        @Nested
        @DisplayName("with no initial tower")
        class NoInitialTowerOnIsland {
            @Nested
            @DisplayName("and no students")
            class NoStudents extends DoNothingBehaviourTest {
            }

            @Nested
            @DisplayName("and no professors but with students")
            class YesStudentsNoProfessors extends DoNothingBehaviourTest {

                @BeforeEach
                public void setUp() {
                    try {
                        gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                        gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                        gameModel.getGameTable().addToIsland(PawnType.RED_DRAGONS, islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                }
            }

            @Nested
            @DisplayName("but with students and professors")
            class YesStudentsAndProfessors {

                @BeforeEach
                public void setUp() {
                    player1.addProfessor(PawnType.RED_DRAGONS);

                    player2.addProfessor(PawnType.BLUE_UNICORNS);
                    player2.addProfessor(PawnType.PINK_FAIRIES);

                    player3.addProfessor(PawnType.GREEN_FROGS);
                }

                @Nested
                @DisplayName("tie condition")
                class Tie extends DoNothingBehaviourTest {

                    @BeforeEach
                    public void setUp() {
                        try {
                            //player 1
                            gameModel.getGameTable().addToIsland(PawnType.RED_DRAGONS, islandID3);
                            //player 2
                            gameModel.getGameTable().addToIsland(PawnType.BLUE_UNICORNS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.PINK_FAIRIES, islandID3);
                            //player 3
                            gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                        } catch (IslandNotFoundException e) {
                            fail();
                        }
                    }
                }

                @Nested
                @DisplayName("Player 2 highest influence")
                class Player2Wins extends UnifyIslandBehaviourTest{

                    @BeforeEach
                    public void setUp() {
                        try {
                            //player 1
                            gameModel.getGameTable().addToIsland(PawnType.RED_DRAGONS, islandID3);
                            //player 2
                            gameModel.getGameTable().addToIsland(PawnType.BLUE_UNICORNS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.BLUE_UNICORNS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.PINK_FAIRIES, islandID3);
                            //player 3
                            gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                        } catch (IslandNotFoundException e) {
                            fail();
                        }
                    }

                    @Override
                    void setExpectedTower() {
                        expectedTower = player2.getTowerType();
                    }

                    @Test
                    public void shouldRemoveTowerFromPlayer2() {
                        int towerNumber = player1.getTowerNumbers();
                        try {
                            gameModel.conquerIsland(islandID3);
                        } catch (IslandNotFoundException e) {
                            fail();
                        }
                        assertEquals(towerNumber - island3.getSize(), player2.getTowerNumbers());
                    }

                    @Test
                    public void shouldNotRemoveTowersFromOtherPlayers() {
                        int towerNumber = player1.getTowerNumbers();
                        try {
                            gameModel.conquerIsland(islandID3);
                        } catch (IslandNotFoundException e) {
                            fail();
                        }
                        assertEquals(towerNumber, player1.getTowerNumbers());
                        assertEquals(towerNumber, player3.getTowerNumbers());
                    }

                    @Test
                    public void shouldSetTowerOnIsland() {
                        try {
                            gameModel.conquerIsland(islandID3);
                            assertEquals(player2.getTowerType(), island3.getTower());
                        } catch (IslandNotFoundException e) {
                            fail();
                        }
                    }
                }
            }
        }

        @Nested
        @DisplayName("with tower, students and professors")
        class YesTowerStudentsProfessors {
            @BeforeEach
            public void setUp() {
                //set the tower
                island3.setTower(player1.getTowerType());
                player1.changeTowerNumber(-island3.getSize());

                //set the professors
                player1.addProfessor(PawnType.RED_DRAGONS);

                player2.addProfessor(PawnType.BLUE_UNICORNS);
                player2.addProfessor(PawnType.PINK_FAIRIES);

                player3.addProfessor(PawnType.GREEN_FROGS);
            }

            @Nested
            @DisplayName("tie between controlling player and another")
            class TieBetweenPlayer1And2 extends DoNothingBehaviourTest {
                @BeforeEach
                public void setUp() {
                    try {
                        //player 1 (+1 from tower)
                        gameModel.getGameTable().addToIsland(PawnType.RED_DRAGONS, islandID3);
                        //player 2
                        gameModel.getGameTable().addToIsland(PawnType.BLUE_UNICORNS, islandID3);
                        gameModel.getGameTable().addToIsland(PawnType.PINK_FAIRIES, islandID3);
                        //player 3
                        gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                }
            }

            @Nested
            @DisplayName("tie between two non controlling players")
            class TieBetweenPlayer2And3 extends DoNothingBehaviourTest {
                @BeforeEach
                public void setUp() {
                    try {
                        //player 1 (+1 from tower)
                        //player 2
                        gameModel.getGameTable().addToIsland(PawnType.BLUE_UNICORNS, islandID3);
                        gameModel.getGameTable().addToIsland(PawnType.PINK_FAIRIES, islandID3);
                        //player 3
                        gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                        gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                }
            }

            @Nested
            @DisplayName("the player controlling island wins")
            class ControllingPlayerWin extends DoNothingBehaviourTest {

                @BeforeEach
                public void setUp() {
                    try {
                        //player 1 (+1 from tower)
                        gameModel.getGameTable().addToIsland(PawnType.RED_DRAGONS, islandID3);
                        gameModel.getGameTable().addToIsland(PawnType.RED_DRAGONS, islandID3);
                        //player 2
                        gameModel.getGameTable().addToIsland(PawnType.BLUE_UNICORNS, islandID3);
                        gameModel.getGameTable().addToIsland(PawnType.PINK_FAIRIES, islandID3);
                        //player 3
                        gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                }
            }

            @Nested
            @DisplayName("a player not controlling island wins")
            class NonControllingPlayer2Win extends UnifyIslandBehaviourTest{

                @BeforeEach
                public void setUp() {
                    try {
                        //player 1 (+1 from tower)
                        gameModel.getGameTable().addToIsland(PawnType.RED_DRAGONS, islandID3);
                        //player 2
                        gameModel.getGameTable().addToIsland(PawnType.BLUE_UNICORNS, islandID3);
                        gameModel.getGameTable().addToIsland(PawnType.PINK_FAIRIES, islandID3);
                        gameModel.getGameTable().addToIsland(PawnType.PINK_FAIRIES, islandID3);
                        //player 3
                        gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                }

                @Override
                void setExpectedTower() {
                    expectedTower = player2.getTowerType();
                }

                @Test
                public void shouldRemoveTowersFromPlayer2() {
                    int towerNumber = player2.getTowerNumbers();
                    try {
                        gameModel.conquerIsland(islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                    assertEquals(towerNumber - island3.getSize(), player2.getTowerNumbers());
                }

                @Test
                public void shouldAddTowersToPlayer1() {
                    int towerNumber = player1.getTowerNumbers();
                    try {
                        gameModel.conquerIsland(islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                    assertEquals(towerNumber + island3.getSize(), player1.getTowerNumbers());
                }

                @Test
                public void shouldNotChangeTowersOfPlayer3() {
                    int towerNumber = player3.getTowerNumbers();
                    try {
                        gameModel.conquerIsland(islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                    assertEquals(towerNumber, player3.getTowerNumbers());
                }

                @Test
                public void shouldChangeTowerOnIsland() {
                    try {
                        gameModel.conquerIsland(islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                    assertEquals(player2.getTowerType(), island3.getTower());
                }
            }
        }
    }
}