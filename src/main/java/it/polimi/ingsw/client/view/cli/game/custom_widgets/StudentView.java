package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Icons;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Icon;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.StatelessWidget;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Widget;
import it.polimi.ingsw.server.model.utils.PawnType;

/**
 * A widget used to display one student
 */
public class StudentView extends StatelessWidget {

    /**
     * The type of student
     */
    private final PawnType student;

    /**
     * Creates a widget that displays the passed student
     * @param student the type of student
     */
    public StudentView(PawnType student) {
        this.student = student;
        create();
    }

    @Override
    protected Widget build() {
        Icon icon = new Icon(Icons.PAWN);
        return switch (student){
            case BLUE_UNICORNS -> icon.setColor(Color.BLUE);
            case GREEN_FROGS -> icon.setColor(Color.GREEN);
            case YELLOW_GNOMES -> icon.setColor(Color.YELLOW);
            case RED_DRAGONS -> icon.setColor(Color.RED);
            case PINK_FAIRIES -> icon.setColor(Color.BRIGHT_MAGENTA);
        };
    }
}
