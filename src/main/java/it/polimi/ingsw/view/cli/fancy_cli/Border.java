package it.polimi.ingsw.view.cli.fancy_cli;

import it.polimi.ingsw.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.view.cli.fancy_cli.utils.ConsoleCli;
import it.polimi.ingsw.view.cli.fancy_cli.utils.Icons;

/**
 * A widget that make possible to add a border around another widget
 */
public class Border extends Widget{

    /**
     * The content of this widget
     */
    private final Widget child;

    /**
     * The type of border to draw
     */
    private final BorderType borderType;

    /**
     * The color of the border
     */
    private final String color;

    /**
     * Creates a border of the specified type around the child using the provided color
     * @param child the content of this widget
     * @param borderType the type of border chosen
     * @param color the color of the border
     */
    public Border(Widget child, BorderType borderType, Color color) {
        this.child = child;
        this.borderType = borderType;
        this.color = color.foreground;
        setHeight(child.getHeight()+2);
        setWidth(child.getWidth()+2);
    }

    /**
     * Creates a border of the specified type around the child using the default color.
     * @param child the content of this widget
     * @param borderType the type of border chosen
     */
    public Border(Widget child, BorderType borderType){
        this(child, borderType, Color.DEFAULT);
    }

    /**
     * Creates a border around the child using the specified color and the default doubled border.
     * @param child the content of this widget
     * @param color the color of the border
     */
    public Border(Widget child, Color color){
        this(child, BorderType.DOUBLE, color);
    }

    /**
     * Creates a border around the child using the default color and doubled border.
     * @param child the content of this widget
     */
    public Border(Widget child){
        this(child, BorderType.DOUBLE, Color.DEFAULT);
    }

    @Override
    public void show() {

        child.setStartingPoint(getStartingPoint()+1);

        ConsoleCli.moveToColumn(getStartingPoint());
        drawBorder();


        child.show();
    }

    private void drawBorder(){
        drawSegment(borderType.getTopLeft());
        for (int i = 0; i < child.getWidth(); i++) {
            drawSegment(borderType.getHorizontal());
        }
        drawSegment(borderType.getTopRight());
        System.out.print("\n");
        for (int i = 0; i < child.getHeight(); i++) {
            drawSegment(borderType.getVertical());
            ConsoleCli.moveCursorRight(child.getWidth());
            drawSegment(borderType.getVertical());
            System.out.print("\n");
        }
        drawSegment(borderType.getBottomLeft());
        for (int i = 0; i < child.getWidth(); i++) {
            drawSegment(borderType.getHorizontal());
        }
        drawSegment(borderType.getBottomRight());
        ConsoleCli.moveCursorUp(child.getHeight());
    }

    private void drawSegment(Icons segment){
        System.out.print(color+segment);
        ConsoleCli.resetStyle();
    }
}
