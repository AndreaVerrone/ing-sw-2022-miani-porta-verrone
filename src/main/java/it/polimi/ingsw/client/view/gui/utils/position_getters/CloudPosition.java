package it.polimi.ingsw.client.view.gui.utils.position_getters;

/**
 * Class to get the position of the clouds on the {@code islandGrid} of the view
 */
public enum CloudPosition {
    CLOUD1(2,2),
    CLOUD2(3,4),
    CLOUD3(3,2);

    /**
     * Column of the grid where the cloud is located
     */
    private final int column;

    /**
     * Row of the grid where the cloud is located
     */
    private final int row;

    /**
     * Constructor of the class. Saves the column and the row of the cloud on the {@code islandGrid}
     * @param column number of the column where the cloud is located
     * @param row number of the row where the cloud is located
     */
    CloudPosition(int column, int row) {
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
