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
    int numberOfStudentsMoved;

    /**
     * This is the number od students that the player has to move
     */
    int numOfStudentsToMove;


    public MoveStudentState(Game game) {
        this.game = game;
        this.gameModel = game.getModel();
        this.numberOfStudentsMoved=0;
        int numOfPlayers=game.getModel().getPlayerList().size();
        this.numOfStudentsToMove=(numOfPlayers==2||numOfPlayers==4)?3:4;
    }

    @Override
    public void moveStudentToIsland(PawnType student, int islandID) throws NotValidOperationException, NotValidArgumentException {
        // remove student from entrance
        try {
            gameModel.getCurrentPlayer().removeStudentFromEntrance(student);
        }catch (NotEnoughStudentException e){
            throw new NotValidArgumentException("this student is not present at entrance");
        }

        // add student to island
        try {
            gameModel.getGameTable().addToIsland(student,islandID);
        } catch (IslandNotFoundException e) {
            try {
                gameModel.getCurrentPlayer().addStudentToEntrance(student);
            } catch (ReachedMaxStudentException ex) {
                ex.printStackTrace();
            }
            throw new NotValidArgumentException("this island does not exist");
        }

        numberOfStudentsMoved ++;

        if(numberOfStudentsMoved==numOfStudentsToMove){
            goToMoveMotherNatureState();
        }
    }

    @Override
    public void moveStudentToDiningRoom(PawnType student) throws NotValidOperationException, NotValidArgumentException {

        try {
            gameModel.getCurrentPlayer().moveFromEntranceToDiningRoom(student);
        }catch (NotEnoughStudentException e){
            throw new NotValidArgumentException("you are trying to remove from entrance a student " +
                    "that it is not present");
        }catch (ReachedMaxStudentException e){
            throw new NotValidArgumentException("you cannot add this student on the dining room " +
                    "since its table is full!");
        }

        numberOfStudentsMoved ++;
        gameModel.checkProfessor(student);

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

