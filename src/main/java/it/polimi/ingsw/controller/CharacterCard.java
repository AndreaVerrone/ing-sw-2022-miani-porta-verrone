package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.ChangeCurrentPlayerObserver;
import it.polimi.ingsw.model.StudentList;

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

    // MANAGEMENT OF OBSERVERS ON COIN ON CHARACTER CARD
    /**
     * List of the observer on the coin on character card
     */
    private final List<CoinOnCardObserver> coinOnCardObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on character card .
     * @param observer the observer to be added
     */
    public void addCoinOnCardObserver(CoinOnCardObserver observer){
        coinOnCardObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on character card .
     * @param observer the observer to be removed
     */
    public void removeCoinOnCardObserver(CoinOnCardObserver observer){
        coinOnCardObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on character card .
     * @param characterCard the card on which the change has been happened
     * @param coinOnCard the actual value (true, if the coin is present, false otherwise)
     */
    public void notifyCoinOnCardObservers(CharacterCard characterCard,boolean coinOnCard){
        for(CoinOnCardObserver observer : coinOnCardObservers)
            observer.coinOnCardObserverUpdate(this,this.used);
    }

    // MANAGEMENT OF OBSERVERS ON STUDENTS ON CHARACTER CARD
    /**
     * List of the observer on the students on character card.
     */
    private final List<StudentsOnCardObserver> studentsOnCardObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the students on character card.
     * @param observer the observer to be added
     */
    public void addStudentsOnCardObserver(StudentsOnCardObserver observer){
        studentsOnCardObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the students on character card.
     * @param observer the observer to be removed
     */
    public void removeStudentsOnCardObserver(StudentsOnCardObserver observer){
        studentsOnCardObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on the students on character card.
     * @param characterCardsType the character card type on which the student has been changed
     * @param actualStudents the actual student list on island
     */
    public void notifyStudentsOnCardObservers(CharacterCardsType characterCardsType, StudentList actualStudents){
        for(StudentsOnCardObserver observer : studentsOnCardObservers)
            observer.studentsOnCardObserverUpdate(characterCardsType, actualStudents);
    }

}
