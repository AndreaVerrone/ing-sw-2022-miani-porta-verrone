package it.polimi.ingsw.client.view.cli.fancy_cli.widgets;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Alignment;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * A widget that arranges its children in a 3x3 grid,
 * with each cell having the same width and height
 */
public class GridView extends Widget{

    /**
     * The possible position of the {@code GridView}.
     */
    public enum Position{
        TOP_LEFT,
        TOP_CENTER,
        TOP_RIGHT,
        LEFT,
        CENTER,
        RIGHT,
        BOTTOM_LEFT,
        BOTTOM_CENTER,
        BOTTOM_RIGHT
    }

    /**
     * The children of this grid
     */
    private final Map<Position, Widget> widgetMap = new HashMap<>(9, 1);

    /**
     * The alignment of a child in the cell
     */
    private final Alignment alignment;

    /**
     * The width of each cell
     */
    private int cellWidth;
    /**
     * The height od each cell
     */
    private int cellHeight;

    /**
     * Creates a new empty 3x3 grid aligning each child in the cell it occupies
     * accordingly to the selected alignment
     * @param alignment the alignment of each child in a cell
     */
    public GridView(Alignment alignment){
        this.alignment = alignment;
    }

    /**
     * Creates a new empty 3x3 grid centering each child in the cell it occupies
     */
    public GridView(){
        this(Alignment.CENTER);
    }

    @Override
    protected void setCanvas(Canvas canvas) {
        super.setCanvas(canvas);
        for (Widget child : widgetMap.values())
            child.setCanvas(canvas);
    }

    /**
     * Adds a widget in the specified position of this grid. If a widget was already
     * in that position, this will be replaced with the one provided.
     * @param child the child to add to this grid
     * @param position the position in which add the child
     * @return the grid itself
     */
    public GridView addChild(Widget child, Position position){
        Widget previous = widgetMap.get(position);
        if (previous != null) {
            previous.detachListener();
            previous.setCanvas(null);
        }
        widgetMap.put(position, child);
        calculateCellDimension();
        child.onSizeChange(this::calculateCellDimension);
        child.setCanvas(getCanvas());
        update();
        return this;
    }

    @Override
    protected void display() {
        if (widgetMap.isEmpty())
            return;
        Column column = new Column();
        int j = 0;
        for (int i = 0; i < 3; i++) {
            Row row = new Row();
            for (; j < (i+1)*3; j++) {
                Widget element = widgetMap.get(Position.values()[j]);
                Widget cell = new SizedBox(
                        Objects.requireNonNullElseGet(element, () -> new Text(" ")),
                        cellWidth, cellHeight, alignment);
                row.addChild(cell);
            }
            column.addChild(row);
        }
        column.setStartingPoint(getStartingPoint());
        column.setBgColor(getBgColor());
        column.setCanvas(getCanvas());
        column.show();
    }

    private void calculateCellDimension(){
        var highestChild = widgetMap.values().stream().max(Comparator.comparingInt(Widget::getHeight));
        cellHeight = highestChild.map(Widget::getHeight).orElse(0);

        var widestChild = widgetMap.values().stream().max(Comparator.comparingInt(Widget::getWidth));
        cellWidth = widestChild.map(widget -> widget.getWidth()+2).orElse(0);

        setWidth(cellWidth*3);
        setHeight(cellHeight*3);
    }

}
