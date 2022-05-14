package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NotEnoughStudentException;
import it.polimi.ingsw.model.PawnType;
import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;
import it.polimi.ingsw.model.player.Player;

public class UseCharacterCard12State implements State{

    /**
     * This is the Game class
     * @see Game
     */
    private final Game game;

    /**
     * This is the state from which the character card has been used and
     * this is the state in which the game have to return after the usage of the card.
     */
    private final State originState;

    /**
     * The character card that uses this state
     */
    private final CharacterCard12 characterCard12;

    public UseCharacterCard12State(Game game, State originState, CharacterCard12 characterCard12) {
        this.game = game;
        this.originState = originState;
        this.characterCard12=characterCard12;
    }

    /** This method allows to remove 3 student of the specified color in the parameter from the dining room
     * of all players.
     * If there are less than 3 players, it will be removed all the students of that color that is possible to remove.
     */
    private void removeStudentFromDiningRoom(PawnType color){

        for (Player player : game.getModel().getPlayerList()) {
            try {
                for (int i = 0; i < 3; i++) {
                    player.removeStudentFromDiningRoom(color);
                }
            } catch (NotEnoughStudentException e) {
                // do nothing
            }
        }
    }

    /**
     * This method allows to go back to the state at which the character card has been used.
     */
    private void returnBack(){
        game.effectEpilogue(characterCard12);
        characterCard12.setAsUsed();
        game.setState(originState);
    }

    /**
     * This method allows to remove 3 student of the specified color in the parameter from the dining room
     * of all players.
     * If there are less than 3 players, it will be removed all the students of that color that is possible to remove.
     * @param color the {@code PawnType} of the student
     * @param originPosition the {@code Position} from where take the student
     * @throws NotValidOperationException if the student are taken from a location
     */
    @Override
    public void choseStudentFromLocation(PawnType color, Position originPosition) throws NotValidOperationException {

        if(!originPosition.isLocation(Location.NONE)){
            throw new NotValidOperationException("you cannot take student from the table");
        }

        removeStudentFromDiningRoom(color);
        returnBack();
    }
}
