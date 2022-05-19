package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.PawnType;
import it.polimi.ingsw.server.model.player.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class EndStateTest {

    private Game game;
    private Player player1;
    private Player player2;
    private Player player3;

    @BeforeEach
    void setUp() {
        //Create first player
        Collection<PlayerLoginInfo> players = new ArrayList<>();
        PlayerLoginInfo playerLoginInfo1 = new PlayerLoginInfo("A");
        //Create second player
        PlayerLoginInfo playerLoginInfo2 = new PlayerLoginInfo("B");
        PlayerLoginInfo playerLoginInfo3 = new PlayerLoginInfo("");
        players.add(playerLoginInfo1);
        players.add(playerLoginInfo2);
        players.add(playerLoginInfo3);
        //Create Game
        game = new Game(players);
        //Get Player1
        player1 = game.getModel().getCurrentPlayer();
        //Get Player2
        game.getModel().nextPlayerTurn();
        player2 = game.getModel().getCurrentPlayer();
        //Get Player3
        game.getModel().nextPlayerTurn();
        player3 = game.getModel().getCurrentPlayer();
    }

    @AfterEach
    void tearDown() {
        game = null;
        player1 = null;
        player2 = null;
        player3 = null;
    }

    /**
     * Method to change the number of towers and professors of a player
     * @param player player to change towers and professors
     * @param numberOfTowers number of towers that should remain to the player
     * @param numberOfProfessors number of professors to change to the player
     */
    private void changeTowerAndProfessors(Player player, int numberOfTowers, int numberOfProfessors ){
        player.changeTowerNumber(numberOfTowers-player.getTowerNumbers());
        int i= 0;
        for (PawnType p: PawnType.values()){
            if(i==numberOfProfessors) return;
            player.addProfessor(p);
            i ++;
        }
    }

    @Test
    public void onePlayerWithLessTowersThanTheOthers_ShouldWinPlayer1(){
        changeTowerAndProfessors(player1, 2, 2);
        changeTowerAndProfessors(player2, 3, 3);
        changeTowerAndProfessors(player3, 3, 0);
        game.setState(new EndState(game));
        Collection<Player> winners = game.getWinner();
        //Player1 has fewer towers than everyone
        assertTrue(winners.contains(player1));
        //Not contains player2
        assertFalse(winners.contains(player2));
        //Not contains player3
        assertFalse(winners.contains(player3));
        //Only one player wins
        assertEquals(1, winners.size());
    }

    @Test
    public void twoPlayersWithSameTowers_OneOfThemWithMoreProfessors_ShouldWinPlayer2(){
        changeTowerAndProfessors(player1, 2, 2);
        changeTowerAndProfessors(player2, 2, 3);
        changeTowerAndProfessors(player3, 3, 0);
        game.setState(new EndState(game));
        Collection<Player> winners = game.getWinner();
        //Player2 as same towers as player1, but has more professors
        assertTrue(winners.contains(player2));
        //Not contains player1
        assertFalse(winners.contains(player1));
        //Not contains player3
        assertFalse(winners.contains(player3));
        //There should be one winner
        assertEquals(1, winners.size());
    }

    @Test
    public void twoPlayersWithSameTowers_AndWithSameProfessors_ShouldDrawPlayer1AndPlayer2(){
        changeTowerAndProfessors(player1, 2, 2);
        changeTowerAndProfessors(player2, 2, 2);
        changeTowerAndProfessors(player3, 3, 0);
        game.setState(new EndState(game));
        Collection<Player> winners = game.getWinner();
        //Both player1 and player2 should be in the winner list
        assertTrue(winners.contains(player1));
        assertTrue(winners.contains(player2));
        //Not contains player3
        assertFalse(winners.contains(player3));
        //There should be two winners
        assertEquals(2, winners.size());
    }

    @Test
    public void threePlayersWithSameTowers_AndWithSameProfessors_ShouldDrawPlayer1AndPlayer2AndPlayer3(){
        changeTowerAndProfessors(player1, 2, 1);
        changeTowerAndProfessors(player2, 2, 1);
        changeTowerAndProfessors(player3, 2, 1);
        game.setState(new EndState(game));
        Collection<Player> winners = game.getWinner();
        //Both player1, player2 and player3 should be in the winner list
        assertTrue(winners.contains(player1));
        assertTrue(winners.contains(player2));
        assertTrue(winners.contains(player3));
        //There should be three winners
        assertEquals(3, winners.size());
    }

    @Test
    public void threePlayersWithSameTowers_OnePlayerWithMoreProfessors_ShouldWinPlayerOne(){
        changeTowerAndProfessors(player1, 2, 0);
        changeTowerAndProfessors(player2, 2, 3);
        changeTowerAndProfessors(player3, 2, 0);
        game.setState(new EndState(game));
        Collection<Player> winners = game.getWinner();
        //Player2 has more professors
        assertTrue(winners.contains(player2));
        //Not contains player1
        assertFalse(winners.contains(player1));
        //Not contains player3
        assertFalse(winners.contains(player3));
        //There should be one winner
        assertEquals(1, winners.size());
    }


}