package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Icons;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Column;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.StatefulWidget;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Text;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Widget;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;

import java.util.List;

/**
 * a widget to use to represent a generic list of students.
 */
public class ListOfStudents extends StatefulWidget {

    /**
     * the student list to use to fill the column with students.
     */
    private final StudentList students;

    /**
     * The constructor of the class.
     * It will create a widget containing a column of students.
     * In each row there is a colored symbol representing the student followed by the number of student of that
     * color that it is present in the list to represent
     * @param students the student list to represent
     */
    public ListOfStudents(StudentList students) {
        this.students=students;
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

        Text blueStudents = new Text(" " + Icons.PAWN + " : "+ this.students.getNumOf(PawnType.BLUE_UNICORNS)).setForegroundColor(Color.BLUE);
        Text greenStudents = new Text(" " + Icons.PAWN + " : "+ this.students.getNumOf(PawnType.GREEN_FROGS)).setForegroundColor(Color.GREEN);
        Text yellowStudents = new Text(" " + Icons.PAWN + " : "+ this.students.getNumOf(PawnType.YELLOW_GNOMES)).setForegroundColor(Color.BRIGHT_YELLOW);
        Text redStudents = new Text(" " + Icons.PAWN + " : "+ this.students.getNumOf(PawnType.RED_DRAGONS)).setForegroundColor(Color.RED);
        Text pinkStudents = new Text(" " + Icons.PAWN + " : "+ this.students.getNumOf(PawnType.PINK_FAIRIES)).setForegroundColor(Color.MAGENTA);

        return new Column(
                List.of(
                    blueStudents,
                    greenStudents,
                    yellowStudents,
                    redStudents,
                    pinkStudents
                )
        );
    }
}
