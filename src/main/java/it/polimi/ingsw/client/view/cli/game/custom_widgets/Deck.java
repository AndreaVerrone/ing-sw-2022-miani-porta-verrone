package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.player.Assistant;

import java.util.ArrayList;
import java.util.Collection;

/**
 * this class represent a deck of cards.
 */
public class Deck extends StatefulWidget {

    /**
     * the list of the Assistant card that are in the deck.
     */
    private final Collection<Assistant> assistantsList;

    public Collection<Assistant> getAssistantsList() {
        return new ArrayList<>(this.assistantsList);
    }

    /**
     * The name of the deck.
     */
    private final String deckName = Translator.getPlayerDeckName();

    /**
     * the max length of the row of the deck.
     */
    private static final int MAX_ROW_LENGHT = 5;

    /**
     * The constructor of the class
     * @param assistantsList the list of assistant card that compose the deck
     */
    public Deck(Collection<Assistant> assistantsList) {
        this.assistantsList = assistantsList;
        create();
    }

    /**
     * A method used to define by which Widgets this StatefulWidget is composed.
     * This method is run every time something in the content change or when it should be
     * displayed, so no heavy processes should be done inside it.
     *
     * @return a Widget describing how this should be drawn on screen
     */
    @Override
    protected Widget build() {

        // collection containing the content of the widget
        Collection<Widget> content = new ArrayList<>();

        // add the name of the deck to the content
        content.add(new Text(deckName).addTextStyle(TextStyle.BOLD));

        // collection containing the assistant cards
        Collection<Widget> assistantsInRow = new ArrayList<>();
        for (Assistant assistant : assistantsList) {
            if (assistantsInRow.size() == MAX_ROW_LENGHT) {
                // if the first row is full, add to the content of the widget the first row
                content.add(new Row(assistantsInRow));
                // then empty the row in order to have a clean list
                // in which add the cards of the second row
                assistantsInRow.clear();
            }
            assistantsInRow.add(new AssistantCard(assistant));
        }
        if (!assistantsInRow.isEmpty())
            content.add(new Row(assistantsInRow));

        return new Border(new Column(content), BorderType.DOUBLE);

    }
}
