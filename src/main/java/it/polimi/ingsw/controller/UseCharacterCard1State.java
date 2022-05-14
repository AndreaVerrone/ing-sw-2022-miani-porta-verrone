package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.gametable.exceptions.EmptyBagException;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;

public class UseCharacterCard1State extends UseCharacterCardState implements State {

    /**
     * This is the model of the game
     * @see GameModel
     */
    private final GameModel gameModel;

    /**
     * The character card that uses this state
     */
    private final CharacterCard1 characterCard1;

    /**
     * This is the student to move from the card to the island.
     */
    private PawnType studentToMove;

    /**
     * The constructor of the class.
     * @param game the Game class
     * @param originState the state from which the character card has been used
     * @param characterCard1 the character card that uses this state
     */
    public UseCharacterCard1State(Game game, State originState, CharacterCard1 characterCard1) {
        super(game,originState,characterCard1);
        this.gameModel=game.getModel();
        this.characterCard1=characterCard1;
    }

    /**
     * @param color the {@code PawnType} of the student
     * @param position the {@code Position} from where take the student
     * @throws NotValidOperationException if the location of the position is not the character card 1
     * @throws NotValidArgumentException if the student is not present on the character card 1
     */
    @Override
    public void choseStudentFromLocation(PawnType color, Position position) throws NotValidArgumentException, NotValidOperationException {

        // CHECKS
        // 1. check that the location is the one of character card 1
        if(!position.isLocation(Location.CHARACTER_CARD_1)){
            throw new NotValidOperationException("you have to take a student from the character card 1");
        }

        // 2. the student to move is present on the card
        if(characterCard1.getStudentList().getNumOf(color)<0) {
            throw new NotValidArgumentException("is not present a student of that color");
        }

        // ACTIONS
        // if everything is right set the student to move
        studentToMove=color;
    }

    /**
     * This method allows to put a student on the island specified in the parameter.
     * @param destination the Position
     * @throws NotValidArgumentException if the island does not exist
     * @throws NotValidOperationException if the location of the position is not an island or the student to move is null
     *                                    or there are no more student on the card
     *                                    (this is a rare situation, but it may happen)
     */
    @Override
    public void chooseDestination(Position destination) throws NotValidArgumentException, NotValidOperationException {

        // CHECKS
        // 1. check that the destination is an island
        if(!destination.isLocation(Location.ISLAND)){
            throw new NotValidOperationException("you have to chose an island");
        }

        // 2. check that the student to move has been set
        if(studentToMove==null){
            throw new NotValidOperationException("you have to chose a student");
        }

        // ACTIONS OF THE METHOD

        // 1. add the student removed from the card to the island if the island
        // exist otherwise throw an exception
        try {
            gameModel.getGameTable().addToIsland(studentToMove,destination.getField());
        } catch (IslandNotFoundException e) {
            throw new NotValidArgumentException("island does not exist");
        }

        // 2. remove student from card
        try {
            characterCard1.removeStudentFromCard(studentToMove);
        }catch (NotEnoughStudentException e) {
            // there is a check before, so it is impossible that this exception will be thrown
            e.printStackTrace();
        }

        // 3. take a student from the bag and put on the card
        try {
            characterCard1.addStudentToCard(gameModel.getStudentFromBag());
        } catch (EmptyBagException e) {
            // Todo: how to manage?
            // simply do nothing as said in in the documentation
            // e.printStackTrace();
        }

        // EPILOGUE
        finalizeCardUsed();
        returnBack();
    }
}
