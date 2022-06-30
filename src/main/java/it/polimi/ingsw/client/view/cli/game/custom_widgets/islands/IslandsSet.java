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

    public Collection<Integer> getIslandsID() {
        return Collections.unmodifiableCollection(islands.keySet());
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
        islands.get(motherNaturePosition).hasMotherNature(false);
        islands.get(islandID).hasMotherNature(true);
        motherNaturePosition = islandID;
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
        for (int i = 0; i < 3; i++) {
            islandViewList = createRow(i, islandViewList, rows);
            rows.add(new SizedBox(1f, 1f));
        }
        return new Column(rows);
    }

    /**
     * Creates one of the three rows that populates the island set
     * @param rowNumber the number of row that needs to be created
     * @param islandViews the list of islands remained to display
     * @param rows the list of widget representing the three row of this island set
     * @return the first unused island in the list
     */
    private List<IslandView> createRow(int rowNumber, List<IslandView> islandViews, Collection<Widget> rows) {
        if (rowNumber == 0)
            return createFirstRow(islandViews, rows);
        if (rowNumber == 1)
            return createSecondRow(islandViews, rows);
        return createThirdRow(islandViews, rows);
    }

    private List<IslandView> createFirstRow(Collection<IslandView> islandViews, Collection<Widget> rows) {
        Collection<Widget> islandsInRow = new ArrayList<>();
        List<IslandView> islandsRemaining = new ArrayList<>(islandViews);
        int rowSize = 0;
        for (IslandView island : islandViews) {
            rowSize += island.getSize();
            if (islandsInRow.isEmpty()) {
                islandsInRow.add(island);
                islandsRemaining.remove(island);
                continue;
            }
            if (rowSize <= 5) {
                islandsInRow.add(new SizedBox(1f, 1f));
                islandsInRow.add(island);
                islandsRemaining.remove(island);
                continue;
            }
            rows.add(new Row(islandsInRow));
            return islandsRemaining;
        }
        return List.of();
    }

    private List<IslandView> createSecondRow(List<IslandView> islandViews, Collection<Widget> rows) {
        if (islandViews.isEmpty())
            return List.of();

        if (islandViews.size() == 1) {
            rows.add(new Row(new ArrayList<>(islandViews)));
            return List.of();
        }
        Collection<Widget> islandsInRow = new ArrayList<>();
        List<IslandView> islandsRemaining = new ArrayList<>(islandViews);
        IslandView island1 = islandsRemaining.remove(0); // take next island to display
        IslandView island2 = islandsRemaining.get(islandsRemaining.size()-1); // take last island
        if (island1.getSize() + island2.getSize() <= 6) { //if the second island can be added
            int emptySpace = 7 * (4 - island1.getSize()) + 1; //the empty space to put in between
            islandsInRow.add(island2);
            islandsInRow.add(new SizedBox(emptySpace, 1f));
            islandsInRow.add(island1);
            islandsRemaining.remove(island2);
        } else
            islandsInRow.add(island1);
        rows.add(new Row(islandsInRow));
        return islandsRemaining;
    }

    private List<IslandView> createThirdRow(Collection<IslandView> islandViews, Collection<Widget> rows) {
        if (islandViews.isEmpty())
            return List.of();
        Collection<Widget> islandsInRow = new ArrayList<>();
        List<IslandView> islands = new ArrayList<>(islandViews);
        Collections.reverse(islands);
        for (IslandView island : islands) {
            if (islandsInRow.isEmpty()) {
                islandsInRow.add(island);
                continue;
            }
            islandsInRow.add(new SizedBox(1f, 1f));
            islandsInRow.add(island);
        }
        rows.add(new Row(islandsInRow));
        return List.of();
    }
}
