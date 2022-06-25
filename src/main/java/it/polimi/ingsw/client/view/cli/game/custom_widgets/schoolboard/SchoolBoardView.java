package it.polimi.ingsw.client.view.cli.game.custom_widgets.schoolboard;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.reduced_model.ReducedPlayer;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.Collection;
import java.util.List;

/**
 * this class is used to give a graphical representation of a school board.
 */
public class SchoolBoardView extends StatefulWidget {

    /**
     * students at the entrance.
     */
    private final StudentList entranceStud;

    /**
     * students in the dining room.
     */
    private final StudentList diningRoomStud;

    /**
     * collection of professors.
     */
    private final Collection<PawnType> actualProfessors;

    /**
     * the number of tower.
     */
    private final int towerNumber;

    /**
     * The color of towers.
     */
    private final TowerType towerColor;

    /**
     * The number of coins.
     */
    private final int numOfCoins;

    /**
     * the owner of the school board.
     */
    private final String owner;

    /**
     * The constructor of the class.
     * It takes in input a reduced version of the school board, and it will
     * create its graphical representation.
     * @param reducedSchoolBoard the reduced school board
     */
    public SchoolBoardView(ReducedPlayer reducedSchoolBoard) {
        this.entranceStud = reducedSchoolBoard.getStudentsInEntrance();
        this.diningRoomStud = reducedSchoolBoard.getStudentsInDiningRoom();
        this.actualProfessors = reducedSchoolBoard.getProfessors();
        this.towerNumber = reducedSchoolBoard.getTowerNumber();
        this.towerColor = reducedSchoolBoard.getTowerType();
        this.numOfCoins = reducedSchoolBoard.getCoinNumber();
        this.owner = reducedSchoolBoard.getOwner();

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
        Text subHeader = new Text(owner);

        // all the components of the school board
        Entrance entrance = new Entrance(entranceStud);
        DiningRoomView diningRoomView = new DiningRoomView(diningRoomStud);
        ProfTable profTable= new ProfTable(actualProfessors);
        TowerLocation towerLocation = new TowerLocation(towerNumber,towerColor);
        CoinCounter coinCounter = new CoinCounter(this.numOfCoins);

        // the layout of the components
        Column locationAndCoinColumn = new Column(List.of(towerLocation,coinCounter));
        Row schoolBoardBody = new Row(List.of(entrance,diningRoomView,profTable,locationAndCoinColumn));
        Column schoolBoard = new Column(List.of(header,subHeader,schoolBoardBody));

        // return the widget
        return new Border(schoolBoard, BorderType.DOUBLE);
    }
}
