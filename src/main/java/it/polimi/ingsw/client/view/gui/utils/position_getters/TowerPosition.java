package it.polimi.ingsw.client.view.gui.utils.position_getters;

/**
 * Class to get the position of the towers on the grid of the tower hall of each school board of the view
 */
public enum TowerPosition {
    TOWER1(0,1),
    TOWER2(1,1),
    TOWER3(0,2),
    TOWER4(1,2),
    TOWER5(0,3),
    TOWER6(1,3),
    TOWER7(0,4),
    TOWER8(1,4);

    /**
     * Column of the grid where the tower is located
     */
    private final int column;

    /**
     * Row of the grid where the tower is located
     */
    private final int row;

    /**
     * Constructor of the class. Saves the column and the row of the tower on the grid of the tower hall of each school board
     * @param column number of the column where the tower is located
     * @param row number of the row where the tower is located
     */
    TowerPosition(int column, int row) {
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
