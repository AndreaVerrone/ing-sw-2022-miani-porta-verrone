package it.polimi.ingsw.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.view.cli.fancy_cli.utils.ConsoleCli;

/**
 * A class representing a generic component of the cli
 */
public abstract class Widget implements Cloneable{

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
    private int startingPoint = 1;

    /**
     * The callback to be run when the size of this widget changes
     */
    private Runnable onSizeChange;

    /**
     * The canvas that will draw this widget
     */
    private Canvas canvas;

    /**
     * If this widget is currently on the screen
     */
    private boolean onScreen = false;

    Widget(){}

    protected void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }

    protected final Canvas getCanvas(){
        return canvas;
    }

    /**
     * A method to call everytime the content of the widget changes.
     */
    protected final void update(){
        if (canvas == null)
            return;
        if (onScreen && canvas.shouldUpdate())
            canvas.show();
    }

    protected final int getWidth() {
        return width;
    }

    protected final void setWidth(int width) {
        this.width = width;
        if (onSizeChange != null)
            onSizeChange.run();
    }

    protected final int getHeight() {
        return height;
    }

    protected final void setHeight(int height) {
        this.height = height;
        if (onSizeChange != null)
            onSizeChange.run();
    }

    protected final int getStartingPoint() {
        return startingPoint;
    }

    protected final void setStartingPoint(int startingPoint) {
        this.startingPoint = startingPoint;
    }


    /**
     * A method used to display this widget. This should not be overrider, as it could lead to unwanted
     * behaviours. To specify how the widget should be displayed, use {@link #display()}.
     */
    protected final void show() {
        onScreen = true;
        ConsoleCli.moveToColumn(startingPoint);
        display();
        ConsoleCli.resetStyle();
        ConsoleCli.moveToColumn(startingPoint+width);
    }

    /**
     * A method to describe in which way this widget should be displayed on the console
     */
    abstract protected void display();

    /**
     * Adds a callback to be run when the size of this widget changes
     * @param callback the callback to run
     */
    protected final void onSizeChange(Runnable callback){
        if (onSizeChange == null)
            onSizeChange = callback;
    }

    /**
     * Removes the listener of the changes of the size of this widget
     */
    protected final void detachListener(){
        onSizeChange = null;
    }

    @Override
    protected Widget clone() {
        try {
            return (Widget) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
