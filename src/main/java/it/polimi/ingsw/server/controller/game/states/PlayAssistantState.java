package it.polimi.ingsw.server.controller.game.states;

import it.polimi.ingsw.network.messages.responses.ErrorCode;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.controller.game.expert.CharacterCard;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.player.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * This state allows the current player to use the assistant card.
 */
public class PlayAssistantState implements GameState {

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
     * This is the number of players that have completed the planning phase
     * (i.e., the usage of the assistant card).
     */
    private int numOfPlayersHavePlayed;

    /**
     * This is the list of the assistant cards played during the round.
     */
    private Collection<Assistant> assistantsPlayed;

    /**
     * This is the number of players of the game.
     */
    private final int numOfPlayers;

    /**
     * The constructor of the class takes in input the game.
     * @param game the Game class
     */
    public PlayAssistantState(Game game) {
        this.game = game;
        this.gameModel=game.getModel();
        numOfPlayersHavePlayed = 0;
        numOfPlayers = gameModel.getPlayerList().size();
        assistantsPlayed = new ArrayList<>();
    }

    @Override
    public void useAssistant(Assistant assistant) throws NotValidArgumentException {

        // this variable hold the current player of the game
        Player currentPlayer=gameModel.getCurrentPlayer();

        // CHECK 1st CONDITION OF NotValidArgumentException
        // if the hand of the players does not contain the assistant he wants to use
        // trow NotValidArgumentException
        if(!currentPlayer.getHand().contains(assistant)){
            throw new NotValidArgumentException(ErrorCode.ASSISTANT_NOT_EXIST);
        }

        // CHECK 2nd CONDITION OF NotValidArgumentException
        // if the player does not use a card already used by another player in the same turn
        // or if he cannot do that since it has only cards that have been already used, it can
        // use the assistant otherwise a NotValidArgumentException will be thrown
        if((!assistantsPlayed.contains(assistant)) || assistantsPlayed.containsAll(currentPlayer.getHand())){
            currentPlayer.useAssistant(assistant);
            numOfPlayersHavePlayed ++;
            assistantsPlayed.add(assistant);
        }else{
            throw new NotValidArgumentException(ErrorCode.ASSISTANT_NOT_USABLE);
        }

        // if players have used the assistant card
        // let's pass to MoveStudentState
        // after computing the order of the play the subsequent action phase and
        // resetting the state such that it is ready for the next use
        if(numOfPlayersHavePlayed==numOfPlayers) {
            game.getModel().calculateActionPhaseOrder();
            resetState();
            game.setState(new MoveStudentState(game, 0));
        }else{
        // otherwise set the next current player and continue the planning phase
            gameModel.nextPlayerTurn();
        }
    }

    /**
     * This method will reset the state to the initial situation (i.e., just after its construction)
     * such that it is ready to be used if another state need to enter on it.
     */
    private void resetState(){
        // no one has used the assistant card
        numOfPlayersHavePlayed=0;
        // the list that collects all the assistant card that have been used in the current round should be empty
        assistantsPlayed = new ArrayList<>();
    }

    @Override
    public void useCharacterCard(CharacterCard characterCard) throws NotValidOperationException {
        throw new NotValidOperationException();
    }

    @Override
    public StateType getType() {
        return StateType.PLAY_ASSISTANT_STATE;
    }

    @Override
    public void skipTurn() {
        List<Assistant> currentPlayerHand = new ArrayList<>(gameModel.getCurrentPlayer().getHand());
        boolean cardUsed = false;
        while (!cardUsed){
            int randomCard = new Random().nextInt(currentPlayerHand.size());
            try {
                useAssistant(currentPlayerHand.get(randomCard));
                cardUsed = true;
            } catch (NotValidArgumentException ignored){}
        }
    }
}