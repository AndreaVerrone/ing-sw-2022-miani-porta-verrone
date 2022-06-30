package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.reduced_model.ReducedCharacter;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Icons;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.StudentList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A widget used to display a character card
 */
public class CharacterCardView extends StatefulWidget {

    /**
     * The card to display
     */
    private final ReducedCharacter card;

    /**
     * Creates a new widget used to display the passed card on the screen
     * @param card the card to display
     */
    public CharacterCardView(ReducedCharacter card) {
        this.card = card;
        create();
    }

    @Override
    protected Widget build() {
        List<Widget> content = new ArrayList<>(List.of(
                new Text(Translator.getCardLabel() + card.getType().name().substring(4)),
                new Text(Translator.getCostLabel() + card.getCost()),
                new Text(Translator.getEffectDescription(card.getType()), 30)
        ));
        if (card.getStudentList() != null)
            content.add(createStudents(card.getStudentList()));
        if (card.getBans() != null)
            content.add(createBans(card.getBans()));
        return new Border(
                new Column(content));
    }

    private Widget createStudents(StudentList studentList) {
        List<Widget> widgets = new ArrayList<>();
        studentList.forEach(type -> {
            widgets.add(new StudentView(type));
            widgets.add(new SizedBox(1, 1));
        });
        widgets.remove(widgets.size()-1);
        return new Row(widgets);
    }

    private Widget createBans(int numBans) {
        Widget banView = new Text(Icons.BAN_EMOJI + " ");
        Collection<Widget> banList = new ArrayList<>();
        for (int i = 0; i < numBans; i++) {
            banList.add(banView);
        }
        return new Row(banList);
    }
}
