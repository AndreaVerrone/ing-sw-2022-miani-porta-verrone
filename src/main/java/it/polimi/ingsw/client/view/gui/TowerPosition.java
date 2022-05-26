package it.polimi.ingsw.client.view.gui;

public enum TowerPosition {
    TOWER1(0,1),
    TOWER2(1,1),
    TOWER3(0,2),
    TOWER4(1,2),
    TOWER5(0,3),
    TOWER6(1,3),
    TOWER7(0,4),
    TOWER8(1,4);

    private final int column;
    private final int row;

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
