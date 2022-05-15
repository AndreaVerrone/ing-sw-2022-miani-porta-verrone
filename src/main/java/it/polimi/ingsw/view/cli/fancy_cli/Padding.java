package it.polimi.ingsw.view.cli.fancy_cli;

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
     * An EM sized empty space
     */
    private static final String EMPTY_SPACE = "\u2003";

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

        setWidth(child.getWidth()+horizontalPadding*2);
        setHeight(child.getHeight()+verticalPadding*2);
    }

    /**
     * Creates a symmetric padding around the provided widget, putting {@code padding} empty
     * spaces all around the child.
     * @param child the content of this widget
     * @param padding the number of empty lines to put in the vertical direction
     */
    public Padding(Widget child, int padding){
        this(child, padding, Math.round(padding*2.5f));
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
