package it.polimi.ingsw.server.controller.game.states;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.gametable.GameTable;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;

/**
 * A class to handle the state of the game in which the player can move mother nature
 */
public class MoveMotherNatureState implements GameState {

    /**
     * Game class of the game
     */
    private final Game game;
    /**
     * Model of the game
     */
    private final GameModel model;
    /**
     * Game table of the game
     */
    private final GameTable gameTable;

    /**
     * Constructor of the class. Saves the game, the model and the game table
     * @param game Game class
     */
    public MoveMotherNatureState(Game game){
        this.game = game;
        this.model = game.getModel();
        this.gameTable = model.getGameTable();
    }

    @Override
    public void moveMotherNature(int positions) throws NotValidArgumentException {
        if (positions == 0) throw new NotValidArgumentException("Mother nature movements cannot be zero!");
        //Get mother nature movements limit
        int movementsLimit = model.getMNMovementLimit();
        if (positions > movementsLimit) throw new NotValidArgumentException("Mother nature movements over the limit!");
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

    /**
     * Method to handle the change of the state.<p>
     *     If it is the last round the next state will be either {@code MoveStudentState} or {@code EndState} if all
     *     players have played their turn.
     * </p>
     * <p>
     *     If it is not last round it goes to {@code ChooseCloudState}.
     * </p>
     */
    private void changeState(){
        if(game.getLastRoundFlag()){
            game.endOfTurn();
        }
        else {
            //Otherwise, go to the next state
            game.setState(game.getChooseCloudState());
        }
    }

    @Override
    public StateType getType() {
        return StateType.MOVE_MOTHER_NATURE_STATE;
    }
}
