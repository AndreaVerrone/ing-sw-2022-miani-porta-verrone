package it.polimi.ingsw.model;

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
    CoinsBag coinsBag;
    Player player1;
    Player player2;
    Player player3;

    @BeforeEach
    void setUp() {
        boolean threePlayers = true;
        coinsBag = new CoinsBag();
        player1 = new Player("player 1", threePlayers, coinsBag);
        player1.setAssistantDeck(Wizard.W1);
        player1.setTowerType(TowerType.BLACK);
        player2 = new Player("player 2", threePlayers, coinsBag);
        player2.setAssistantDeck(Wizard.W2);
        player2.setTowerType(TowerType.WHITE);
        player3 = new Player("player 3", threePlayers, coinsBag);
        player3.setAssistantDeck(Wizard.W3);
        player3.setTowerType(TowerType.GREY);
        gameModel = new GameModel(List.of(player1, player2, player3));
    }

    @AfterEach
    void tearDown() {
        coinsBag = null;
        player1 = null;
        player2 = null;
        player3 = null;
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

        @Nested
        @DisplayName("with no initial tower")
        class NoInitialTowerOnIsland {
            @Nested
            @DisplayName("and no students")
            class NoStudents {

                @Test
                public void shouldNotRemoveTowersFromPlayers() {
                    int towerNumber = player1.getTowerNumbers();
                    try {
                        gameModel.conquerIsland(islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                    assertEquals(towerNumber, player1.getTowerNumbers());
                    assertEquals(towerNumber, player2.getTowerNumbers());
                    assertEquals(towerNumber, player3.getTowerNumbers());
                }

                @Test
                public void shouldNotSetTowerOnIsland() {
                    try {
                        gameModel.conquerIsland(islandID3);
                        TowerType towerAfter = island3.getTower();
                        assertNull(towerAfter);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                }
                
            }

            @Nested
            @DisplayName("and no professors but with students")
            class YesStudentsNoProfessors {

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

                @Test
                public void shouldNotRemoveTowersFromPlayers() {
                    int towerNumber = player1.getTowerNumbers();
                    try {
                        gameModel.conquerIsland(islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                    assertEquals(towerNumber, player1.getTowerNumbers());
                    assertEquals(towerNumber, player2.getTowerNumbers());
                    assertEquals(towerNumber, player3.getTowerNumbers());
                }

                @Test
                public void shouldNotSetTowerOnIsland() {
                    try {
                        gameModel.conquerIsland(islandID3);
                        TowerType towerAfter = island3.getTower();
                        assertNull(towerAfter);
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
                class Tie {

                    @BeforeEach
                    public void setUp() {
                        try {
                            gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.RED_DRAGONS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.BLUE_UNICORNS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.PINK_FAIRIES, islandID3);
                        } catch (IslandNotFoundException e) {
                            fail();
                        }
                    }

                    @Test
                    public void shouldNotRemoveTowersFromPlayers() {
                        int towerNumber = player1.getTowerNumbers();
                        try {
                            gameModel.conquerIsland(islandID3);
                        } catch (IslandNotFoundException e) {
                            fail();
                        }
                        assertEquals(towerNumber, player1.getTowerNumbers());
                        assertEquals(towerNumber, player2.getTowerNumbers());
                        assertEquals(towerNumber, player3.getTowerNumbers());
                    }

                    @Test
                    public void shouldNotSetTowerOnIsland() {
                        try {
                            gameModel.conquerIsland(islandID3);
                            TowerType towerAfter = island3.getTower();
                            assertNull(towerAfter);
                        } catch (IslandNotFoundException e) {
                            fail();
                        }
                    }
                }

                @Nested
                @DisplayName("Player 2 highest influence")
                class Player2Wins {

                    @BeforeEach
                    public void setUp() {
                        try {
                            gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.GREEN_FROGS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.RED_DRAGONS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.BLUE_UNICORNS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.BLUE_UNICORNS, islandID3);
                            gameModel.getGameTable().addToIsland(PawnType.PINK_FAIRIES, islandID3);
                        } catch (IslandNotFoundException e) {
                            fail();
                        }
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
                island3.setTower(player1.getTowerType());
                player1.changeTowerNumber(-island3.getSize());

                player1.addProfessor(PawnType.RED_DRAGONS);

                player2.addProfessor(PawnType.BLUE_UNICORNS);
                player2.addProfessor(PawnType.PINK_FAIRIES);

                player3.addProfessor(PawnType.GREEN_FROGS);
            }

            @Nested
            @DisplayName("tie between controlling player and another")
            class TieBetweenPlayer1And2 {
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

                @Test
                public void shouldNotRemoveTowersFromPlayers() {
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
                public void shouldNotSetTowerOnIsland() {
                    TowerType towerBefore = island3.getTower();
                    try {
                        gameModel.conquerIsland(islandID3);
                    } catch (IslandNotFoundException e) {
                        fail();
                    }
                    TowerType towerAfter = island3.getTower();
                    assertEquals(towerBefore, towerAfter);
                }
            }

            @Nested
            @DisplayName("tie between two non controlling players")
            class TieBetweenPlayer2And3 {
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

                @Test
                public void shouldNotRemoveTowersFromPlayers() {
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
            }

            @Nested
            @DisplayName("the player controlling island wins")
            class ControllingPlayerWin {

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

                @Test
                public void shouldNotRemoveTowersFromPlayers() {
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

            }

            @Nested
            @DisplayName("a player not controlling island wins")
            class NonControllingPlayer2Win {

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