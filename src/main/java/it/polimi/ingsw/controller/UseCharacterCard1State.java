package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.NotEnoughCoinsException;
import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.gametable.exceptions.EmptyBagException;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;

public class UseCharacterCard1State implements State {
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
     * This is the state from which the character card has been used and
     * this is the state in which the game have to return after the usage of the card.
     */
    private final State originState;

    private final CharacterCard1 characterCard1;

    public UseCharacterCard1State(Game game, State originState, CharacterCard1 characterCard1) {
        this.game = game;
        this.originState = originState;
        this.gameModel=game.getModel();
        this.characterCard1=characterCard1;
    }

    @Override
    public void moveFromCardToIsland(PawnType pawnType, int islandID) throws NotValidArgumentException, NotValidOperationException {

        // remove student from card
        try {
            characterCard1.removeStudentFromCard(pawnType);
        } catch (NotEnoughStudentException e) {
            throw new NotValidArgumentException("is not present a student of that color");
        }

        // add student removed from the card to the island
        try {
            gameModel.getGameTable().addToIsland(pawnType,islandID);
        } catch (IslandNotFoundException e) {
            characterCard1.addStudentToCard(pawnType);
            throw new NotValidArgumentException("island does not exist");
        }

        // take a student from the bag and put on the card
        try {
            characterCard1.addStudentToCard(gameModel.getStudentFromBag());
        } catch (EmptyBagException e) {
            // Todo: how to manage?
            // simply do nothing as said in in the documentation
            // e.printStackTrace();
        }

        // if everything is fine:
        characterCard1.effectEpilogue();

        // return to the origin state
        game.setState(originState);
    }
}
