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
     * Only after the first use of the card, this method will mark this card as used,
     * and it will increment by one its cost.
     */
    public void setAsUsed(){
        if (!used){
            used = true;
            cost++;
        }
    }

    /**
     * The actual implementation of the effect of this card
     */
    public abstract void effect();

}
