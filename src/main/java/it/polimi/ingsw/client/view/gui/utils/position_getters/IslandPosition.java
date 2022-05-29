package it.polimi.ingsw.client.view.gui.utils.position_getters;

/**
 * Class to get the position of the islands on the {@code islandGrid} of the view
 */
public enum IslandPosition {
    ISLAND1(1,0),
    ISLAND2(2,0),
    ISLAND3(3,0),
    ISLAND4(4,0),
    ISLAND5(5,2),
    ISLAND6(5,4),
    ISLAND7(4,6),
    ISLAND8(3,6),
    ISLAND9(2,6),
    ISLAND10(1,6),
    ISLAND11(0,4),
    ISLAND12(0,2);

    /**
     * Column of the grid where the island is located
     */
    private final int column;

    /**
     * Row of the grid where the island is located
     */
    private final int row;

    /**
     * Constructor of the class. Saves the column and the row of the island on the {@code islandGrid}
     * @param column number of the column where the island is located
     * @param row number of the row where the island is located
     */
    IslandPosition(int column, int row) {
    this.column = column;
    this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }
}
