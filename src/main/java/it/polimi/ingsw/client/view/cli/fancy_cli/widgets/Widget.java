package it.polimi.ingsw.client.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.ConsoleCli;

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

    private Color backgroundColor = Color.DEFAULT;

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

    void setCanvas(Canvas canvas){
        this.canvas = canvas;
    }

    final Canvas getCanvas(){
        return canvas;
    }

    /**
     * A method to call everytime the content of the widget changes.
     */
    void update(){
        if (canvas == null)
            return;
        if (onScreen && canvas.shouldUpdate())
            canvas.show();
    }

    final int getWidth() {
        return width;
    }

    final void setWidth(int width) {
        this.width = width;
        if (onSizeChange != null)
            onSizeChange.run();
    }

    final int getHeight() {
        return height;
    }

    final void setHeight(int height) {
        this.height = height;
        if (onSizeChange != null)
            onSizeChange.run();
    }

    final int getStartingPoint() {
        return startingPoint;
    }

    final void setStartingPoint(int startingPoint) {
        this.startingPoint = startingPoint;
    }

    void setBgColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    Color getBgColor(){
        return backgroundColor;
    }

    /**
     * A method used to display this widget. This should not be overrider, as it could lead to unwanted
     * behaviours. To specify how the widget should be displayed, use {@link #display()}.
     */
    final void show() {
        onScreen = true;
        ConsoleCli.moveToColumn(startingPoint);
        display();
        ConsoleCli.resetStyle();
        ConsoleCli.moveToColumn(startingPoint+width);
    }

    /**
     * A method to describe in which way this widget should be displayed on the console
     */
    abstract void display();

    /**
     * Adds a callback to be run when the size of this widget changes
     * @param callback the callback to run
     */
    void onSizeChange(Runnable callback){
        if (onSizeChange == null)
            onSizeChange = callback;
    }

    /**
     * Removes the listener of the changes of the size of this widget
     */
    final void detachListener(){
        onSizeChange = null;
    }

    /**
     * Creates a deep copy of this widget and removes any size listener and canvas from it.
     * @return a deep copy of this widget
     */
    @Override
    protected Widget clone() {
        try {
            Widget widget = (Widget) super.clone();
            widget.canvas = null;
            widget.onSizeChange = null;
            return widget;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
