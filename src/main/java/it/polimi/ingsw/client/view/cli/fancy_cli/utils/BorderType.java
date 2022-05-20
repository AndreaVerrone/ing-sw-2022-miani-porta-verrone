package it.polimi.ingsw.client.view.cli.fancy_cli.utils;

/**
 * The style of the border
 */
public enum BorderType {

    /**
     * <pre>
     *                   ┌──┐
     *  A border like:   │  │
     *                   └──┘
     * </pre>
     */
    SINGLE(Icons.BOX_BORDER_SINGLE_TOP_LEFT,
            Icons.BOX_BORDER_SINGLE_TOP_RIGHT,
            Icons.BOX_BORDER_SINGLE_BOTTOM_LEFT,
            Icons.BOX_BORDER_SINGLE_BOTTOM_RIGHT,
            Icons.BOX_BORDER_SINGLE_HORIZONTAL,
            Icons.BOX_BORDER_SINGLE_VERTICAL),
    /**
     * <pre>
     *                   ╔══╗
     *  A border like:   ║  ║
     *                   ╚══╝
     * </pre>
     */
    DOUBLE(Icons.BOX_BORDER_DOUBLE_TOP_LEFT,
            Icons.BOX_BORDER_DOUBLE_TOP_RIGHT,
            Icons.BOX_BORDER_DOUBLE_BOTTOM_LEFT,
            Icons.BOX_BORDER_DOUBLE_BOTTOM_RIGHT,
            Icons.BOX_BORDER_DOUBLE_HORIZONTAL,
            Icons.BOX_BORDER_DOUBLE_VERTICAL),
    /**
     * <pre>
     *                   ╓──╖
     *  A border like:   ║  ║
     *                   ╙──╜
     * </pre>
     */
    DOUBLE_VERTICAL(Icons.BOX_BORDER_DOUBLE_VERTICAL_TOP_LEFT,
            Icons.BOX_BORDER_DOUBLE_VERTICAL_TOP_RIGHT,
            Icons.BOX_BORDER_DOUBLE_VERTICAL_BOTTOM_LEFT,
            Icons.BOX_BORDER_DOUBLE_VERTICAL_BOTTOM_RIGHT,
            Icons.BOX_BORDER_SINGLE_HORIZONTAL,
            Icons.BOX_BORDER_DOUBLE_VERTICAL),
    /**
     * <pre>
     *                   ╒══╕
     *  A border like:   │  │
     *                   ╘══╛
     * </pre>
     */
    DOUBLE_HORIZONTAL(Icons.BOX_BORDER_DOUBLE_HORIZONTAL_TOP_LEFT,
            Icons.BOX_BORDER_DOUBLE_HORIZONTAL_TOP_RIGHT,
            Icons.BOX_BORDER_DOUBLE_HORIZONTAL_BOTTOM_LEFT,
            Icons.BOX_BORDER_DOUBLE_HORIZONTAL_BOTTOM_RIGHT,
            Icons.BOX_BORDER_DOUBLE_HORIZONTAL,
            Icons.BOX_BORDER_SINGLE_VERTICAL)
    ;

    /**
     * The top left segment
     */
    private final Icons topLeft;
    /**
     * The top right segment
     */
    private final Icons topRight;
    /**
     * The bottom left segment
     */
    private final Icons bottomLeft;
    /**
     * The bottom right segment
     */
    private final Icons bottomRight;
    /**
     * The horizontal segment
     */
    private final Icons horizontal;
    /**
     * The vertical segment
     */
    private final Icons vertical;

    BorderType(Icons topLeft, Icons topRight, Icons bottomLeft, Icons bottomRight, Icons horizontal, Icons vertical) {
        this.topLeft = topLeft;
        this.topRight = topRight;
        this.bottomLeft = bottomLeft;
        this.bottomRight = bottomRight;
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    public Icons getTopLeft() {
        return topLeft;
    }

    public Icons getTopRight() {
        return topRight;
    }

    public Icons getBottomLeft() {
        return bottomLeft;
    }

    public Icons getBottomRight() {
        return bottomRight;
    }

    public Icons getHorizontal() {
        return horizontal;
    }

    public Icons getVertical() {
        return vertical;
    }
}
