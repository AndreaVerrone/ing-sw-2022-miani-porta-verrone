package it.polimi.ingsw.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.view.cli.fancy_cli.utils.Icons;

/**
 * A widget for displaying an icon
 */
public class Icon extends Widget {

    /**
     * The icon to display
     */
    private final Icons icon;

    /**
     * The color of the icon
     */
    private String color;

    /**
     * The background color for the icon
     */
    private String backgroundColor;

    /**
     * Creates an icon displaying the selected icon with the provided color and background
     * @param icon the icon selected
     * @param color the color of the icon
     * @param backgroundColor the color of the background
     */
    public Icon(Icons icon, Color color, Color backgroundColor){
        this.icon = icon;
        this.color = color.foreground;
        this.backgroundColor = backgroundColor.background;
        setHeight(1);
        setWidth(1);
    }

    /**
     * Creates an icon displaying the selected icon with the provided
     * color and default background color
     * @param icon the icon selected
     * @param color the color of the icon
     */
    public Icon(Icons icon, Color color){
        this(icon, color, Color.DEFAULT);
    }

    /**
     * Creates an icon displaying the selected icon with the default color and background
     * @param icon the icon selected
     */
    public Icon(Icons icon){
        this(icon, Color.DEFAULT, Color.DEFAULT);
    }

    public Icon setColor(Color color){
        this.color = color.foreground;
        return this;
    }

    public Icon setBackgroundColor(Color backgroundColor){
        this.backgroundColor = backgroundColor.background;
        return this;
    }

    @Override
    protected void display() {
        System.out.print(backgroundColor+color+icon);
    }
}
