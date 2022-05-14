package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NotEnoughCoinsException;

/**
 * A class representing character cards
 */
abstract public class CharacterCard {

    /**
     * the Game class
     */
    Game game;

    /**
     * A textual description of the effect of this card
     */
    private final String effectDescription;

    /**
     * The cost that have to be paid to use this card
     */
    private int cost;

    /**
     * If this card has been used since the beginning of the game.
     * If true the card must have a coin on it
     */
    private boolean used = false;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param cost the initial cost of the card
     * @param effectDescription the description of the effect of this card
     */
    CharacterCard(int cost, String effectDescription){
        this.effectDescription = effectDescription;
        this.cost = cost;
    }

    public String getEffectDescription(){
        return effectDescription;
    }

    public int getCost(){
        return cost;
    }

    public boolean isUsed(){
        return used;
    }

    /**
     * This method will mark this card as used
     */
    private void setAsUsed(){
        if (!used){
            used = true;
        }
    }

    /**
     * This method allow to increment the cost of the usage of the card
     */
    private void incrementCost(){
        cost ++;
    }

    /**
     * The actual implementation of the effect of this card
     */
    public abstract void effect();

    /**
     * This method must be called after the effect of the card has been properly applied
     * in order to take the coin of the usage from the player and signal that it cannot
     * use another time in this turn a character card and
     * then set the card as used, and increment its cost.
     */
    public void effectEpilogue(){

        // 1. set that the current player has used a character card
        game.setCanUseCharacterCard(false);

        // 2. take the cost of the card from the player
        boolean putInBagAllCoins = this.isUsed();
        int cost = this.getCost();
        try {
            game.getModel().getCurrentPlayer().removeCoins(cost,putInBagAllCoins);
        } catch (NotEnoughCoinsException e) {
            // todo: how to manage?
            // it is impossible
            // I have checked before
        }

        // 2. set the card as used
        setAsUsed();

        // 3. increment the cost of the card
        incrementCost();
    }
}
