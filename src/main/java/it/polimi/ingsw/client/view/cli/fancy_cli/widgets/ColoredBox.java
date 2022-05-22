package it.polimi.ingsw.client.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.ConsoleCli;

/**
 * A widget used to create a colored background for another widget
 */
public class ColoredBox extends Widget{

    /**
     * The content of this widget
     */
    private final Widget child;
    /**
     * The color of the background
     */
    private final Color color;

    /**
     * Creates a new widget that shows a colored background behind the provided widget.
     * @param child the widget whose background must be colored
     * @param color the color for the background
     */
    public ColoredBox(Widget child, Color color){
        this.child = child;
        this.color = color;
        child.setBgColor(color);
        setHeight(child.getHeight());
        setWidth(child.getWidth());
    }
    @Override
    void display() {
        for (int i = 0; i < child.getHeight(); i++) {
            ConsoleCli.moveToColumn(getStartingPoint());
            System.out.print(color.background);
            for (int j = 0; j < child.getWidth(); j++) {
                System.out.print(" ");
            }
            ConsoleCli.resetStyle();
            System.out.print("\n");
        }
        ConsoleCli.moveCursorUp(child.getHeight());
        child.setStartingPoint(getStartingPoint());
        child.show();
    }
}
