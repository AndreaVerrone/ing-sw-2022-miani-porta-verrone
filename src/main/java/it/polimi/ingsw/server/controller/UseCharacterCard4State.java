package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.model.GameModel;
import it.polimi.ingsw.server.model.utils.exceptions.IslandNotFoundException;

public class UseCharacterCard4State extends UseCharacterCardState implements State{

    /**
     * This is the model of the game
     * @see GameModel
     */
    private final GameModel gameModel;

    /**
     * The constructor of the class
     * @param game the Game class
     * @param originState the state from which the character card has been used
     * @param characterCard4 the character card that uses this state
     */
    public UseCharacterCard4State(ExpertGame game, State originState, CharacterCard4 characterCard4) {
        super(game,originState,characterCard4);
        gameModel=game.getModel();
    }

    /** This method allows to compute the influence on the island specified on the parameter
     * @param islandID the island on which compute the influence
     * @throws NotValidArgumentException if the island does not exist
     */
    private void computeInfluenceOn(int islandID) throws NotValidArgumentException {
        try {
            gameModel.conquerIsland(islandID);
        } catch (IslandNotFoundException e) {
            throw new NotValidArgumentException("the island does not exist");
        }
    }

    /**
     * This method allows to compute the influence on the island specified in the parameter.
     * @param destination the Position
     * @throws NotValidArgumentException if the island does not exist
     * @throws NotValidOperationException if the location of the position is not an island
     */
    @Override
    public void chooseDestination(Position destination) throws NotValidArgumentException, NotValidOperationException {

        if(!destination.isLocation(Location.ISLAND)){
            throw new NotValidOperationException("you have to chose an island");
        }

        computeInfluenceOn(destination.getField());

        // EPILOGUE
        finalizeCardUsed();
        returnBack();
    }
}
