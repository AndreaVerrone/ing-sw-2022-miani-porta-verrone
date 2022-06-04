package it.polimi.ingsw.client.view.cli.game.custom_widgets.islands;

import it.polimi.ingsw.client.reduced_model.ReducedIsland;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.*;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;

import java.util.*;

/**
 * A widget used to show on the console the list of islands currently in game
 */
public class IslandsSet extends StatefulWidget {

    /**
     * A map containing all the islands in view mapped to their ID
     */
    private final Map<Integer, IslandView> islands = new HashMap<>();
    /**
     * The ID of the island that currently has mother nature on
     */
    private int motherNaturePosition;

    /**
     * Creates a new set of islands that need to be shown on the screen based
     * on the collection of islands passed as a parameter
     *
     * @param reducedIslands the collection of islands
     */
    public IslandsSet(Collection<ReducedIsland> reducedIslands) {
        for (ReducedIsland island : reducedIslands) {
            islands.put(island.ID(), new IslandView(island));
        }
        create();
    }

    /**
     * Changes the students on the specified island with the new ones passed as parameter
     *
     * @param islandID    the ID of the island
     * @param newStudents the new list of students on the island
     */
    public void studentsChanged(int islandID, StudentList newStudents) {
        islands.get(islandID).studentsChanged(newStudents);
    }

    /**
     * Changes the tower on the specified island with the new one passed as parameter
     *
     * @param islandID the ID of the island
     * @param newTower the new tower on the island
     */
    public void towerChanged(int islandID, TowerType newTower) {
        islands.get(islandID).towerChanged(newTower);
    }

    /**
     * Changes the number of bans on the specified island with the new one passed as parameter
     *
     * @param islandID the ID of the island
     * @param newBans  the new number of bans on the island
     */
    public void bansChanged(int islandID, int newBans) {
        islands.get(islandID).bansChanged(newBans);
    }

    /**
     * Sets which island currently has mother nature on.
     *
     * @param islandID the ID of the island
     */
    public void motherNatureMoved(int islandID) {
        if(motherNaturePosition!=0){
            islands.get(motherNaturePosition).hasMotherNature(false);
        }
        islands.get(islandID).hasMotherNature(true);
        setState(()->motherNaturePosition = islandID);
    }

    /**
     * Unifies the island passed as {@code islandID} with the one removed specified as {@code removedIslandID},
     * adding as much smaller islands as the size of the removed island.
     *
     * @param islandID          the ID of the island kept
     * @param removedIslandID   the ID of the island removed
     * @param removedIslandSize the size of the island remover
     */
    public void unifyIslands(int islandID, int removedIslandID, int removedIslandSize) {
        IslandView island = islands.get(islandID);
        island.addIslands(removedIslandSize, removedIslandID - islandID);
        setState(() -> islands.remove(removedIslandID));
    }

    @Override
    protected Widget build() {
        Collection<Widget> rows = new ArrayList<>();
        List<IslandView> islandViewList = new ArrayList<>(islands.values());
        int startIndex = 0;
        for (int i = 0; i < 3; i++) {
            List<IslandView> subList = islandViewList.subList(startIndex, islandViewList.size());
            IslandView firstUnused = createRow(i, subList, rows);
            if (firstUnused == null) //if all the islands are placed
                break;
            startIndex = islandViewList.indexOf(firstUnused);
            rows.add(new SizedBox(1f, 1f));
        }
        return new Column(rows);
    }

    private IslandView createRow(int rowNumber, List<IslandView> islandViews, Collection<Widget> rows) {
        if (rowNumber == 0)
            return createFirstRow(islandViews, rows);
        if (rowNumber == 1)
            return createSecondRow(islandViews, rows);
        return createThirdRow(islandViews, rows);
    }

    private IslandView createFirstRow(Collection<IslandView> islandViews, Collection<Widget> rows) {
        Collection<Widget> islandsInRow = new ArrayList<>();
        int rowSize = 0;
        for (IslandView island : islandViews) {
            rowSize += island.getSize();
            if (islandsInRow.isEmpty()) {
                islandsInRow.add(island);
                continue;
            }
            if (rowSize <= 5) {
                islandsInRow.add(new SizedBox(1f, 1f));
                islandsInRow.add(island);
                continue;
            }
            rows.add(new Row(islandsInRow));
            return island;
        }
        return null;
    }

    private IslandView createSecondRow(List<IslandView> islandViews, Collection<Widget> rows) {
        if (islandViews.size() == 1) {
            rows.add(new Row(new ArrayList<>(islandViews)));
            return null;
        }
        Collection<Widget> islandsInRow = new ArrayList<>();
        IslandView island1 = islandViews.get(0);
        IslandView island2 = islandViews.get(1);
        IslandView lastIsland = island2;
        islandsInRow.add(island1);
        if (island1.getSize() + island2.getSize() <= 6) { //if the second island can be added
            int emptySpace = 7 * (4 - island1.getSize()) + 1; //the empty space to put in between
            islandsInRow.add(new SizedBox(emptySpace, 1f));
            islandsInRow.add(island2);
            if (islandViews.size() > 2)
                lastIsland = islandViews.get(2);
            else
                lastIsland = null;
        }
        rows.add(new Row(islandsInRow));
        return lastIsland;
    }

    private IslandView createThirdRow(Collection<IslandView> islandViews, Collection<Widget> rows) {
        Collection<Widget> islandsInRow = new ArrayList<>();
        for (IslandView island : islandViews) {
            if (islandsInRow.isEmpty()) {
                islandsInRow.add(island);
                continue;
            }
            islandsInRow.add(new SizedBox(1f, 1f));
            islandsInRow.add(island);
        }
        rows.add(new Row(islandsInRow));
        return null;
    }
}
