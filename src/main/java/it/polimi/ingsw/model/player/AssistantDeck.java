package it.polimi.ingsw.model.player;

import it.polimi.ingsw.model.ChangeAssistantDeckObserver;

import java.util.*;

/**
 * This class represent the deck of the assistant cards.
 */
public class AssistantDeck {

    /**
     * this is the wizard associated to the cards
     */
    private final Wizard wizard;

    /**
     * this is the deck containing the available assistant cards
     */
    private final Set<Assistant> deck = new HashSet<>();

    /**
     * This is the constructor of the class.
     * It will associate the {@code wizard} passed as parameter and it will set all the cards in {@code Assistant}
     * @see Assistant
     * @param wizard is the wizard that will be associated to the deck
     */
    AssistantDeck(Wizard wizard){
        this.wizard=wizard;
        // Assistant.values() returns an array containing the constants of the enum type Assistant
        // List.of returns an unmodifiable list containing using the array in input
        deck.addAll(List.of((Assistant.values())));
    }

    /**
     * This method remove the {@code assistant} passed in input from the deck
     * @param assistant is the card to eliminate from the deck
     */
    void removeAssistant(Assistant assistant){
        assert deck.contains(assistant): "the assistant non present";
        deck.remove(assistant);
        notifyChangeAssistantDeckObservers();
    }

    Wizard getWizard(){
        return wizard;
    }

    /**
     * Gets the current cards in the deck
     * <p>
     * Note: this should be used only to observe the content.
     * To modify it, use the appropriate methods of {@code AssistantDeck}.
     * @return the cards in the deck
     * @see #removeAssistant(Assistant)
     */
    Set<Assistant> getCards() {
        // return a copy of the arraylist passed as a parameter
        return new HashSet<>(deck);
    }

    // MANAGEMENT OF OBSERVERS ON ASSISTANT DECK
    /**
     * List of the observer on the assistant deck.
     */
    private final List<ChangeAssistantDeckObserver> changeAssistantDeckObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on the assistant deck.
     * @param observer the observer to be added
     */
    public void addChangeAssistantDeckObserver(ChangeAssistantDeckObserver observer){
        changeAssistantDeckObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the assistant deck.
     * @param observer the observer to be removed
     */
    public void removeChangeAssistantDeckObserver(ChangeAssistantDeckObserver observer){
        changeAssistantDeckObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on the assistant deck.
     */
    public void notifyChangeAssistantDeckObservers(){
        for(ChangeAssistantDeckObserver observer : changeAssistantDeckObservers)
            observer.changeAssistantDeckObserverUpdate();
    }
}
