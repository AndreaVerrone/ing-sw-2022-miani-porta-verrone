package it.polimi.ingsw.server.controller.game.expert.card_states;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.controller.game.expert.cards.CharacterCard8;
import it.polimi.ingsw.server.controller.game.states.GameState;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.strategies.influence.ComputeInfluenceWithoutStudentColor;
import it.polimi.ingsw.server.model.utils.PawnType;

/**
 * Class to implement the state where the current player can use the card 8
 */
public class UseCharacterCard8State extends UseCharacterCardState {

    /**
     * Model of the game
     */
    private final GameModel model;


    /**
     * Constructor of the class. Saves the game, the state before this one and the card used
     * @param game game class of the game
     * @param card card used in the state
     */
    public UseCharacterCard8State(ExpertGame game, GameState originState, CharacterCard8 card){
        super(game, originState, card);
        this.model = game.getModel();
    }

    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition) throws NotValidArgumentException {
        if(originPosition.isLocation(Location.NONE)) {
            //Change strategy
            model.setComputeInfluenceStrategy(new ComputeInfluenceWithoutStudentColor(color));

            // EPILOGUE
            finalizeCardUsed();
            returnBack();
        }
        else{
            throw new NotValidArgumentException();
        }
    }

    @Override
    public StateType getType() {
        return StateType.USE_CHARACTER_CARD8_STATE;
    }
}
