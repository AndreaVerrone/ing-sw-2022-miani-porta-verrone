package it.polimi.ingsw.client.view.cli.game.custom_widgets.clouds;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.reduced_model.ReducedCloud;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;

import java.util.*;

/**
 * this class is a widget used to represent a set of clouds.
 */
public class CloudsSet extends StatefulWidget {

    /**
     * a collection containing the reduced clouds
     */
    private final Collection<ReducedCloud> clouds = new ArrayList<>();

    /**
     * the constructor of the class.
     * It will create a widget to represent a set of clouds
     * @param clouds the map containing the < ID, student list > pair of the clouds
     */
    public CloudsSet(Collection<ReducedCloud> clouds) {

        // create the list of reduced cloud
        this.clouds.addAll(clouds);
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

        // the header of the widget
        Text header = new Text(Translator.getHeaderNameOfClouSet()).addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

        // the row of clouds
        Row cloudRow = new Row();
        for(ReducedCloud cloud : clouds){
            cloudRow.addChild(new CloudView(cloud));
        }

        return new Border(new Column(List.of(header,cloudRow)), BorderType.SINGLE);

    }
}
