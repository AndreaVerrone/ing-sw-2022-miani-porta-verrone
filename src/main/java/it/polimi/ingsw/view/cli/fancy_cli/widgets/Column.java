package it.polimi.ingsw.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.view.cli.fancy_cli.utils.ConsoleCli;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * A widget to show other widgets one above each other
 */
public class Column extends Widget{

    /**
     * The widgets in this column
     */
    private final List<Widget> children = new ArrayList<>();

    /**
     * Creates an empty column
     */
    public Column(){}

    /**
     * Creates a column containing the passed widgets
     * @param children the widgets to add to this column
     */
    public Column(Collection<Widget> children){
        this.children.addAll(children);
        calculateDimensions();
        for (Widget child : children){
            child.onSizeChange(this::calculateDimensions);
        }
    }

    private void calculateDimensions(){
        int width = children.stream().max(Comparator.comparingInt(Widget::getWidth))
                .map(Widget::getWidth).orElse(0);
        int height = children.stream().reduce(0, (integer, widget) -> integer+widget.getHeight(), Integer::sum);
        setWidth(width);
        setHeight(height);
    }

    @Override
    protected void setCanvas(Canvas canvas) {
        super.setCanvas(canvas);
        for (Widget child : children)
            child.setCanvas(canvas);
    }

    /**
     * Adds a widget to this column
     * @param child the widget to add
     * @return the column itself
     */
    public Column addChild(Widget child){
        children.add(child);
        calculateDimensions();
        child.onSizeChange(this::calculateDimensions);
        child.setCanvas(getCanvas());
        update();
        return this;
    }

    /**
     * Removes the last widget in this column
     * @return the column itself
     */
    public Column removeLast(){
        Widget toRemove = children.remove(children.size()-1);
        toRemove.setCanvas(null);
        toRemove.detachListener();
        return this;
    }

    @Override
    protected void display() {
        for (Widget child:children) {
            child.setStartingPoint(getStartingPoint());
            child.show();
            System.out.print("\n");
        }
        ConsoleCli.moveCursorUp(1);
    }
}
