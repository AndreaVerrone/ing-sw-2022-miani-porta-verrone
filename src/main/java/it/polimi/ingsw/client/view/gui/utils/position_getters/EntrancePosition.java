package it.polimi.ingsw.client.view.gui.utils.position_getters;

/**
 * Class to get the position of the students on the grid of the entrance of each school board of the view
 */
public enum EntrancePosition {
    STUDENT1(1,1),
    STUDENT2(0,2),
    STUDENT3(1,2),
    STUDENT4(0,3),
    STUDENT5(1,3),
    STUDENT6(0,4),
    STUDENT7(1,4),
    STUDENT8(0,5),
    STUDENT9(1,5);

    /**
     * Column of the grid where the student is located
     */
    private final int column;

    /**
     * Row of the grid where the student is located
     */
    private final int row;

    /**
     * Constructor of the class. Saves the column and the row of the student on the grid of the entrance of each school board
     * @param column number of the column where the student is located
     * @param row number of the row where the student is located
     */
    EntrancePosition(int column, int row) {
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
