package it.polimi.ingsw.server.controller.game.expert.card_states;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.game.Location;
import it.polimi.ingsw.server.controller.game.Position;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.controller.game.expert.cards.CharacterCard5;
import it.polimi.ingsw.server.controller.game.states.GameState;
import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;

public class UseCharacterCard5State extends UseCharacterCardState {

    /**
     * This is the model of the game
     * @see GameModel
     */
    private final GameModel gameModel;

    /**
     * The character card that uses this state
     */
    private final CharacterCard5 characterCard5;

    /**
     * The constructor of the class
     * @param game the Game class
     * @param originState the state from which the character card has been used
     * @param characterCard5 the character card that uses this state
     */
    public UseCharacterCard5State(ExpertGame game, GameState originState, CharacterCard5 characterCard5) {
        super(game,originState,characterCard5);
        this.gameModel=game.getModel();
        this.characterCard5=characterCard5;
    }

    /**
     * This method allows to set the ban on the island specified on the parameter
     * @param islandID the island ID of the island on which put the ban
     * @throws NotValidArgumentException if the island does not exist
     * @throws NotValidOperationException if there is no any ban on the island
     */
    private void setBanOnIsland(int islandID) throws NotValidArgumentException, NotValidOperationException {

        if(characterCard5.getNumOfBans()<=0){
            throw new NotValidOperationException("this card cannot be used, there are no bans on it");
        }

        try {
            gameModel.getGameTable().getIsland(islandID).addBan();
        } catch (IslandNotFoundException e) {
            throw new NotValidArgumentException("the island does not exist");
        }
    }

    /** This method allows to set the ban on the island specified on the parameter.
     * @param destination the Position
     * @throws NotValidOperationException if the location of the position is not an island or
     *                                    if there is no any ban on the island
     * @throws NotValidArgumentException if the island does not exist
     */
    @Override
    public void chooseDestination(Position destination) throws NotValidArgumentException, NotValidOperationException {

        if(!destination.isLocation(Location.ISLAND)){
            throw new NotValidOperationException("you have to chose an island");
        }

        setBanOnIsland(destination.getField());

        // EPILOGUE
        finalizeCardUsed();
        // in addiction to the usual epilogue remove a ban from the card
        characterCard5.removeBan();
        returnBack();
    }
}
