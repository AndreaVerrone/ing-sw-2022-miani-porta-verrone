package it.polimi.ingsw.client.view.gui;

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

    private final int column;
    private final int row;

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
