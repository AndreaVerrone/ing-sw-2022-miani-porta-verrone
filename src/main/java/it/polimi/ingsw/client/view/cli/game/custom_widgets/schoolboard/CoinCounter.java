package it.polimi.ingsw.client.view.cli.game.custom_widgets.schoolboard;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Icons;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;

import java.util.List;

/**
 * A class used to give a graphical representation of the number of coins
 */
class CoinCounter extends StatefulWidget {

    /**
     * The number of coins
     */
    private final int coinNumber;

    /**
     * The constructor of the class.
     * It will create the class taking in input the number of coins.
     * @param coinNumber the number of coins
     */
    CoinCounter(int coinNumber) {
        this.coinNumber=coinNumber;
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
        Text header = new Text(Translator.getCoinCounterHeader()).addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

        // the number of coins
        Text numOfCoins = new Text(" " + Icons.COIN + " : " + this.coinNumber + " ").setForegroundColor(Color.YELLOW);

        return new Border(new Column(List.of(header,numOfCoins)),BorderType.SINGLE);

    }
}
