package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.StudentList;

import java.util.*;

public class CloudsSet extends StatefulWidget {

    /**
     * a map containing the IDs of the clouds and the corresponding cloud widget.
     */
    private Map<Integer, CloudView> mapIdCloudWidget= new HashMap<>();

    /**
     * a collection containing the clouds widget
     */
    private Collection<CloudView> cloudViews = new ArrayList<>();

    /**
     * the constructor of the class
     * @param clouds the map containing the < ID, student list > pair of the clouds
     */
    public CloudsSet(Map<Integer, StudentList> clouds) {

        // create the list of cloud widget and fill the mapIdCloudWidget
        for(Map.Entry<Integer,StudentList> cloud : clouds.entrySet()){
            CloudView newCloudView = new CloudView(cloud.getKey(),cloud.getValue());

            cloudViews.add(newCloudView);
            mapIdCloudWidget.put(cloud.getKey(),newCloudView);
        }

        create();
    }

    /**
     * This method will update the student list of the cloud with the ID specified in the parameter
     * @param ID the ID of the cloud on which the student list must be changed
     * @param studentList the new student list to put on the island
     */
    /* NOT NEEDED
    public void updateStudentList(int ID, StudentList studentList) {
        mapIdCloudWidget.get(ID).setStudents(studentList);
    }
    */

    /**
     * A method used to define by which Widgets this StatefulWidget is composed.
     * This method is run every time something in the content change or when it should be
     * displayed, so no heavy processes should be done inside it.
     *
     * @return a Widget describing how this should be drawn on screen
     */
    @Override
    protected Widget build() {

        // the header of the widget
        Text header = new Text(Translator.getHeaderNameOfClouSet()).addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

        // the row of clouds
        Row cloudRow = new Row();
        for(CloudView cloudView : cloudViews){
            cloudRow.addChild(cloudView);
        }

        return new Border(new Column(List.of(header,cloudRow)), BorderType.SINGLE);

    }
}
