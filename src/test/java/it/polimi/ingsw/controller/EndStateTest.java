package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.CoinsBag;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.player.Player;
import org.junit.jupiter.api.*;

import java.text.CollationElementIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EndStateTest {

    private class ArrayStub extends ArrayList{

        private final int size;
        public ArrayStub(int size){
            this.size =size;
        }

        @Override
        public int size() {
            return size;
        }

    }
    private class PlayerStub extends Player{

        private final int numberOfTowers;
        private final int numberOfProfessors;

        public PlayerStub(int numberOfTowers, int numberOfProfessors) {
            super(new PlayerLoginInfo("notRelevant"), true, new CoinsBag());
            this.numberOfTowers = numberOfTowers;
            this.numberOfProfessors = numberOfProfessors;
        }

        @Override
        public int getTowerNumbers() {
            return numberOfTowers;
        }

        @Override
        public Collection<PawnType> getProfessors() {
            return new ArrayStub(numberOfProfessors);
        }
    }

    private class ModelStub extends GameModel{

        private final List<Player> players;

        public ModelStub(List<Player> players) {
            super(new ArrayList<>(List.of(new PlayerLoginInfo("A"), new PlayerLoginInfo("B"), new PlayerLoginInfo("C"))));
            this.players = players;
        }

        @Override
        public List<Player> getPlayerList() {
            return players;
        }
    }

    private class GameStub extends Game{
        private final GameModel model;

        public GameStub(List<Player> players) {
            super(new ArrayList<>(List.of(new PlayerLoginInfo("A"), new PlayerLoginInfo("B"), new PlayerLoginInfo("C"))));
            this.model = new ModelStub(players);
        }

        @Override
        protected GameModel getModel() {
            return model;
        }
    }

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void onePlayerWithLessTowersThanTheOthers_ShouldWinPlayer1(){
        PlayerStub player1 = new PlayerStub(2, 1);
        PlayerStub player2 = new PlayerStub(3, 3);
        PlayerStub player3 = new PlayerStub(3, 1);
        Game game = new GameStub(List.of(player1, player2, player3));
        new EndState(game);
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
        PlayerStub player1 = new PlayerStub(2, 2);
        PlayerStub player2 = new PlayerStub(2, 3);
        PlayerStub player3 = new PlayerStub(3, 0);
        Game game = new GameStub(List.of(player1, player2, player3));
        new EndState(game);
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
        PlayerStub player1 = new PlayerStub(2, 2);
        PlayerStub player2 = new PlayerStub(2, 2);
        PlayerStub player3 = new PlayerStub(3, 0);
        Game game = new GameStub(List.of(player1, player2, player3));
        new EndState(game);
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
        PlayerStub player1 = new PlayerStub(2, 1);
        PlayerStub player2 = new PlayerStub(2, 1);
        PlayerStub player3 = new PlayerStub(2, 1);
        Game game = new GameStub(List.of(player1, player2, player3));
        new EndState(game);
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
        PlayerStub player1 = new PlayerStub(2, 0);
        PlayerStub player2 = new PlayerStub(2, 3);
        PlayerStub player3 = new PlayerStub(2, 0);
        Game game = new GameStub(List.of(player1, player2, player3));
        new EndState(game);
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