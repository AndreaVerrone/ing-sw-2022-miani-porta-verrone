package it.polimi.ingsw.client.view.gui;

public enum CloudPosition {
    CLOUD1(2,2),
    CLOUD2(3,4),
    CLOUD3(3,2),
    ;

    private final int column;
    private final int row;
    
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
