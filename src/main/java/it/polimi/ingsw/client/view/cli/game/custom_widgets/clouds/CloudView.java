package it.polimi.ingsw.client.view.cli.game.custom_widgets.clouds;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.reduced_model.ReducedCloud;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.ListOfStudents;
import it.polimi.ingsw.server.model.utils.StudentList;
import java.util.List;

/**
 * this class is used to represent a cloud.
 * It is characterized by the name and the student that are in it.
 */
class CloudView extends StatefulWidget {

    /**
     * ID of the cloud
     */
    private final int ID;

    /**
     * students on the cloud
     */
    private final StudentList students;

    /**
     * the constructor of the class.
     * It will create a representation of a cloud taking in input a reduced cloud.
     * @param reducedCloud the reduced cloud to represent
     */
    CloudView(ReducedCloud reducedCloud) {
        this.students = reducedCloud.students();
        this.ID = reducedCloud.ID();
        create();
    }

    public int getID() {
        return ID;
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

        // the name of the cloud
        Text CloudName = new Text(Translator.getCloudNamePrefixCloudView() +this.ID).addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

        // create the list of the students
        ListOfStudents listOfStudents = new ListOfStudents(students);

        return new Border(
                new Column(List.of(CloudName, listOfStudents)),
                BorderType.DOUBLE
        );
    }
}



