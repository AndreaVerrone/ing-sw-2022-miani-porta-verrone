package it.polimi.ingsw.client.view.gui;

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
    ISLAND12(0,2),
    ;

    private final int column;
    private final int row;

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
