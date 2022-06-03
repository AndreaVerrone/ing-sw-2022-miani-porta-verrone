package it.polimi.ingsw.server.controller.game.states;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.CloudNotFoundException;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A class to handle the state of the game in which the player can choose a cloud and take all the students from it
 */
public class ChooseCloudState implements GameState {

    private final Game game;
    /**
     * Model of the game
     */
    private final GameModel model;
    /**
     * Number of players that have chosen already a cloud
     */
    private int numberOfPlayers;

    /**
     * Constructor of the class. Saves the game and the model of the game and sets the number of players that have
     * already played to zero
     * @param game class used to change the current state
     */
    public ChooseCloudState(Game game){
        this.game = game;
        this.model = game.getModel();
        numberOfPlayers = 0;
    }

    @Override
    public void takeFromCloud(int cloudID) throws NotValidOperationException, NotValidArgumentException {
        //Get current player
        Player player = model.getCurrentPlayer();
        try {
            //Get the students from the chosen cloud and fill with it the entrance of the current player
            StudentList students = model.getGameTable().getFromCloud(cloudID);
            if (students.numAllStudents()==0) throw new NotValidArgumentException("The cloud is empty!");
            for(PawnType p : PawnType.values()){
                for (int i = students.getNumOf(p); i>0; i--){
                    player.addStudentToEntrance(p);
                }
            }
        } catch (CloudNotFoundException e) {
            throw new NotValidArgumentException("The cloud doesn't exist!");
        } catch (ReachedMaxStudentException e) {
            throw new NotValidOperationException();
        }
        numberOfPlayers++;
        changeState();
    }

    /**
     * Method to handle the change of the state.<p>
     *     If all players have played it's the end of the round and the game returns to the state where players can use an assistant card
     * </p>
     * <p>
     *     If there are still players that haven't played yet the game returns to the state where the next player can
     *     move a student
     * </p>
     */
    private void changeState(){

        if (numberOfPlayers == model.getPlayerList().size()){
            //End of the round
            //Reset number of players that have played
            numberOfPlayers = 0;
            //Fill the clouds
            model.getGameTable().fillClouds();
            //Calculate new players order
            model.calculatePlanningPhaseOrder();
            game.setState(game.getPlayAssistantState());
        }
        else{
            //End of the current player turn
            model.nextPlayerTurn();

            //Change state
            game.setState(game.getMoveStudentState());
        }
        game.endOfTurn();
    }

    @Override
    public StateType getType() {
        return StateType.CHOOSE_CLOUD_STATE;
    }

    @Override
    public void skipTurn() {
        StudentList students = null;
        while (students == null){
            int randomCloud = new Random().nextInt(model.getPlayerList().size());
            try {
                students = model.getGameTable().getFromCloud(randomCloud);
                if (students.numAllStudents() == 0)
                    students = null;
            } catch (CloudNotFoundException e){}
        }

        List<PawnType> pawnTypeList = new ArrayList<>(List.of(PawnType.values()));
        while (students.numAllStudents() != 0){
            int randomStudentIndex = new Random().nextInt(pawnTypeList.size());
            PawnType randomStudent = pawnTypeList.get(randomStudentIndex);
            if (students.getNumOf(randomStudent) == 0) {
                pawnTypeList.remove(randomStudentIndex);
                continue;
            }
            try {
                model.getCurrentPlayer().addStudentToEntrance(randomStudent);
                students.changeNumOf(randomStudent, -1);
            } catch (ReachedMaxStudentException e) {
                break;
            } catch (NotEnoughStudentException e) {}
        }
        model.getGameTable().fillBag(students);
        changeState();
    }
}
