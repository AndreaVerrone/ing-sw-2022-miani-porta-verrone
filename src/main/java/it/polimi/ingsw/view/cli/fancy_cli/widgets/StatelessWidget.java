package it.polimi.ingsw.view.cli.fancy_cli.widgets;

/**
 * A class used to create new customized widgets that can't change over time.
 * When possible this should be used instead of {@link StatefulWidget} as this is more lightweight.
 */
public abstract class StatelessWidget extends Widget{

    /**
     * The content of this widget
     */
    private final Widget content;

    /**
     * Creates a new widget with content that can't change over time
     */
    protected StatelessWidget() {
        Widget widget = build();
        content = widget.clone();
        widget.setCanvas(null);
        setWidth(content.getWidth());
        setHeight(content.getHeight());
    }

    /**
     * Converts the passed widget to a StatelessWidget
     * @param widget the widget to convert
     * @return a StatelessWidget that shows the passed widget
     */
    public static Widget from(Widget widget){
        return new StatelessWidget() {
            @Override
            protected Widget build() {
                return widget;
            }
        };
    }


    @Override
    void onSizeChange(Runnable callback) {
        //disable the behaviour
    }

    @Override
    void update() {
        //disable the behaviour
    }

    @Override
    void display() {
        content.setStartingPoint(getStartingPoint());
        content.display();
    }

    /**
     * A method used to define by which Widgets this StatelessWidget is composed.
     * This method is run only once when the widget is created.
     * Any changes in the content of this widget will be discarded.
     * @return a Widget describing how this should be drawn on screen
     */
    protected abstract Widget build();
}
