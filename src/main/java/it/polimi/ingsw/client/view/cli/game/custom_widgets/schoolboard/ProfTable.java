package it.polimi.ingsw.client.view.cli.game.custom_widgets.schoolboard;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.PawnType;
import java.util.Collection;
import java.util.List;


/**
 * a class to visualize the professors table.
 */
class ProfTable extends StatefulWidget {

    /**
     * the list of professors.
     */
    private final Collection<PawnType> actualProfessors;

    /**
     * The constructor of the class.
     * @param actualProfessors the professor list
     */
    ProfTable(Collection<PawnType> actualProfessors) {
        this.actualProfessors = actualProfessors;
        create();
    }

    // todo: remove not needed
    /*void setActualProfessors(Collection<PawnType> actualProfessors) {
        setState(()->this.actualProfessors=actualProfessors);
    }*/

    /**
     * A method used to define by which Widgets this StatefulWidget is composed.
     * This method is run every time something in the content change or when it should be
     * displayed, so no heavy processes should be done inside it.
     *
     * @return a Widget describing how this should be drawn on screen
     */
    @Override
    protected Widget build() {

        String stringHeader = Translator.getProfTableHeader();
        Text header = new Text(stringHeader).addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

        Text blueStudents = new Text("").setForegroundColor(Color.BLUE);
        Text greenStudents = new Text("").setForegroundColor(Color.GREEN);
        Text yellowStudents = new Text("").setForegroundColor(Color.BRIGHT_YELLOW);
        Text redStudents = new Text("").setForegroundColor(Color.RED);
        Text pinkStudents = new Text("").setForegroundColor(Color.MAGENTA);

        int numOfSpace =  stringHeader.length()/2;
        StringBuilder padding = new StringBuilder();
        padding.append(" ".repeat(numOfSpace));

        if(actualProfessors.contains(PawnType.BLUE_UNICORNS)) {
            blueStudents.setText(padding + "█");
            //blueStudents.setText(padding + "\uD83D\uDFE6");
        }

        if(actualProfessors.contains(PawnType.GREEN_FROGS)) {
            greenStudents.setText(padding + "█");
            //greenStudents.setText(padding+ "\uD83D\uDFE9");
        }

        if(actualProfessors.contains(PawnType.YELLOW_GNOMES)) {
            yellowStudents.setText(padding + "█");
            //yellowStudents.setText(padding+"\uD83D\uDFE8");
        }

        if(actualProfessors.contains(PawnType.RED_DRAGONS)) {
            redStudents.setText(padding + "█");
            //redStudents.setText(padding+"\uD83D\uDFE5");
        }

        if(actualProfessors.contains(PawnType.PINK_FAIRIES)) {
            pinkStudents.setText(padding + "█");
            //pinkStudents.setText(padding + "\uD83D\uDFEA");
        }

        return new Border(
                new Column(List.of(
                        header,
                        blueStudents,
                        greenStudents,
                        yellowStudents,
                        redStudents,
                        pinkStudents)),
                BorderType.SINGLE);
    }
}
