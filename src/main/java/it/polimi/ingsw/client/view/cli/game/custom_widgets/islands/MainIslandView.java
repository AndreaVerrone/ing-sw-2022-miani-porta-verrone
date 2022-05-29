package it.polimi.ingsw.client.view.cli.game.custom_widgets.islands;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Icons;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.TextStyle;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.TowerView;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A widget used to represent the main content of an island
 */
class MainIslandView extends StatefulWidget {

    /**
     * The title of the island (the island number)
     */
    private final Widget islandHeader;
    /**
     * The actual content of the island (students, tower and mother nature)
     */
    private final GridView islandContent = new GridView();
    /**
     * A widget used to display the bans on the island, if any
     */
    private Widget bans;

    /**
     * Creates a new widget used to display all the information of an island
     * @param ID the id of the island
     * @param students the students on the island
     * @param towerType the tower on the island
     * @param ban the number of bans on the island
     */
    MainIslandView(int ID, StudentList students, TowerType towerType, int ban) {

        islandHeader = new Text(Translator.getIslandName() + ID)
                        .addTextStyle(TextStyle.ITALIC).addTextStyle(TextStyle.BOLD);
        setStudents(students);
        if (towerType != null)
            setTower(towerType);
        setBan(ban);

        create();
    }

    private void setStudents(StudentList students) {
        int pos = 0;
        for (PawnType type : PawnType.values()) {
            Widget student = new StudentOnIsland(type, students.getNumOf(type));
            islandContent.addChild(student, GridView.Position.values()[pos]);
            pos++;
        }
    }

    private void setTower(TowerType tower) {
        islandContent.addChild(new TowerView(tower), GridView.Position.BOTTOM_RIGHT);
    }


    private void setBan(int ban) {
        if (ban == 0) {
            bans = null;
            return;
        }
        Widget banView = new Text(" " + Icons.BAN_EMOJI);
        Collection<Widget> banList = new ArrayList<>(ban);
        for (int i = 0; i < ban; i++) {
            banList.add(banView);
        }
        bans = new Row(banList);
    }

    /**
     * Changes the students on this island with the new ones passed as parameter
     * @param newStudents the new list of students on this island
     */
    void studentsChanged(StudentList newStudents) {
        setState(() -> setStudents(newStudents));
    }

    /**
     * Changes the tower on this island with the new one passed as parameter
     * @param newTower the new tower on this island
     */
    void towerChanged(TowerType newTower) {
        setState(() -> setTower(newTower));
    }

    /**
     * Changes the number of bans on this island with the new one passed as parameter
     * @param newBans the new number of bans on this island
     */
    void bansChanged(int newBans) {
        setState(() -> setBan(newBans));
    }

    /**
     * Sets if this island currently has mother nature on or not.
     * @param motherNatureIsPresent {@code true} if mother nature is on this island, {@code false} otherwise
     */
    void hasMotherNature(boolean motherNatureIsPresent) {
        setState(() ->
                islandContent.addChild(
                        motherNatureIsPresent ? new Icon(Icons.FLOWER_EMOJI) : new Text(""),
                        GridView.Position.BOTTOM_CENTER)
        );
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
        Column column = new Column(List.of(
                islandHeader,
                islandContent));
        if (bans != null)
            column.addChild(bans);

        return new ColoredBox(
                new Border(
                        new SizedBox(
                                column,
                                5f, 5f),
                        BorderType.DOUBLE),
                Color.GREEN);
    }
}
