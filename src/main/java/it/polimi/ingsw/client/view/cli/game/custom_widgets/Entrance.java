package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;

import java.util.List;

public class Entrance extends StatefulWidget {

    /**
     * students that are at the entrance.
     */
    private StudentList students;

    /**
     * The constructor of the class
     * @param students the students that are at the entrance
     */
    public Entrance(StudentList students) {
        setState(()->this.students = students);
        create();
    }

    /* NOT NEEDED
    public void setStudents(StudentList students) {
        setState(()->this.students=students);
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

        // the header
        Text header = new Text(Translator.getEntranceHeader()).addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

        // the students
        Text blueStudents = new Text(" █ : "+ this.students.getNumOf(PawnType.BLUE_UNICORNS)).setForegroundColor(Color.BLUE);
        Text greenStudents = new Text(" █ : "+ this.students.getNumOf(PawnType.GREEN_FROGS)).setForegroundColor(Color.GREEN);
        Text yellowStudents = new Text(" █ : "+ this.students.getNumOf(PawnType.YELLOW_GNOMES)).setForegroundColor(Color.BRIGHT_YELLOW);
        Text redStudents = new Text(" █ : "+ this.students.getNumOf(PawnType.RED_DRAGONS)).setForegroundColor(Color.RED);
        Text pinkStudents = new Text(" █ : "+ this.students.getNumOf(PawnType.PINK_FAIRIES)).setForegroundColor(Color.MAGENTA);

        return new Border (
                new Column(List.of(header,blueStudents,greenStudents,yellowStudents,redStudents,pinkStudents)),
                BorderType.SINGLE
        );

    }
}
