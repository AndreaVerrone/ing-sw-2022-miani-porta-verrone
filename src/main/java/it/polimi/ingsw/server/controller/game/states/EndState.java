package it.polimi.ingsw.server.controller.game.states;

import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.player.Player;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * A class to handle the end of the game
 */
public class EndState implements GameState {

    /**
     * Game class of the game
     */
    private final Game game;
    /**
     * Model of the game
     */
    private final GameModel model;

    /**
     * Constructor of the class. Saves the {@code model} and calculates the winner
     * @param game Game class
     */
    public EndState(Game game){
        this.game = game;
        this.model = game.getModel();
        calculateWinner();
    }

    /**
     * Method to calculate the winner.
     * <p>
     *     Wins the player with fewer towers, in case of a draw, wins the player with more professors. If these are equals too,
     *     both players are considered winners
     *</p>
     */
    private void calculateWinner(){

        Player firstWinner = model.getPlayerList().stream().
                reduce(model.getCurrentPlayer(), ((player1, player2) -> {
                    if (player1.getTowerNumbers() < player2.getTowerNumbers()) {
                        return player1;
                    }
                    else{
                        if (player1.getTowerNumbers() == player2.getTowerNumbers()){
                            if (player1.getProfessors().size() > player2.getProfessors().size()){
                                return player1;
                            }
                            else{
                                if (player1.getProfessors().size() == player2.getProfessors().size()){
                                    return player1;
                                }
                            }
                        }
                        return player2;
                    }
                }));
        //Number of towers of the winner
        int winnerNumberOfTowers = firstWinner.getTowerNumbers();
        //Number of professors of the winner
        int winnerNumberOfProfessors = firstWinner.getProfessors().size();
        //List of winners, there is more than one in case of draw
        Collection<Player> winners;
        winners = model.getPlayerList().stream()
                        //If a player has the same number of towers and professors is considered a draw
                        .filter(player -> (player.getTowerNumbers() == winnerNumberOfTowers && player.getProfessors().size() == winnerNumberOfProfessors))
                        .collect(Collectors.toList());
        game.setWinner(winners);
    }
}
