package it.polimi.ingsw.view.cli.fancy_cli;

/**
 * A class representing a generic component of the cli
 */
public abstract class Widget implements Drawable {

    /**
     * The width, in character width number, of this widget
     */
    private int width;

    /**
     * The height, in number of lines, of this widget
     */
    private int height;

    /**
     * The column number from which this widget should be drawn
     */
    private int startingPoint = 0;

    public int getWidth() {
        return width;
    }

    protected void setWidth(int width) {
        this.width = width;
    }

    protected int getHeight() {
        return height;
    }

    protected void setHeight(int height) {
        this.height = height;
    }

    protected int getStartingPoint() {
        return startingPoint;
    }

    protected void setStartingPoint(int startingPoint) {
        this.startingPoint = startingPoint;
    }
}
