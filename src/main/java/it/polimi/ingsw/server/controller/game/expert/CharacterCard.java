package it.polimi.ingsw.server.controller.game.expert;

import it.polimi.ingsw.server.controller.game.expert.card_observers.CoinOnCardObserver;
import it.polimi.ingsw.server.controller.game.expert.card_observers.StudentsOnCardObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing character cards
 */
abstract public class CharacterCard {

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
     * the type of the card
     * @see CharacterCardsType
     */
    private final CharacterCardsType cardType;

    /**
     * Creates a new character card with the specified initial cost and the description passed as a parameter.
     * @param characterCardsType the type of this card
     */
    protected CharacterCard(CharacterCardsType characterCardsType){
        this.cardType = characterCardsType;
        this.effectDescription = cardType.getDescription();
        this.cost = cardType.getCost();
    }

    private String getEffectDescription(){
        return effectDescription;
    }

    int getCost(){
        return cost;
    }

    protected CharacterCardsType getCardType() {
        return cardType;
    }

    boolean isUsed(){
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
            notifyCoinOnCardObservers();
        }
    }

    /**
     * The actual implementation of the effect of this card
     */
    public abstract void effect();

    // MANAGEMENT OF OBSERVERS ON COIN ON CHARACTER CARD
    /**
     * List of the observer on the coin on character card
     */
    private final List<CoinOnCardObserver> coinOnCardObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on character card usage .
     * @param observer the observer to be added
     */
    void addCoinOnCardObserver(CoinOnCardObserver observer){
        coinOnCardObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on character card usage.
     * @param observer the observer to be removed
     */
    void removeCoinOnCardObserver(CoinOnCardObserver observer){
        coinOnCardObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that the card has been used.
     */
    private void notifyCoinOnCardObservers(){
        for(CoinOnCardObserver observer : coinOnCardObservers)
            observer.coinOnCardObserverUpdate(this.cardType);
    }

    // MANAGEMENT OF OBSERVERS ON STUDENTS ON CHARACTER CARD

    /**
     * This method allows to add the observer, passed as a parameter, on the students on character card.
     * @param observer the observer to be added
     */
    protected void addStudentsOnCardObserver(StudentsOnCardObserver observer){}

    /**
     * This method allows to remove the observer, passed as a parameter, on the students on character card.
     * @param observer the observer to be removed
     */
    protected void removeStudentsOnCardObserver(StudentsOnCardObserver observer){}

}
