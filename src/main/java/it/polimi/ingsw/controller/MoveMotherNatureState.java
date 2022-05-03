package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.gametable.GameTable;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;

public class MoveMotherNatureState implements State {

    private final Game game;
    private final GameModel model;
    private final GameTable gameTable;
    private int numberOfPlayers = 0;

    public MoveMotherNatureState(Game game){
        this.game = game;
        this.model = game.getModel();
        this.gameTable = model.getGameTable();
    }

    @Override
    public void moveMotherNature(int positions) throws NotValidArgumentException{
        if (positions == 0) throw new NotValidArgumentException();
        //Get mother nature movements limit
        int movementsLimit = model.getMNMovementLimit();
        if (positions > movementsLimit) throw new NotValidArgumentException();
        //Move mother nature
        gameTable.moveMotherNature(positions);
        //Try to conquer the island
        try {
            model.conquerIsland(gameTable.getMotherNaturePosition());
        } catch (IslandNotFoundException e) {
            throw new NotValidArgumentException();
        }
        changeState();
    }

    private void changeState(){
        if(game.getLastRoundFlag()){
            //If this is the last round
            if (numberOfPlayers == model.getPlayerList().size()){
                //If all players have played their turn go at the end of the game
                game.setState(game.getEndState());
            }
            else{
                //More players have to play their last turn
                numberOfPlayers++;
                model.nextPlayerTurn();
                game.setState(game.getMoveStudentState());
            }
        }
        else {
            //Otherwise, go to the next state
            game.setState(game.getChooseCloudState());
        }
    }

}
