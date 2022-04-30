package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.gametable.GameTable;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;
import it.polimi.ingsw.model.player.Assistant;

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
        //Set the new state of the game
        game.setState(game.getChooseCloudState());
    }

}
