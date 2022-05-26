package it.polimi.ingsw.client.view.cli.game.custom_widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.List;

public class CoinCounter extends StatefulWidget {

    /**
     * The number of coins
     */
    private int coinNumber;

    /**
     * The constructor of the class
     * @param coinNumber the number of coins
     */
    public CoinCounter(int coinNumber) {
        setState(()->this.coinNumber=coinNumber);
        create();
    }

    /* NOT NEEDED
    public void setCoinNumber(int newCoinNumber) {
        setState(() -> this.coinNumber=newCoinNumber);
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
        Text header = new Text("COINS").addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

        // the number of coins
        Text numOfCoins = new Text(" ● : " + this.coinNumber + " ").setForegroundColor(Color.YELLOW);

        return new Border(new Column(List.of(header,numOfCoins)),BorderType.SINGLE);

    }
}
