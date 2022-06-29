package it.polimi.ingsw.server.controller.game.expert.card_states;

import it.polimi.ingsw.network.messages.responses.ErrorCode;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.controller.game.expert.cards.CharacterCard11;
import it.polimi.ingsw.server.controller.game.states.GameState;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.exceptions.EmptyBagException;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import it.polimi.ingsw.server.model.utils.exceptions.ReachedMaxStudentException;

public class UseCharacterCard11State extends UseCharacterCardState {

    /**
     * This is the model of the game
     *
     * @see GameModel
     */
    private final GameModel gameModel;

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
    public UseCharacterCard11State(ExpertGame game, GameState originState, CharacterCard11 characterCard11) {
        super(game,originState,characterCard11);
        this.gameModel = game.getModel();
        this.characterCard11 = characterCard11;
    }

    private void moveFromCardToDiningRoom(PawnType pawnType) throws NotValidArgumentException {

        // remove student from card
        try {
            characterCard11.removeStudentFromCard(pawnType);
        } catch (NotEnoughStudentException e) {
            throw new NotValidArgumentException(ErrorCode.STUDENT_NOT_PRESENT);
        }

        // add student removed from the card to the dining room
        try {
            gameModel.getCurrentPlayer().addStudentToDiningRoom(pawnType);
        } catch (ReachedMaxStudentException e) {
            characterCard11.addStudentToCard(pawnType);
            throw new NotValidArgumentException(ErrorCode.DININGROOM_FULL);
        }

        // take a student from the bag and put on the card
        try {
            characterCard11.addStudentToCard(gameModel.getGameTable().getStudentFromBag());
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method allows to put a student to the dining room.
     * @param color the {@code PawnType} of the student
     * @param originPosition the {@code Position} from where take the student
     * @throws NotValidArgumentException if the dining room is full or if the student is not present on the card
     */
    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition) throws NotValidArgumentException {

        if(!originPosition.isLocation(Location.CHARACTER_CARD_11)){
            throw new NotValidArgumentException();
        }

        moveFromCardToDiningRoom(color);

        // EPILOGUE
        finalizeCardUsed();
        returnBack();
    }

    @Override
    public StateType getType() {
        return StateType.USE_CHARACTER_CARD11_STATE;
    }
}

