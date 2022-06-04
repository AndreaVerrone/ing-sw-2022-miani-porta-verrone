package it.polimi.ingsw.client.view.cli.game.custom_widgets.schoolboard;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.List;

class TowerLocation extends StatefulWidget {

    /**
     * The number of towers
     */
    int towerNumber;

    /**
     * The color of the tower
     */
    TowerType towerColor;

    // NOT NEEDED // todo: remove
    /*void setTowerNumber(int towerNumber) {
        setState(()->this.towerNumber=towerNumber);
    }*/

    // NOT NEEDED // todo: remove
    /*void setTowerColor(TowerType towerColor) {
        setState(()->this.towerColor=towerColor);
    }*/

    /**
     * The constructor of the class.
     * @param towerNumber the number of towers
     * @param towerColor the color of the tower
     */
    TowerLocation(int towerNumber, TowerType towerColor) {
        this.towerNumber = towerNumber;
        this.towerColor = towerColor;

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
        Text header = new Text(Translator.getTowerLocationHeader()).addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

        // the content of the widget
        Text content = new Text(" █ : "+towerNumber + " ");
        switch (towerColor){
            case WHITE -> content.setForegroundColor(Color.WHITE);
            case GREY -> content.setForegroundColor(Color.GREY);
            case BLACK -> content.setForegroundColor(Color.BLACK);
        }

        return new Border(new Column(List.of(header, content)), BorderType.SINGLE);
    }
}
