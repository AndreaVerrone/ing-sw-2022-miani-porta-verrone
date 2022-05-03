package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.player.Player;

/**
 * A class to handle the end of the game
 */
public class EndState {

    /**
     * Model of the game
     */
    private final GameModel model;

    /**
     * Constructor of the class. Saves the {@code model} and calculates the winner
     * @param game Game class
     */
    public EndState(Game game){
        this.model = game.getModel();
        calculateWinner();
    }

    /**
     * Method to calculate the winner
     */
    private void calculateWinner(){

        Player winner = model.getPlayerList().stream().
                reduce(model.getCurrentPlayer(), ((player1, player2) -> {
                    if (player1.getTowerNumbers() < player2.getTowerNumbers()) {
                        return player1;
                    }
                    else{
                        if (player1.getTowerNumbers() == player2.getTowerNumbers()){
                            if (player1.getProfessors().size() >= player2.getProfessors().size()){
                                return player1;
                            }
                        }
                        return player2;
                    }
                }));
        //TODO: observer to notify winner
    }
}
