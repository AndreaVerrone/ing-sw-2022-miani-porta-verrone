package it.polimi.ingsw.server.controller.game.states;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayAssistantStateTest {

    PlayAssistantState playAssistantState;

    Game game;

    @BeforeEach
    void setUp() {

        Collection<PlayerLoginInfo> playerLoginInfo = new ArrayList<>(List.of(
                new PlayerLoginInfo("Player1"),
                new PlayerLoginInfo("Player2"),
                new PlayerLoginInfo("Player3")
        ));

        game = new Game(playerLoginInfo);

        playAssistantState = new PlayAssistantState(game);

    }

    @AfterEach
    void tearDown() {
        game=null;
        playAssistantState=null;
    }

    @Test public void useAssistant_CARD1_CardInDeckAndNotAlreadyUsed_ShouldUse(){

        // this is the current player before the calling of the method
        Player oldCurrentPlayer = game.getModel().getCurrentPlayer();

        // call the method
        try {
            playAssistantState.useAssistant(Assistant.CARD_1);
        } catch (NotValidArgumentException e) {
            fail();
        }

        // CHECKS
        // 1. the assistant used is the last assistant that has been used by the player
        // that was the current player before the calling of the method (i.e., oldCurrentPlayer)
        assertEquals(Assistant.CARD_1,oldCurrentPlayer.getLastAssistant());

        // 2. the other 2 players can now play an assistant.
        // Note that the assistant played by the second player must be different
        // from the one played by the first player and the one played by the third should differ
        // from both the assistants already played
        // - player2 plays CARD_2
        Player player2 = game.getModel().getCurrentPlayer();
        try {
            playAssistantState.useAssistant(Assistant.CARD_2);
        } catch (NotValidArgumentException e) {
            fail();
        }
        assertEquals(Assistant.CARD_2,player2.getLastAssistant());

        // - player3 plays CARD_3
        Player player3 = game.getModel().getCurrentPlayer();
        try {
            playAssistantState.useAssistant(Assistant.CARD_3);
        } catch (NotValidArgumentException e) {
            fail();
        }
        assertEquals(Assistant.CARD_3,player3.getLastAssistant());
    }

    @Test public void useAssistant_CARD1_CardInDeckAndAlreadyUsedButCanBeUsed_ShouldUse(){

        // Player1 plays CARD_1
        try {
            playAssistantState.useAssistant(Assistant.CARD_1);
        } catch (NotValidArgumentException e) {
            fail();
        }

        // remove all the assistants from the player2's deck except for the CARD_1
        Player player2 = game.getModel().getCurrentPlayer();

        for(int i = 1; i<Assistant.values().length ; i++){
            player2.useAssistant(Assistant.values()[i]);
        }



        // check that the player2 can use the CARD_1 even if CARD_1 has already been played
        // by player1 since it has only that card to play

        // player 2 plays CARD_1
        try {
            playAssistantState.useAssistant(Assistant.CARD_1);
        } catch (NotValidArgumentException e) {
            fail();
        }
        assertEquals(Assistant.CARD_1,player2.getLastAssistant());
    }

    @Test public void useAssistant_CARD1_CardNotInDeck_ShouldThrow(){

        // remove the CARD_1 from the player's deck before calling the method to test
        game.getModel().getCurrentPlayer().useAssistant(Assistant.CARD_1);

        // check that the NotValidArgumentException is thrown
        assertThrows(NotValidArgumentException.class, ()-> playAssistantState.useAssistant(Assistant.CARD_1));

    }

    @Test public void useAssistant_CARD1_CardInDeckAndAlreadyUsedAndCannotBeUsed_ShouldThrow(){

        // player1 uses the CARD_1
        try {
            playAssistantState.useAssistant(Assistant.CARD_1);
        } catch (NotValidArgumentException e) {
            fail();
        }

        // check that the Player2 (i.e., the next player cannot play it)
        // a NotValidArgumentException should be thrown
        assertThrows(NotValidArgumentException.class, ()->playAssistantState.useAssistant(Assistant.CARD_1));
    }

    @Test public void useAssistant_CARD1_CanBeUsed_ContinuePlanningPhase(){

        Player currentPlayer = game.getModel().getCurrentPlayer();

        // use the card
        try {
            playAssistantState.useAssistant(Assistant.CARD_1);
        } catch (NotValidArgumentException e) {
            fail();
        }


        // CHECKS
        // 1. the state after the calling of the method is PlayAssistantState
        assertEquals(game.getPlayAssistantState(),game.getState());
        // 2. the next player is correctly set
        assertNotEquals(currentPlayer,game.getModel().getCurrentPlayer());

    }

    @Test public void useAssistant_CARD3_CanBeUsed_GoToNextPhase(){

        // Player1 plays CARD_1
        try {
            playAssistantState.useAssistant(Assistant.CARD_1);
        } catch (NotValidArgumentException e) {
            fail();
        }

        // Player2 plays CARD_2
        try {
            playAssistantState.useAssistant(Assistant.CARD_2);
        } catch (NotValidArgumentException e) {
            fail();
        }

        // Player3 plays CARD_3
        try {
            playAssistantState.useAssistant(Assistant.CARD_3);
        } catch (NotValidArgumentException e) {
            fail();
        }

        //CHECKS
        // 1. the current player after the calling of the method should be Player1 since it is
        // the player that has used the card with the lowest value
        assertEquals("Player1",game.getModel().getCurrentPlayer().getNickname());
        // 2. the state now should be MoveStudentState that is the first of the action phase
        // since all the player of the game have completed the planning phase
        assertEquals(StateType.MOVE_STUDENT_STATE,game.getState().getType());
    }

    @Test
    public void skipTurn_ShouldUseARandomAssistant(){
        Player currentPlayer = game.getModel().getCurrentPlayer();
        assertNull(currentPlayer.getLastAssistant());
        playAssistantState.skipTurn();
        assertNotNull(currentPlayer.getLastAssistant());
    }

    @Test
    public void skipTurn_ShouldUseAnAssistantPresentInHand(){
        Player currentPlayer = game.getModel().getCurrentPlayer();
        Collection<Assistant> playerHand = currentPlayer.getHand();
        playAssistantState.skipTurn();
        assertTrue(playerHand.contains(currentPlayer.getLastAssistant()));
    }

    @Test
    public void skipTurn_ShouldNotUseAnAlreadyUsedAssistant(){
        try {
            playAssistantState.useAssistant(Assistant.CARD_1);
        } catch (NotValidArgumentException e){
            fail();
        }
        Player currentPlayer = game.getModel().getCurrentPlayer();
        playAssistantState.skipTurn();
        assertNotEquals(Assistant.CARD_1, currentPlayer.getLastAssistant());
    }
}