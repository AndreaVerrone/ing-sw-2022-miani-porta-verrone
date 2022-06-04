package it.polimi.ingsw.client.view.cli.game.custom_widgets.schoolboard;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.Collection;
import java.util.List;

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

/* SETTERS NOT NEEDED // todo: remove
    public void setEntranceStud(StudentList entranceStud) {
        setState(()->this.entranceStud=entranceStud);
    }

    public void setDiningRoomStud(StudentList diningRoomStud) {
        setState(()->this.diningRoomStud = diningRoomStud);
    }

    public void setActualProfessors(Collection<PawnType> actualProfessors) {
        setState(()->this.actualProfessors = actualProfessors);
    }

    public void setTowerNumber(int towerNumber) {
        setState(()->this.towerNumber = towerNumber);
    }

    public void setTowerColor(TowerType towerColor) {
        setState(()->this.towerColor = towerColor);
    }

    public void setNumOfCoins(int newNumOfCoins) {
        setState(()->this.numOfCoins = newNumOfCoins);
    }
 */

    /**
     * The constructor of the class.
     * @param entranceStud the student in entrance
     * @param diningRoomStud the students in the dining room
     * @param actualProfessors the professors
     * @param towerNumber the number of tower
     * @param towerColor the color of towers
     * @param numOfCoins the number of coins
     * @param owner the owner of the school board
     */
    public SchoolBoardView(
            StudentList entranceStud,
            StudentList diningRoomStud,
            Collection<PawnType> actualProfessors,
            int towerNumber,
            TowerType towerColor,
            int numOfCoins,
            String owner) {
        this.entranceStud = entranceStud;
        this.diningRoomStud = diningRoomStud;
        this.actualProfessors = actualProfessors;
        this.towerNumber = towerNumber;
        this.towerColor = towerColor;
        this.numOfCoins = numOfCoins;
        this.owner = owner;

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
