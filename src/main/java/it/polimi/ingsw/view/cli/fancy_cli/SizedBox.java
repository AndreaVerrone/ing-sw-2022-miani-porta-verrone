package it.polimi.ingsw.view.cli.fancy_cli;

import it.polimi.ingsw.view.cli.fancy_cli.utils.Alignment;
import it.polimi.ingsw.view.cli.fancy_cli.utils.ConsoleCli;

/**
 * A widget for reserving a minimum amount of space on the screen
 */
public class SizedBox extends Widget{

    /**
     * The content of this widget
     */
    private final Widget child;
    /**
     * The alignment of the child in this
     */
    private final Alignment alignment;

    /**
     * The minimum width that this widget should occupy
     */
    private final int minWidth;
    /**
     * The minimum height that this widget should occupy
     */
    private final int minHeight;

    /**
     * The amount of space to put above the child, accordingly to the alignment
     */
    private int spaceTop;
    /**
     * The amount of space to put before the child, accordingly to the alignment
     */
    private int spaceBefore;

    /**
     * Creates a box big no less than the dimension specified,
     * aligning the child inside it as chosen. A value less than 1 for width or height indicates
     * to use the corresponding size of the child.
     * @param child the content of this widget
     * @param width the minimum width of this widget
     * @param height the minimum height of this widget
     * @param alignment the alignment of the child in this widget
     */
    public SizedBox(Widget child, int width, int height, Alignment alignment) {
        this.child = child;
        this.alignment = alignment;
        minWidth = ConsoleCli.convertFromGeneralWidthToCharNumber(width);
        minHeight = height;
        calculateDimensions();
        child.onSizeChange(this::calculateDimensions);
    }

    /**
     * Creates a box big no less than the dimension specified, centering the child inside it.
     * A value less than 1 for width or height indicates to use the corresponding size of the child.
     * @param child the content of this widget
     * @param width the minimum width of this widget
     * @param height the minimum height of this widget
     */
    public SizedBox(Widget child, int width, int height) {
        this(child, width, height, Alignment.CENTER);
    }

    /**
     * Creates an empty box big as the dimension specified.
     * A value less than 1 for width or height is converted to 1.
     * @param width the minimum width of this widget
     * @param height the minimum height of this widget
     */
    public SizedBox(int width, int height) {
        this(new Text(" "), width, height, Alignment.BOTTOM);
    }


    @Override
    protected void display() {
        for (int i = 0; i < spaceTop; i++) {
            System.out.print("\n");
        }
        child.setStartingPoint(getStartingPoint()+spaceBefore);
        child.show();
    }

    private void calculateDimensions(){
        setWidth(Math.max(child.getWidth(), minWidth));
        setHeight(Math.max(child.getHeight(), minHeight));
        calculateSpacing();
    }

    private void calculateSpacing(){
        int horizontalGap = getWidth() - child.getWidth();
        int verticalGap = getHeight() - child.getHeight();
        switch (alignment){
            case CENTER -> {
                spaceTop = verticalGap/2;
                spaceBefore = horizontalGap/2;
            }
            case TOP -> {
                spaceTop = 0;
                spaceBefore = horizontalGap/2;
            }
            case BOTTOM -> {
                spaceTop = verticalGap;
                spaceBefore = horizontalGap/2;
            }
            case START -> {
                spaceTop = verticalGap/2;
                spaceBefore = 0;
            }
            case END -> {
                spaceTop = verticalGap/2;
                spaceBefore = horizontalGap;
            }
            case TOP_START -> {
                spaceTop = 0;
                spaceBefore = 0;
            }
            case TOP_END -> {
                spaceTop = 0;
                spaceBefore = horizontalGap;
            }
            case BOTTOM_START -> {
                spaceTop = verticalGap;
                spaceBefore = 0;
            }
            case BOTTOM_END -> {
                spaceTop = verticalGap;
                spaceBefore = horizontalGap;
            }
        }
    }

}
