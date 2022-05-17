package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;
import it.polimi.ingsw.model.player.ReachedMaxStudentException;

public class MoveStudentState implements State{

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

    public MoveStudentState(Game game) {
        this.game = game;
        this.gameModel = game.getModel();
        this.numberOfStudentsMoved=0;
        int numOfPlayers=game.getModel().getPlayerList().size();
        this.numOfStudentsToMove=(numOfPlayers==2||numOfPlayers==4)?3:4;
    }

    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition)throws NotValidOperationException, NotValidArgumentException{

        // 1. check that the student comes from the entrance
        if(!originPosition.isLocation(Location.ENTRANCE)){
            throw new NotValidOperationException("you have to take the student from the entrance");
        }
        // 2. check that the student of that color is present at the entrance
        if (gameModel.getCurrentPlayer().getStudentsInEntrance().getNumOf(color) < 1)
            throw new NotValidArgumentException("There are no students of that color at the entrance");
        studentToMove = color;
    }

    @Override
    public void chooseDestination(Position destination)throws NotValidOperationException,NotValidArgumentException{

        if (studentToMove == null)
            throw new NotValidOperationException("you have to chose the student before");
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
            throw new NotValidArgumentException("you are trying to remove from entrance a student " +
                    "that it is not present");
        }catch (ReachedMaxStudentException e){
            throw new NotValidArgumentException("you cannot add this student on the dining room " +
                    "since its table is full!");
        }
        gameModel.checkProfessor(studentToMove);
    }

    private void moveToIsland(int islandID) throws NotValidArgumentException {
        try {
            gameModel.getGameTable().addToIsland(studentToMove,islandID);
            gameModel.getCurrentPlayer().removeStudentFromEntrance(studentToMove);
        } catch (IslandNotFoundException e) {
            throw new NotValidArgumentException("this island does not exist");
        } catch (NotEnoughStudentException e) {
            e.printStackTrace();
        }
    }

    private void updateState(){
        studentToMove = null;
        numberOfStudentsMoved ++;

        if(numberOfStudentsMoved==numOfStudentsToMove){
            goToMoveMotherNatureState();
        }
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
}

