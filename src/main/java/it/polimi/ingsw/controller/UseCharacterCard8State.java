package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ComputeInfluenceWithoutStudentColor;
import it.polimi.ingsw.model.PawnType;

/**
 * Class to implement the state where the current player can use the card 8
 */
public class UseCharacterCard8State implements State{

    /**
     * Game class of the game
     */
    private final Game game;

    /**
     * State of the game before going to the state to use a card
     */
    private final State originState;

    /**
     * Card to use in this state
     */
    private final CharacterCard card;

    /**
     * Constructor of the class. Saves the game, the state before this one and the card used
     * @param game game class of the game
     * @param originState the state from which the character card has been used
     * @param card card used in the state
     */
    public UseCharacterCard8State(Game game, State originState, CharacterCard card){
        this.game = game;
        this.originState = originState;
        this. card = card;
    }

    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition) throws NotValidOperationException{
        if(originPosition.isLocation(Location.NONE)) {
            game.getModel().setComputeInfluenceStrategy(new ComputeInfluenceWithoutStudentColor(color));
            card.effectEpilogue();
            game.setState(originState);
        }
        else{
            throw new NotValidOperationException("You can't get from a location now!");
        }
    }
}
