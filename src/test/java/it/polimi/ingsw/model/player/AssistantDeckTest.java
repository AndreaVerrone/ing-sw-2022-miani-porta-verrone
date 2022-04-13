package it.polimi.ingsw.model.player;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AssistantDeckTest {

    AssistantDeck assistantDeck = null;

    @BeforeEach
    void setUp() {
        assistantDeck = new AssistantDeck(Wizard.W1);
    }

    @AfterEach
    void tearDown() {
        assistantDeck = null;
    }

    @Test
    public void constructor__ShouldContainAllCards(){
        Set<Assistant> setOfAllCards = new HashSet<>(List.of(Assistant.values()));
        Set<Assistant> cardInDeck = assistantDeck.getCards();
        assertEquals(setOfAllCards,cardInDeck);
    }

    @Test
    public void removeAssistant__ShouldThrow(){
       // remove all the elements in the set
       for(int i=0; i <Assistant.values().length; i=i+1){
            assistantDeck.removeAssistant(Assistant.values()[i]);
       }
       // remove an element that is not present
       assertThrows(AssertionError.class, () -> assistantDeck.removeAssistant(Assistant.CARD_1));
    }

}