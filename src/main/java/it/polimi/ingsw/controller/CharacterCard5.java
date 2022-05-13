package it.polimi.ingsw.controller;

public class CharacterCard5 extends CharacterCard{

    /**
     * This is the Game class
     * @see Game
     */
    private final Game game;

    /**
     * the number of ban cards that are present on the island.
     */
    private int numOfBans;
    /**
     * Creates a new character card with the specified initial cost, the description
     * and the game passed as a parameter.
     * @param cost              the initial cost of the card
     * @param effectDescription the description of the effect of this card
     * @param game              the Game class
     */
    CharacterCard5(int cost, String effectDescription, Game game) {
        super(cost, effectDescription);
        this.game=game;
        numOfBans=4;
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard5State(game,game.getState(),this));
    }

    public int getNumOfBans() {
        return numOfBans;
    }

    /**
     * This method allow to add a ban on the card.
     * <p>
     * Note:
     * The number of bans on this card cannot be more than 4
     */
    public void addBan() {
        assert numOfBans<=4 : "bans are too much";
        this.numOfBans++;
    }

    /**
     * This method allow to remove a bran from the card
     */
    public void removeBan() {
        if(numOfBans<=0){
            numOfBans=0;
        }else {
            this.numOfBans--;
        }
    }

}
