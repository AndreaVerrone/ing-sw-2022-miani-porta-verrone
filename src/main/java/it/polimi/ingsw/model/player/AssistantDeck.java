package it.polimi.ingsw.model.player;

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
}
