package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.gametable.exceptions.EmptyBagException;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;
import it.polimi.ingsw.model.player.ReachedMaxStudentException;

public class UseCharacterCard11State implements State {

    /**
     * This is the Game class
     *
     * @see Game
     */
    private final Game game;

    /**
     * This is the model of the game
     *
     * @see GameModel
     */
    private final GameModel gameModel;

    /**
     * This is the state from which the character card has been used and
     * this is the state in which the game have to return after the usage of the card.
     */
    private final State originState;

    /**
     * The character card that uses this state
     */
    private final CharacterCard11 characterCard11;

    /**
     * The constructor of the class.
     * @param game the Game class
     * @param originState the state from which the character card has been used
     * @param characterCard11 the character card that uses this state
     */
    public UseCharacterCard11State(Game game, State originState, CharacterCard11 characterCard11) {
        this.game = game;
        this.originState = originState;
        this.gameModel = game.getModel();
        this.characterCard11 = characterCard11;
    }

    public void moveFromCardToDiningRoom(PawnType pawnType) throws NotValidArgumentException{

        // remove student from card
        try {
            characterCard11.removeStudentFromCard(pawnType);
        } catch (NotEnoughStudentException e) {
            throw new NotValidArgumentException("is not present a student of that color");
        }

        // add student removed from the card to the dining room
        try {
            gameModel.getCurrentPlayer().addStudentToDiningRoom(pawnType);
        } catch (ReachedMaxStudentException e) {
            characterCard11.addStudentToCard(pawnType);
            throw new NotValidArgumentException("the dining room is full");
        }

        // take a student from the bag and put on the card
        try {
            characterCard11.addStudentToCard(gameModel.getStudentFromBag());
        } catch (EmptyBagException e) {
            // Todo: how to manage?
            // simply do nothing as said in the documentation
            // e.printStackTrace();
        }
    }

    /**
     * This method allows to go back to the state at which the character card has been used.
     */
    private void returnBack(){
        // if everything is fine:
        characterCard11.effectEpilogue();
        // return to the origin state
        game.setState(originState);
    }

    /**
     * This method allows to put a student to the dining room.
     * @param color the {@code PawnType} of the student
     * @param originPosition the {@code Position} from where take the student
     * @throws NotValidArgumentException if the dining room is full or if the student is not present on the card
     * @throws NotValidOperationException if the location of the position is not the character card 11
     */
    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition) throws NotValidArgumentException, NotValidOperationException {

        if(!originPosition.isLocation(Location.CHARACTER_CARD_11)){
            throw new NotValidOperationException("take a student from the character card 11");
        }

        moveFromCardToDiningRoom(color);
        returnBack();
    }
}

