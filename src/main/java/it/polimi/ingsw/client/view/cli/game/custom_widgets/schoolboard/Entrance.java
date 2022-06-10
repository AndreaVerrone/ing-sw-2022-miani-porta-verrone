package it.polimi.ingsw.client.view.cli.game.custom_widgets.schoolboard;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.ListOfStudents;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;

import java.util.List;

class Entrance extends StatefulWidget {

    /**
     * students that are at the entrance.
     */
    private final StudentList students;

    /**
     * The constructor of the class
     * @param students the students that are at the entrance
     */
    Entrance(StudentList students) {
        this.students = students;
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

        // the header
        Text header = new Text(Translator.getEntranceHeader()).addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

        // the students
        ListOfStudents listOfStudents = new ListOfStudents(students);

        return new Border(
                new Column(List.of(header,listOfStudents)),
                BorderType.SINGLE
        );

    }
}
