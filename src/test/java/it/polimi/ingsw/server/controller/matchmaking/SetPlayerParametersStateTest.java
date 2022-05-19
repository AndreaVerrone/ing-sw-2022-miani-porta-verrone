package it.polimi.ingsw.server.controller.matchmaking;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.model.player.Wizard;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class SetPlayerParametersStateTest {

    MatchMaking matchMaking = null;
    SetPlayerParametersState state = null;

    @BeforeEach
    void setUp() {
        matchMaking = new MatchMaking(3, false);
        try {
            matchMaking.addPlayer("player 1");
            matchMaking.addPlayer("player 2");
            matchMaking.addPlayer("player 3");
        } catch (NotValidOperationException | NotValidArgumentException e) {
            fail();
        }
        matchMaking.chooseFirstPlayer();
        state = new SetPlayerParametersState(matchMaking, 1);
        matchMaking.setState(state);
    }

    @AfterEach
    void tearDown() {
        matchMaking = null;
        state = null;
    }

    @ParameterizedTest
    @ValueSource(ints = {-2, 0, 4, 7})
    public void constructor_WithInvalidPlayerNumber_ShouldThrow(int playerServing) {
        assertThrows(AssertionError.class, () -> new SetPlayerParametersState(matchMaking, playerServing));
    }

    @Nested
    @DisplayName("setTowerOfPlayer_WithTowerAvailable")
    class SetTowerAvailable{

        @Nested
        @DisplayName("and tower of player not set")
        class PlayerEmpty{

            @Test
            public void shouldSet(){
                try {
                    state.setTowerOfPlayer(TowerType.BLACK);
                } catch (NotValidArgumentException e) {
                    fail();
                }
                assertEquals(TowerType.BLACK, matchMaking.getCurrentPlayer().getTowerType());
            }

            @Test
            public void shouldRemoveFromAvailable(){
                try {
                    state.setTowerOfPlayer(TowerType.BLACK);
                } catch (NotValidArgumentException e) {
                    fail();
                }
                assertFalse(matchMaking.getTowersAvailable().contains(TowerType.BLACK));
            }
        }

        @Nested
        @DisplayName("and tower of player set")
        class PlayerFull{

            @BeforeEach
            public void setUp(){
                try {
                    state.setTowerOfPlayer(TowerType.WHITE);
                } catch (NotValidArgumentException e) {
                    fail();
                }
            }

            @Test
            public void shouldChange(){
                try {
                    state.setTowerOfPlayer(TowerType.BLACK);
                } catch (NotValidArgumentException e) {
                    fail();
                }
                assertEquals(TowerType.BLACK, matchMaking.getCurrentPlayer().getTowerType());
            }

            @Test
            public void shouldRemoveFromAvailable(){
                try {
                    state.setTowerOfPlayer(TowerType.BLACK);
                } catch (NotValidArgumentException e) {
                    fail();
                }
                assertFalse(matchMaking.getTowersAvailable().contains(TowerType.BLACK));
            }

            @Test
            public void shouldPutBackPrevTower(){
                var prevTower = matchMaking.getCurrentPlayer().getTowerType();
                try {
                    state.setTowerOfPlayer(TowerType.BLACK);
                } catch (NotValidArgumentException e) {
                    fail();
                }
                assertTrue(matchMaking.getTowersAvailable().contains(prevTower));
            }
        }
    }

    @Test
    public void setTowerOfPlayer_WithTowerNotAvailable_ShouldThrow() {
        try {
            state.setTowerOfPlayer(TowerType.BLACK);
        } catch (NotValidArgumentException e) {
            fail();
        }

        assertThrows(NotValidArgumentException.class, () -> state.setTowerOfPlayer(TowerType.BLACK));
    }

    @Nested
    @DisplayName("setWizardOfPlayer_WithWizardAvailable")
    class SetWizardAvailable{

        @Nested
        @DisplayName("and wizard of player not set")
        class PlayerEmpty{

            @Test
            public void shouldSet(){
                try {
                    state.setWizardOfPlayer(Wizard.W1);
                } catch (NotValidArgumentException e) {
                    fail();
                }
                assertEquals(Wizard.W1, matchMaking.getCurrentPlayer().getWizard());
            }

            @Test
            public void shouldRemoveFromAvailable(){
                try {
                    state.setWizardOfPlayer(Wizard.W1);
                } catch (NotValidArgumentException e) {
                    fail();
                }
                assertFalse(matchMaking.getWizardsAvailable().contains(Wizard.W1));
            }
        }

        @Nested
        @DisplayName("and wizard of player set")
        class PlayerFull{

            @BeforeEach
            public void setUp(){
                try {
                    state.setWizardOfPlayer(Wizard.W3);
                } catch (NotValidArgumentException e) {
                    fail();
                }
            }

            @Test
            public void shouldChange(){
                try {
                    state.setWizardOfPlayer(Wizard.W1);
                } catch (NotValidArgumentException e) {
                    fail();
                }
                assertEquals(Wizard.W1, matchMaking.getCurrentPlayer().getWizard());
            }

            @Test
            public void shouldRemoveFromAvailable(){
                try {
                    state.setWizardOfPlayer(Wizard.W1);
                } catch (NotValidArgumentException e) {
                    fail();
                }
                assertFalse(matchMaking.getWizardsAvailable().contains(Wizard.W1));
            }

            @Test
            public void shouldPutBackPrevWizard(){
                var prevWizard = matchMaking.getCurrentPlayer().getWizard();
                try {
                    state.setWizardOfPlayer(Wizard.W1);
                } catch (NotValidArgumentException e) {
                    fail();
                }
                assertTrue(matchMaking.getWizardsAvailable().contains(prevWizard));
            }
        }
    }

    @Test
    public void setWizardOfPlayer_WithWizardNotAvailable_ShouldThrow() {
        try {
            state.setWizardOfPlayer(Wizard.W1);
        } catch (NotValidArgumentException e) {
            fail();
        }

        assertThrows(NotValidArgumentException.class, () -> state.setWizardOfPlayer(Wizard.W1));
    }

    @Test
    public void next_WhenPlayerHasNotChooseTower_ShouldThrow(){
        assertThrows(NotValidOperationException.class, () -> state.next());
    }

    @Test
    public void next_WhenPlayerHasNotChooseWizard_ShouldThrow(){
        assertThrows(NotValidOperationException.class, () -> state.next());
    }

    @Test
    public void next_WhenPlayerHasFinished_ShouldChangeCurrentPlayer(){
        try {
            state.setWizardOfPlayer(Wizard.W2);
            state.setTowerOfPlayer(TowerType.BLACK);
        } catch (NotValidArgumentException e){
            fail();
        }

        var prevPlayer = matchMaking.getCurrentPlayer();
        try {
            state.next();
        } catch (NotValidOperationException e) {
            fail();
        }
        assertNotEquals(prevPlayer, matchMaking.getCurrentPlayer());
    }
}