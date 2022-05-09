package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ComputeInfluenceWithoutStudentColor;
import it.polimi.ingsw.model.PawnType;

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

    public UseCharacterCard8State(Game game, State originState, CharacterCard card){
        this.game = game;
        this.originState = originState;
        this. card = card;
    }
    @Override
    public void chooseStudent(PawnType pawnType) {
        game.getModel().setComputeInfluenceStrategy(new ComputeInfluenceWithoutStudentColor(pawnType));

        card.effectEpilogue();

        game.setState(originState);
    }
}
