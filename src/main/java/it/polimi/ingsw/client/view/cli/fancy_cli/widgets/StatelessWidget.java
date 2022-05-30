package it.polimi.ingsw.client.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;

/**
 * A class used to create new customized widgets that can't change over time.
 * When possible this should be used instead of {@link StatefulWidget} as this is more lightweight.
 */
public abstract class StatelessWidget extends Widget{

    /**
     * The content of this widget
     */
    private Widget content;

    /**
     * Creates a new widget with content that can't change over time
     */
    protected StatelessWidget() {
        create();
    }

    /**
     * A method that creates this widget, using the build method provided.
     * This should be used at the end of the constructor if some attributes
     * are added at the widget. Not doing that can cause the widget to not
     * render properly.
     */
    protected final void create(){
        if (content != null)
            return;
        Widget widget;
        try {
            widget = build();
        } catch (NullPointerException e){
            return;
        }
        if (widget == null)
            return;
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
        if (widget instanceof StatelessWidget)
            return widget;
        return new StatelessWidget() {
            @Override
            protected Widget build() {
                return widget;
            }
        };
    }

    @Override
    final void setBgColor(Color backgroundColor) {
        content.setBgColor(backgroundColor);
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
