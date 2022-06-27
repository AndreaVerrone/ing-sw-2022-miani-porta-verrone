package it.polimi.ingsw.server.controller.game.states;

import it.polimi.ingsw.network.messages.responses.ErrorCode;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;

public class MoveStudentState implements GameState {

    /**
     * This is the Game class
     * @see Game
     */
    private final Game game;

    /**
     * This is the model of the game
     * @see GameModel
     */
    private final GameModel gameModel;

    /**
     * This is the number of students that that player has moved.
     */
    private int numberOfStudentsMoved;

    /**
     * This is the number of students that the player has to move
     */
    private final int numOfStudentsToMove;

    /**
     * The student that has to be moved from entrance to destination
     */
    private PawnType studentToMove;

    public MoveStudentState(Game game, int numberOfStudentsMoved) {
        this.game = game;
        this.gameModel = game.getModel();
        this.numberOfStudentsMoved = numberOfStudentsMoved;
        int numOfPlayers=game.getModel().getPlayerList().size();
        this.numOfStudentsToMove = numOfPlayers==2 ? 3 : 4;
    }

    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition)throws NotValidArgumentException {

        // 1. check that the student comes from the entrance
        if(!originPosition.isLocation(Location.ENTRANCE)){
            throw new NotValidArgumentException();
        }
        // 2. check that the student of that color is present at the entrance
        if (gameModel.getCurrentPlayer().getStudentsInEntrance().getNumOf(color) < 1)
            throw new NotValidArgumentException(ErrorCode.STUDENT_NOT_PRESENT);
        studentToMove = color;
    }

    @Override
    public void chooseDestination(Position destination)throws NotValidArgumentException{

        if (studentToMove == null)
            return;
        if (destination.isLocation(Location.DINING_ROOM)) {
            moveToDiningRoom();
            updateState();
            return;
        }
        if (destination.isLocation(Location.ISLAND)) {
            moveToIsland(destination.getField());
            updateState();
            return;
        }
        throw new NotValidArgumentException();
    }

    private void moveToDiningRoom() throws NotValidArgumentException {
        try {
            gameModel.getCurrentPlayer().moveFromEntranceToDiningRoom(studentToMove);
        }catch (NotEnoughStudentException e){
            throw new NotValidArgumentException(ErrorCode.STUDENT_NOT_PRESENT);
        }catch (ReachedMaxStudentException e){
            throw new NotValidArgumentException(ErrorCode.DININGROOM_FULL);
        }
        gameModel.checkProfessor(studentToMove);
    }

    private void moveToIsland(int islandID) throws NotValidArgumentException {
        try {
            gameModel.getGameTable().addToIsland(studentToMove,islandID);
            gameModel.getCurrentPlayer().removeStudentFromEntrance(studentToMove);
        } catch (IslandNotFoundException e) {
            throw new NotValidArgumentException(ErrorCode.ISLAND_NOT_EXIST);
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();
        }
    }

    private void updateState(){
        numberOfStudentsMoved ++;

        if(numberOfStudentsMoved==numOfStudentsToMove){
            goToMoveMotherNatureState();
            return;
        }
        game.setState(new MoveStudentState(game, numberOfStudentsMoved));
    }

    /**
     * this method allow to pass to the next state(i.e., MoveMotherNatureState)
     */
    private void goToMoveMotherNatureState(){
        resetState();
        game.setState(game.getMoveMotherNatureState());
    }

    /**
     * This method will reset the state to the initial situation (i.e., just after its construction)
     * such that it is ready to be used if another state need to enter on it.
     */
    private void resetState(){
        numberOfStudentsMoved = 0;
    }

    @Override
    public StateType getType() {
        return StateType.MOVE_STUDENT_STATE;
    }

    @Override
    public void skipTurn() {
        if (numberOfStudentsMoved == 0){
            game.endOfTurn();
            return;
        }
        resetState();
        game.setState(game.getChooseCloudState());
        game.getState().skipTurn();
    }
}

