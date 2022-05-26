package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.player.Assistant;

import java.util.ArrayList;
import java.util.List;

/**
 * this class represent a deck of cards.
 */
public class Deck extends StatefulWidget {

    /**
     * the list of the Assistant card that are in the deck.
     */
    private List<Assistant> assistantsList;

    public List<Assistant> getAssistantsList() {
        return new ArrayList<>(this.assistantsList);
    }

    /**
     * The name of the deck.
     */
    private final String deckName;

    /**
     * the max length of the row of the deck.
     */
    private final int MAX_ROW_LENGHT = 5;

    /**
     * The constructor of the class
     * @param assistantsList the list of assistant card that compose the deck
     * @param deckName the name of the deck of card
     */
    public Deck(List<Assistant> assistantsList, String deckName) {

        this.assistantsList = assistantsList;
        this.deckName=deckName;

        create();
    }

    /* NOT NEEDED
    public void setAssistantsList(List<Assistant> newAssistantsList) {
        setState(()->this.assistantsList=newAssistantsList);
    }*/

    /**
     * This method will add the assistant card passed as a parameter to the assistant
     * @param assistant the assistant card to be added
     */
    public void addAssistant(Assistant assistant){
        List <Assistant> newAssistantList = getAssistantsList();
        newAssistantList.add(assistant);

        setState(()-> this.assistantsList = newAssistantList);
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

        // TODO: find a better way to implement it

        // fill the grid with the assistant cards
        Row row1 = new Row();
        Row row2 = new Row();

        int numOfRow=0; // num of rows needed

        for(int i=0;i<assistantsList.size();i++){
            if(i<MAX_ROW_LENGHT){
                row1.addChild(new AssistantCard(assistantsList.get(i)));
                numOfRow=1; // 1 row needed
            }else{
                row2.addChild(new AssistantCard(assistantsList.get(i)));
                numOfRow=2; // 2 rows needed
            }
        }

        Text deckHeader =  new Text(deckName).addTextStyle(TextStyle.BOLD);

        Column deck = switch (numOfRow) {
            case 0 -> new Column(List.of(deckHeader));
            case 1 -> new Column(List.of(deckHeader, row1));
            case 2 -> new Column(List.of(deckHeader, row1, row2));
            default -> new Column();
        };

        return new Border(deck,BorderType.DOUBLE);
    }
}
