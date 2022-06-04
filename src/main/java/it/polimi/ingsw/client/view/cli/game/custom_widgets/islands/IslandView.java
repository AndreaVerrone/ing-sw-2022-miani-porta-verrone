package it.polimi.ingsw.client.view.cli.game.custom_widgets.islands;

import it.polimi.ingsw.client.reduced_model.ReducedIsland;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.BorderType;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.TowerView;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A widget used to display an island of any size on the screen
 */
class IslandView extends StatefulWidget {

    /**
     * The main island of this
     */
    private final MainIslandView mainIsland;
    /**
     * The number of smaller island to put before the main island
     */
    private int islandsBefore;
    /**
     * The number of smaller island to put after the main island
     */
    private int islandsAfter;
    /**
     * The type of tower on the island, if any
     */
    private TowerType tower;
    /**
     * The actual widget that is seen on the console
     */
    private Widget content;

    /**
     * Creates a new island in the console based on the information passed as an argument
     * @param island the reduced version of an island
     */
    IslandView(ReducedIsland island) {
        mainIsland = new MainIslandView(island.ID(), island.studentList(), island.tower(), island.ban());
        int smallerIslands = island.size() - 1;
        islandsBefore = smallerIslands/2;
        islandsAfter = smallerIslands - islandsBefore;
        tower = island.tower();
        createContent();
        create();
    }

    private void createContent(){
        if (islandsBefore == 0 && islandsAfter == 0) {
            content = mainIsland;
            return;
        }
        Widget smallerIsland = new SmallerIsland();
        Collection<Widget> widgets = new ArrayList<>();
        Row row = new Row();
        for (int i = 0; i < islandsBefore; i++) {
            widgets.add(smallerIsland);
        }
        if (!widgets.isEmpty()) {
            row.addChild(new Row(widgets));
            widgets.clear();
        }
        row.addChild(mainIsland);
        for (int i = 0; i < islandsAfter; i++) {
            widgets.add(smallerIsland);
        }
        if (!widgets.isEmpty()) {
            row.addChild(new Row(widgets));
        }

        content = row;
    }

    /**
     * @return the size of this island
     */
    int getSize(){
        return islandsBefore+1+islandsAfter;
    }

    /**
     * Changes the students on this island with the new ones passed as parameter
     * @param newStudents the new list of students on this island
     */
    void studentsChanged(StudentList newStudents) {
        setState(()->mainIsland.studentsChanged(newStudents));
    }

    /**
     * Changes the tower on this island with the new one passed as parameter
     * @param newTower the new tower on this island
     */
    void towerChanged(TowerType newTower) {
        mainIsland.towerChanged(newTower);
        tower = newTower;
        setState(this::createContent);
    }

    /**
     * Changes the number of bans on this island with the new one passed as parameter
     * @param newBans the new number of bans on this island
     */
    void bansChanged(int newBans) {
        setState(()->mainIsland.bansChanged(newBans));
    }

    /**
     * Sets if this island currently has mother nature on or not.
     * @param motherNatureIsPresent {@code true} if mother nature is on this island, {@code false} otherwise
     */
    void hasMotherNature(boolean motherNatureIsPresent) {
        mainIsland.hasMotherNature(motherNatureIsPresent);
    }

    /**
     * Add the specified amount of smaller islands before or after the main island based on the {@code position}
     * parameter. If {@code position} is positive, the islands are placed after the main island; otherwise,
     * the islands are placed before
     * @param number the number of smaller islands to add
     * @param position where the islands should be added
     */
    void addIslands(int number, int position){
        if (position > 0) {
            islandsAfter += number;
        } else {
            islandsBefore += number;
        }
        setState(this::createContent);
    }

    @Override
    protected Widget build() {
        return content;
    }

    /**
     * A smaller version of an island used to visually represent the size of an island.
     * This contains only the tower of the right type.
     */
    class SmallerIsland extends StatelessWidget{

        @Override
        protected Widget build() {
            return new ColoredBox(
                    new Border(
                            new SizedBox(
                                    new TowerView(tower),
                                    3f, 3f
                            ),
                            BorderType.SINGLE
                    ),
                    Color.GREEN
            );
        }
    }
}
