package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.model.gametable.Island;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.strategies.influence.ComputeInfluenceWithNoTowers;
import it.polimi.ingsw.server.model.strategies.influence.ComputeInfluenceWithTwoAdditional;
import it.polimi.ingsw.server.model.strategies.influence.ComputeInfluenceWithoutStudentColor;
import it.polimi.ingsw.server.model.strategies.mother_nature.MotherNatureLimitPlusTwo;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.TowerType;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {

    GameModel gameModel;

    Player player1;
    Player player2;
    Player player3;

    @BeforeEach
    void setUp() {

        PlayerLoginInfo playerLoginInfo1=new PlayerLoginInfo("player 1");
        playerLoginInfo1.setWizard(Wizard.W1);
        playerLoginInfo1.setTowerType(TowerType.BLACK);

        PlayerLoginInfo playerLoginInfo2 = new PlayerLoginInfo("player 2");
        playerLoginInfo2.setWizard(Wizard.W2);
        playerLoginInfo2.setTowerType(TowerType.WHITE);

        PlayerLoginInfo playerLoginInfo3 = new PlayerLoginInfo("player 3");
        playerLoginInfo3.setWizard(Wizard.W3);
        playerLoginInfo3.setTowerType(TowerType.GREY);

        gameModel = new GameModel(List.of(playerLoginInfo1,playerLoginInfo2,playerLoginInfo3));

        player1 = gameModel.getCurrentPlayer();
        gameModel.nextPlayerTurn();
        player2 = gameModel.getCurrentPlayer();
        gameModel.nextPlayerTurn();
        player3 = gameModel.getCurrentPlayer();
        gameModel.calculatePlanningPhaseOrder();
    }

    @AfterEach
    void tearDown() {
        player1 = null;
        player2 = null;
        player3 = null;
        gameModel = null;
    }

    @Test
    public void calculatePlanningPhaseOrder__InitialOrder213_FinalOrder231(){

        // initial order at the moment of the creation is: player1, player2, player3

        // use assistants and compute the order to play the action phase
        // order of the action phase is: player2, player1, player3
        player2.useAssistant(Assistant.CARD_1);
        player1.useAssistant(Assistant.CARD_2);
        player3.useAssistant(Assistant.CARD_3);
        gameModel.calculateActionPhaseOrder();

        // compute planning phase order
        // the order should be: player2, player3, player1
        // and assert that it is
        gameModel.calculatePlanningPhaseOrder();
        assertEquals(player2,gameModel.getCurrentPlayer());
        gameModel.nextPlayerTurn();
        assertEquals(player3,gameModel.getCurrentPlayer());
        gameModel.nextPlayerTurn();
        assertEquals(player1,gameModel.getCurrentPlayer());
    }

    @Test
    public void calculatePlanningPhaseOrder___InitialOrder321_FinalOrder312(){

        // initial order at the moment of the creation is: player1, player2, player3

        // use assistants and compute the order to play the action phase
        // order of the action phase is: player3, player2, player1
        player3.useAssistant(Assistant.CARD_1);
        player2.useAssistant(Assistant.CARD_2);
        player1.useAssistant(Assistant.CARD_3);
        gameModel.calculateActionPhaseOrder();

        // compute panning phase order
        // the order should be: player3, player1, player2
        // and assert that it is
        gameModel.calculatePlanningPhaseOrder();
        assertEquals(player3,gameModel.getCurrentPlayer());
        gameModel.nextPlayerTurn();
        assertEquals(player1,gameModel.getCurrentPlayer());
        gameModel.nextPlayerTurn();
        assertEquals(player2,gameModel.getCurrentPlayer());
    }

    @Test
    public void calculatePlanningPhaseOrder__UnalteredOrderByActionPhase_OrderShouldBeTheSame(){
        // initial order --> 1 2 3
        // compute panning phase order --> order 1 2 3
        gameModel.calculatePlanningPhaseOrder();
        assertEquals(player1,gameModel.getCurrentPlayer());
        gameModel.nextPlayerTurn();
        assertEquals(player2,gameModel.getCurrentPlayer());
        gameModel.nextPlayerTurn();
        assertEquals(player3,gameModel.getCurrentPlayer());
    }

    @Test
    public void calculatePlanningPhaseOrder_WithPlayer3UsingLowestAssistant_ShouldBeFirstPlayer() {
        player1.useAssistant(Assistant.CARD_9);
        player2.useAssistant(Assistant.CARD_5);
        player3.useAssistant(Assistant.CARD_1);

        gameModel.calculateActionPhaseOrder();

        assertEquals(player3, gameModel.getCurrentPlayer());
    }

    @Test
    public void nextPlayerTurn_WithPlayerOrder123_ShouldSetCurrentPlayerAs2() {
        gameModel.nextPlayerTurn();
        assertEquals(player2, gameModel.getCurrentPlayer());
    }

    @Test
    public void getMotherNatureMovementsLimit_StandardStrategy_ShouldReturnLimitOfCard(){
        //Use assistant
        gameModel.getCurrentPlayer().useAssistant(Assistant.CARD_9);
        //Use standard strategy
        assertEquals(Assistant.CARD_9.getRangeOfMotion(), gameModel.getMNMovementLimit());
    }

    @Test
    public void getMotherNatureMovementsLimit_PlusTwoMovementsStrategy_ShouldReturnLimitOfCardIncremented(){
        //Use assistant
        gameModel.getCurrentPlayer().useAssistant(Assistant.CARD_9);
        //Change strategy
        gameModel.setMotherNatureLimitStrategy(new MotherNatureLimitPlusTwo());
        assertEquals(Assistant.CARD_9.getRangeOfMotion()+2, gameModel.getMNMovementLimit());
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

        @Nested
        @DisplayName("with bans on island")
        class ConquerIslandWIthBan extends DoNothingBehaviourTest{

            @BeforeEach
            public void setUp(){
                island3.addBan();
                island3.addBan();
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

            @Test
            public void conquerIslandWithBans_ShouldRemoveBan(){
                try {
                    gameModel.conquerIsland(islandID3);
                } catch (IslandNotFoundException e) {
                    fail();
                }
                assertEquals(1, island3.getBan());
            }
        }
    }

    @Nested
    @DisplayName("calculate influence using strategies")
    class CalculateInfluence{

        private Island island;
        private final int islandID = 4;

        @BeforeEach
        public void setUp() {
            try {
                island = gameModel.getGameTable().getIsland(islandID);
            } catch (IslandNotFoundException e) {
                fail();
            }
            //set the tower
            island.setTower(player3.getTowerType());
            player3.changeTowerNumber(-island.getSize());

            //set the professors
            player1.addProfessor(PawnType.RED_DRAGONS);

            player2.addProfessor(PawnType.BLUE_UNICORNS);
            player2.addProfessor(PawnType.PINK_FAIRIES);

            player3.addProfessor(PawnType.GREEN_FROGS);
            //set the students
            try {
                //player 1
                gameModel.getGameTable().addToIsland(PawnType.RED_DRAGONS, islandID);
                //player 2
                gameModel.getGameTable().addToIsland(PawnType.BLUE_UNICORNS, islandID);
                gameModel.getGameTable().addToIsland(PawnType.PINK_FAIRIES, islandID);
                //player 3 (+1 from tower)
                gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID);
            } catch (IslandNotFoundException e) {
                fail();
            }
        }

        @Test
        public void calculateStandardInfluence_ShouldWinPlayer3(){
            //Use standard strategy
            try {
                gameModel.conquerIsland(islandID);
            } catch (IslandNotFoundException e) {
                fail();
            }
            assertEquals(player3.getTowerType(), island.getTower());
        }

        @Test
        public void calculateInfluenceWithNoTowers_ShouldWinPlayer2(){
            //Change strategy
            gameModel.setComputeInfluenceStrategy(new ComputeInfluenceWithNoTowers());
            try {
                gameModel.conquerIsland(islandID);
            } catch (IslandNotFoundException e) {
                fail();
            }
            assertEquals(player2.getTowerType(), island.getTower());
        }

        @Test
        public void calculateInfluenceWithoutStudentColor_ShouldWinPlayer2(){
            //Change strategy
            gameModel.setComputeInfluenceStrategy(new ComputeInfluenceWithoutStudentColor(PawnType.GREEN_FROGS));
            try {
                gameModel.conquerIsland(islandID);
            } catch (IslandNotFoundException e) {
                fail();
            }
            assertEquals(player2.getTowerType(), island.getTower());
        }

        @Test
        public void calculateInfluenceWithTwoAdditional_ShouldWinPlayer1(){
            //Change strategy
            gameModel.setComputeInfluenceStrategy(new ComputeInfluenceWithTwoAdditional(gameModel));
            try {
                gameModel.conquerIsland(islandID);
            } catch (IslandNotFoundException e) {
                fail();
            }
            assertEquals(player1.getTowerType(), island.getTower());
        }
    }
}