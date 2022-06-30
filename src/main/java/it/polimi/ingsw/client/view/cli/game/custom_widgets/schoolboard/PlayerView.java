package it.polimi.ingsw.client.view.cli.game.custom_widgets.schoolboard;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.reduced_model.ReducedPlayer;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.AssistantCard;

import java.util.List;

/**
 * this class is used to give a graphical representation of a school board.
 */
public class PlayerView extends StatelessWidget {

    /**
     * The reduced version of this player
     */
    private final ReducedPlayer reducedPlayer;

    /**
     * If this player is playing an expert game
     */
    private final boolean isExpertGame;

    /**
     * The constructor of the class.
     * It takes in input a reduced version of the school board, and it will
     * create its graphical representation.
     *
     * @param reducedPlayer the reduced school board
     * @param isExpertGame if the player is playing an expert game
     */
    public PlayerView(ReducedPlayer reducedPlayer, boolean isExpertGame) {
        this.reducedPlayer = reducedPlayer;
        this.isExpertGame = isExpertGame;
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
        Text header = new Text(Translator.getSchoolBoardViewHeader()).addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);

        // the name of the owner of the school board
        Text subHeader = new Text(reducedPlayer.getOwner());

        // all the components of the school board
        Entrance entrance = new Entrance(reducedPlayer.getStudentsInEntrance());
        DiningRoomView diningRoomView = new DiningRoomView(reducedPlayer.getStudentsInDiningRoom());
        ProfTable profTable= new ProfTable(reducedPlayer.getProfessors());
        TowerLocation towerLocation = new TowerLocation(reducedPlayer.getTowerNumber(), reducedPlayer.getTowerType());
        Widget assistantUsed = new AssistantCard(reducedPlayer.getLastAssistantUsed());
        CoinCounter coinCounter = new CoinCounter(reducedPlayer.getCoinNumber());

        // the layout of the components
        Widget locationAndCoinColumn = isExpertGame ? new Column(List.of(towerLocation,coinCounter))
                : towerLocation;
        Widget schoolBoardBody = new Row(List.of(entrance,diningRoomView,profTable,locationAndCoinColumn));
        Widget schoolBoard = new Column(List.of(header,subHeader,schoolBoardBody));

        // return the widget
        return new Row(List.of(new Border(schoolBoard), assistantUsed));
    }
}
