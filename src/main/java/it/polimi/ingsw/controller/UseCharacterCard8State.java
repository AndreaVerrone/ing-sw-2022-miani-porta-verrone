package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ComputeInfluenceWithoutStudentColor;
import it.polimi.ingsw.model.GameModel;
import it.polimi.ingsw.model.PawnType;

/**
 * Class to implement the state where the current player can use the card 8
 */
public class UseCharacterCard8State extends UseCharacterCardState implements State{

    /**
     * Model of the game
     */
    private final GameModel model;


    /**
     * Constructor of the class. Saves the game, the state before this one and the card used
     * @param game game class of the game
     * @param card card used in the state
     */
    public UseCharacterCard8State(Game game, State originState, CharacterCard8 card){
        super(game, originState, card);
        this.model = game.getModel();
    }

    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition) throws NotValidOperationException{
        if(originPosition.isLocation(Location.NONE)) {
            //Change strategy
            model.setComputeInfluenceStrategy(new ComputeInfluenceWithoutStudentColor(color));

            // EPILOGUE
            finalizeCardUsed();
            returnBack();
        }
        else{
            throw new NotValidOperationException("You can't get from a location now!");
        }
    }
}
