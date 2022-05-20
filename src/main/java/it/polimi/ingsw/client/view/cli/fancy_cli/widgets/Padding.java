package it.polimi.ingsw.client.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.ConsoleCli;

/**
 * A widget that make possible to add padding around a widget
 */
public class Padding extends Widget {

    /**
     * The content of this padding widget
     */
    private final Widget child;

    /**
     * The empty space to put above and below the child. It is measured in number of lines.
     */
    private final int verticalPadding;

    /**
     * The empty space to put before and after the child. It is measured in characters number.
     */
    private final int horizontalPadding;

    /**
     * Creates an asymmetric padding around the provided widget, putting {@code verticalPadding} empty lines
     * above and below the child, and {@code horizontalPadding} empty spaces before and after the child.
     * @param child the content of this widget
     * @param verticalPadding the number of empty lines to put in the vertical direction
     * @param horizontalPadding the number of empty characters to put in the horizontal direction
     */
    public Padding(Widget child, int verticalPadding, int horizontalPadding){
        this.child = child;
        this.verticalPadding = verticalPadding;
        this.horizontalPadding = horizontalPadding;

        setWidth(child.getWidth()+this.horizontalPadding*2);
        setHeight(child.getHeight()+this.verticalPadding*2);

        child.onSizeChange(() ->{
            setWidth(child.getWidth()+this.horizontalPadding*2);
            setHeight(child.getHeight()+this.verticalPadding*2);
        });
    }

    /**
     * Creates an asymmetric padding around the provided widget, putting {@code verticalPadding} empty lines
     * above and below the child, and {@code horizontalPadding} empty spaces before and after the child.
     * @param child the content of this widget
     * @param verticalPadding the empty space to put in the vertical direction, in points number
     * @param horizontalPadding the empty space to put in the horizontal direction, in points number
     */
    public Padding(Widget child, float verticalPadding, float horizontalPadding){
        this(child, Math.round(verticalPadding), ConsoleCli.convertFromGeneralWidthToCharNumber(horizontalPadding));
    }

    /**
     * Creates a symmetric padding around the provided widget, putting {@code padding} empty
     * spaces all around the child.
     * @param child the content of this widget
     * @param padding the empty space to put around the widget, in points number
     */
    public Padding(Widget child, float padding){
        this(child, padding, padding);
    }

    @Override
    protected void setCanvas(Canvas canvas) {
        super.setCanvas(canvas);
        child.setCanvas(canvas);
    }

    @Override
    protected void display() {
        showVerticalPadding();
        child.setStartingPoint(getStartingPoint()+horizontalPadding);
        child.show();
        showVerticalPadding();
    }

    private void showVerticalPadding(){
        for (int i = 0; i < verticalPadding; i++) {
            System.out.print("\n");
        }
    }
}
