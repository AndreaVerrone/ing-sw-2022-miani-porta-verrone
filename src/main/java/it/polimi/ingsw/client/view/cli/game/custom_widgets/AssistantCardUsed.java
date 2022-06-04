package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.player.Assistant;

import java.util.List;

/**
 * a class used to represent the assistant card.
 */
public class AssistantCardUsed extends StatefulWidget {

    /**
     * The assistant card to display
     */
    private final Assistant assistantCard;

    /**
     * the player that has used the card
     */
    private String player;

    /**
     * The constructor of the class
     * @param assistantCard the assistant card
     */
    public AssistantCardUsed(Assistant assistantCard) {
        this.assistantCard=assistantCard;
        create();
    }

    public String getPlayer() {
        return player;
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

        // the name of the card
        Text name = new Text(assistantCard.name()).addTextStyle(TextStyle.BOLD).addTextStyle(TextStyle.ITALIC).setForegroundColor(Color.BRIGHT_GREY);

        // the value of the card
        Text value = new Text(Translator.getValueFieldAssistantCard() + assistantCard.getValue());

        // the range of motion of the card
        Text rangeOfMotion = new Text(Translator.getRangeOfMotionFieldAssistantCard()+ assistantCard.getRangeOfMotion());

        // the card
        return new Border(new Column(List.of(name,value,rangeOfMotion)),BorderType.SINGLE);

    }
}