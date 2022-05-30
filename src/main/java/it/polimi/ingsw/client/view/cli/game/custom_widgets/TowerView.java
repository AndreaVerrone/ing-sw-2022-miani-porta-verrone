package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.StatelessWidget;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Text;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Widget;
import it.polimi.ingsw.server.model.utils.TowerType;

/**
 * A widget used to represent the tower of a particular type
 */
public class TowerView extends StatelessWidget {

    /**
     * The color of the tower
     */
    private final Color towerColor;

    /**
     * Creates a CLI representation of a tower of the specified type
     * @param tower the type of tower to represent
     */
    public TowerView(TowerType tower) {
        towerColor = switch (tower) {
            case BLACK -> Color.BLACK;
            case WHITE -> Color.WHITE;
            case GREY -> Color.GREY;
        };
        create();
    }

    @Override
    protected Widget build() {
        return new Text("  ").setBackgroundColor(towerColor);
    }
}
