package it.polimi.ingsw.controller.matchmaking;

import it.polimi.ingsw.controller.NotValidArgumentException;
import it.polimi.ingsw.controller.NotValidOperationException;
import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class ChangePlayersStateTest {

    private MatchMaking matchMaking = null;
    private ChangePlayersState state = null;

    @BeforeEach
    void setUp() {
        matchMaking = new MatchMaking(3, false);
        state = new ChangePlayersState(matchMaking);
    }

    @AfterEach
    void tearDown() {
        matchMaking = null;
        state = null;
    }

    @Test
    public void addPlayer_WithNewNickname_ShouldAddPlayer(){
        String nickname = "nickname";
        try {
            state.addPlayer(nickname);
        } catch (NotValidArgumentException | NotValidOperationException e) {
            fail();
        }
        boolean isPresent = matchMaking.getPlayers().stream().anyMatch(p -> p.getNickname().equals(nickname));
        assertTrue(isPresent);
    }

    @Test
    public void addPlayer_WithNicknameAlreadyTaken_ShouldThrow(){
        String nickname = "nickname";
        try {
            state.addPlayer(nickname);
        } catch (NotValidArgumentException | NotValidOperationException e){
            fail();
        }

        assertThrows(NotValidArgumentException.class, () -> state.addPlayer(nickname));
    }

    @Test
    public void addPlayer_WithLobbyFull_ShouldThrow(){
        // filling with players
        String baseNickname = "nickname";
        for (int i = 0; i < matchMaking.getNumPlayers(); i++){
            try{
                state.addPlayer(baseNickname+i);
            } catch (NotValidArgumentException | NotValidOperationException e){
                fail();
            }
        }

        assertThrows(NotValidOperationException.class, () -> state.addPlayer(baseNickname));
    }

    @Test
    public void removePlayer_WithValidNickname_ShouldRemove(){
        String nickname = "nickname";
        try {
            state.addPlayer(nickname);

            state.removePlayer(nickname);
        } catch (NotValidArgumentException | NotValidOperationException e){
            fail();
        }
        boolean isPresent = matchMaking.getPlayers().stream().anyMatch(p -> p.getNickname().equals(nickname));
        assertFalse(isPresent);
    }

    @Test
    public void removePlayer_WithNotValidNickname_ShouldThrow(){
        assertThrows(NotValidArgumentException.class, () -> state.removePlayer(""));
    }

    @ParameterizedTest(name = "with value equals {0}")
    @ValueSource(ints = {2, 3, 4})
    public void changeNumOfPlayers_WithValidArgument_ShouldChange(int value){
        try {
            state.changeNumOfPlayers(value);
        } catch (NotValidArgumentException e) {
            fail();
        }
        assertEquals(value, matchMaking.getNumPlayers());
    }

    @ParameterizedTest(name = "with value equals {0}")
    @ValueSource(ints = {-1, 1, 5})
    public void changeNumOfPlayers_WithInvalidArgument_ShouldThrow(int value){
        assertThrows(NotValidArgumentException.class, () -> state.changeNumOfPlayers(value));
    }

    @Test
    public void changeNumOfPlayers_WithValueLessThanCurrentPlayers_ShouldThrow(){
        // filling with players
        String baseNickname = "nickname";
        for (int i = 0; i < matchMaking.getNumPlayers(); i++){
            try{
                state.addPlayer(baseNickname+i);
            } catch (NotValidArgumentException | NotValidOperationException e){
                fail();
            }
        }

        assertThrows(NotValidArgumentException.class, () -> state.changeNumOfPlayers(2));
    }

    @Test
    public void next_WithNotEnoughPlayers_ShouldThrow(){
        assertThrows(NotValidOperationException.class, () -> state.next());
    }

    @ParameterizedTest
    @EnumSource
    public void setTowerOfPlayer_ShouldThrow(TowerType tower){
        assertThrows(NotValidOperationException.class, () -> state.setTowerOfPlayer(tower));
    }

    @ParameterizedTest
    @EnumSource
    public void setWizardOfPlayer_ShouldThrow(Wizard wizard){
        assertThrows(NotValidOperationException.class, () -> state.setWizardOfPlayer(wizard));
    }

}