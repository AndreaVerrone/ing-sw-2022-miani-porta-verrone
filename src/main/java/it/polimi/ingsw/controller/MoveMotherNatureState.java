package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.gametable.GameTable;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;

public class MoveMotherNatureState implements State {

    private final Game game;
    private final GameModel model;
    private final GameTable gameTable;

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
        //TODO: change condition for 4 players
        if(gameTable.getNumberOfIslands() <= 3 || model.getCurrentPlayer().getTowerNumbers() == 0){
            //If there are less than four islands or the current player as no tower the game instantly ends.
            game.setState(game.getEndState());
        }
        else {
            //Otherwise, go to the next state
            game.setState(game.getChooseCloudState());
        }
    }

}
