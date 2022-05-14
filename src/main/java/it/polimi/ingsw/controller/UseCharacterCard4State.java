package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;

public class UseCharacterCard4State implements State{

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
    private final CharacterCard4 characterCard4;

    /**
     * The constructor of the class
     * @param game the Game class
     * @param originState the state from which the character card has been used
     * @param characterCard4 the character card that uses this state
     */
    public UseCharacterCard4State(Game game, State originState, CharacterCard4 characterCard4) {
        this.game = game;
        this.originState = originState;
        this.characterCard4=characterCard4;
    }

    /** This method allows to compute the influence on the island specified on the parameter
     * @param islandID the island on which compute the influence
     * @throws NotValidArgumentException if the island does not exist
     */
    private void computeInfluenceOn(int islandID) throws NotValidArgumentException {
        try {
            game.getModel().conquerIsland(islandID);
        } catch (IslandNotFoundException e) {
            throw new NotValidArgumentException("the island does not exist");
        }
    }

    /**
     * This method allows to go back to the state at which the character card has been used.
     */
    private void returnBack(){
        // return to the original state
        characterCard4.effectEpilogue();
        game.setState(originState);
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
        returnBack();
    }
}
