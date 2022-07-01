package it.polimi.ingsw.server.controller.game.expert.card_states;

import it.polimi.ingsw.network.messages.responses.ErrorCode;
import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.StateType;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.controller.game.expert.cards.CharacterCard1;
import it.polimi.ingsw.server.controller.game.states.GameState;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.exceptions.EmptyBagException;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;

public class UseCharacterCard1State extends UseCharacterCardState {

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
    public UseCharacterCard1State(ExpertGame game, GameState originState, CharacterCard1 characterCard1) {
        super(game,originState,characterCard1);
        this.gameModel=game.getModel();
        this.characterCard1=characterCard1;
    }

    /**
     * @param color the {@code PawnType} of the student
     * @param position the {@code Position} from where take the student
     * @throws NotValidArgumentException if the student is not present on the character card 1
     */
    @Override
    public void choseStudentFromLocation(PawnType color, Position position) throws NotValidArgumentException {

        // CHECKS
        // 1. check that the location is the one of character card 1
        if(!position.isLocation(Location.CHARACTER_CARD_1)){
            throw new NotValidArgumentException();
        }

        // 2. the student to move is present on the card
        if(characterCard1.getStudentList().getNumOf(color)<=0) {
            throw new NotValidArgumentException(ErrorCode.STUDENT_NOT_PRESENT);
        }

        // ACTIONS
        // if everything is right set the student to move
        studentToMove=color;
    }

    /**
     * This method allows to put a student on the island specified in the parameter.
     * @param destination the Position
     * @throws NotValidArgumentException if the island does not exist
     */
    @Override
    public void chooseDestination(Position destination) throws NotValidArgumentException {

        // CHECKS
        // 1. check that the student to move has been set
        if(studentToMove==null){
            return;
        }

        // 2. check that the destination is an island
        if(!destination.isLocation(Location.ISLAND)){
            throw new NotValidArgumentException();
        }

        // ACTIONS OF THE METHOD

        // 1. add the student removed from the card to the island if the island
        // exist otherwise throw an exception
        try {
            gameModel.getGameTable().addToIsland(studentToMove,destination.getField());
        } catch (IslandNotFoundException e) {
            throw new NotValidArgumentException(ErrorCode.ISLAND_NOT_EXIST);
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
            characterCard1.addStudentToCard(gameModel.getGameTable().getStudentFromBag());
        } catch (EmptyBagException e) {
            e.printStackTrace();
        }

        // EPILOGUE
        finalizeCardUsed();
        returnBack();
    }

    @Override
    public StateType getType() {
        return StateType.USE_CHARACTER_CARD1_STATE;
    }
}
