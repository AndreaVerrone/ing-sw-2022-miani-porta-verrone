package it.polimi.ingsw.client.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.ConsoleCli;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * A widget to show other widgets one next to each other
 */
public class Row extends Widget{

    /**
     * The widgets in this row
     */
    private final List<Widget> children = new ArrayList<>();

    /**
     * Creates an empty row
     */
    public Row(){}

    /**
     * Creates a row containing the passed widgets
     * @param children the widgets to add to this row
     */
    public Row(Collection<Widget> children){
        this.children.addAll(children);
        calculateDimensions();
        for (Widget child : children){
            child.onSizeChange(this::calculateDimensions);
        }
    }

    private void calculateDimensions(){
        int width = children.stream().reduce(0, (integer, widget) -> integer+widget.getWidth(), Integer::sum);
        int height = children.stream().max(Comparator.comparingInt(Widget::getHeight))
                .map(Widget::getHeight).orElse(0);
        setWidth(width);
        setHeight(height);
    }

    @Override
    protected void setCanvas(Canvas canvas) {
        super.setCanvas(canvas);
        for (Widget child : children)
            child.setCanvas(canvas);
    }

    @Override
    void setBgColor(Color backgroundColor) {
        super.setBgColor(backgroundColor);
        for (Widget child : children)
            child.setBgColor(backgroundColor);
    }

    /**
     * Adds a widget to this row
     * @param child the widget to add
     * @return the row itself
     */
    public Row addChild(Widget child){
        children.add(child);
        calculateDimensions();
        child.onSizeChange(this::calculateDimensions);
        child.setCanvas(getCanvas());
        child.setBgColor(getBgColor());
        update();
        return this;
    }

    /**
     * Removes the last widget in this row
     * @return the row itself
     */
    public Row removeLast(){
        Widget toRemove = children.remove(children.size()-1);
        toRemove.setCanvas(null);
        toRemove.detachListener();
        return this;
    }

    @Override
    protected void display() {
        int start = getStartingPoint();
        for (Widget child:children) {
            child.setStartingPoint(start);
            child.show();
            start += child.getWidth();
            ConsoleCli.moveCursorUp(child.getHeight()-1);
        }
        ConsoleCli.moveCursorDown(getHeight()-1);
    }
}
