package it.polimi.ingsw.client.view.cli.fancy_cli.widgets;

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
     * Creates a new widget with content that change over time
     */
    protected StatefulWidget(){
        updateContent();
        content.onSizeChange(this::updateContent);
    }

    private void updateContent(){
        content = build();
        if (content == null)
            return;
        setHeight(content.getHeight());
        setWidth(content.getWidth());
    }

    @Override
    void display() {
        content = build();
        if (content == null)
            return;
        content.setCanvas(getCanvas());
        content.setStartingPoint(getStartingPoint());
        content.display();
    }

    /**
     * A method used to define by which Widgets this StatefulWidget is composed.
     * This method is run every time something in the content change or when it should be
     * displayed, so no heavy processes should be done inside it.
     * @return a Widget describing how this should be drawn on screen
     */
    protected abstract Widget build();
}
