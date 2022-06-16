package it.polimi.ingsw.client.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;

/**
 * A class used to create new customized widgets that dynamically change over time.
 * When possible this should be replaced by {@link StatelessWidget}
 */
public abstract class StatefulWidget extends Widget{

    /**
     * The content of this widget
     */
    private Widget content;

    /**
     * If the content of this widget is changed from the previous build
     */
    private boolean dirty = true;

    /**
     * Creates a new widget with content that change over time
     */
    protected StatefulWidget(){
        create();
    }

    /**
     * A method that creates this widget, using the build method provided.
     * This should be used at the end of the constructor if some attributes
     * are added at the widget. Not doing that can cause the widget to not
     * render properly.
     */
    protected final void create() {
        dirty = true;
        try {
            updateContent();
            content.onSizeChange(this::updateContent);
        } catch (NullPointerException ignore) {}
    }

    @Override
    void setCanvas(Canvas canvas) {
        super.setCanvas(canvas);
        if (content != null)
            content.setCanvas(canvas);
    }

    @Override
    final void setBgColor(Color backgroundColor) {
        content.setBgColor(backgroundColor);
    }

    private void updateContent(){
        content = getContent();
        if (content == null)
            return;
        setHeight(content.getHeight());
        setWidth(content.getWidth());
    }

    /**
     * A method to be called every time the state of this stateful widget must change.
     * This will also rebuild the widgets and show on the screen.
     * @param runnable a function that describes how the state must change
     */
    protected final void setState(Runnable runnable){
        runnable.run();
        dirty = true;
        updateContent();
        update();
    }

    private Widget getContent(){
        if (dirty){
            Widget widget = build();
            if (widget != null)
                dirty = false;
            return widget;
        }
        return content;
    }

    @Override
    final void display() {
        content = getContent();
        if (content == null)
            return;
        content.setCanvas(getCanvas());
        content.setStartingPoint(getStartingPoint());
        content.show();
    }

    /**
     * A method used to define by which Widgets this StatefulWidget is composed.
     * This method is run every time something in the content change or when it should be
     * displayed, so no heavy processes should be done inside it.
     * @return a Widget describing how this should be drawn on screen
     */
    protected abstract Widget build();
}
