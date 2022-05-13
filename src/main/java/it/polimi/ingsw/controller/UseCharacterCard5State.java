package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.gametable.exceptions.IslandNotFoundException;

public class UseCharacterCard5State implements State {

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
    private final CharacterCard5 characterCard5;

    /**
     * The constructor of the class
     * @param game the Game class
     * @param originState the state from which the character card has been used
     * @param characterCard5 the character card that uses this state
     */
    public UseCharacterCard5State(Game game, State originState, CharacterCard5 characterCard5) {
        this.game=game;
        this.originState=originState;
        this.characterCard5=characterCard5;
    }

    /**
     * This method allows to set the ban on the island specified on the parameter
     * @param islandID the island ID of the island on which put the ban
     * @throws NotValidArgumentException if the island does not exist
     */
    public void setBanOnIsland(int islandID) throws NotValidArgumentException {
        try {
            game.getModel().getGameTable().getIsland(islandID).addBan();
        } catch (IslandNotFoundException e) {
            throw new NotValidArgumentException("the island does not exist");
        }
    }

    /**
     * This method allows to go back to the state at which the character card has been used.
     */
    private void returnBack(){
        // if everything is fine:
        characterCard5.effectEpilogue();
        // in addiction to the usual epilogue remove a ban from the card
        characterCard5.removeBan();
        // return to the origin state
        game.setState(originState);
    }

    /** This method allows to set the ban on the island specified on the parameter.
     * @param destination the Position
     * @throws NotValidOperationException if the location of the position is not an island
     * @throws NotValidArgumentException if the island does not exist
     */
    @Override
    public void chooseDestination(Position destination) throws NotValidArgumentException, NotValidOperationException {

        if(!destination.isLocation(Location.ISLAND)){
            throw new NotValidOperationException("you have to chose an island");
        }

        setBanOnIsland(destination.getField());
        returnBack();
    }
}
