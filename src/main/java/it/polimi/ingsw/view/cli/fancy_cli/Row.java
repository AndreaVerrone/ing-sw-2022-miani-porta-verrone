package it.polimi.ingsw.view.cli.fancy_cli;

import it.polimi.ingsw.view.cli.fancy_cli.utils.ConsoleCli;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A widget to show other widgets one next to each other
 */
public class Row extends Widget{

    /**
     * The widgets in this row
     */
    private final Collection<Widget> children = new ArrayList<>();

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
    }

    /**
     * Adds a widget to this row
     * @param child the widget to add
     * @return the row itself
     */
    public Row addChild(Widget child){
        children.add(child);
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
    }
}
