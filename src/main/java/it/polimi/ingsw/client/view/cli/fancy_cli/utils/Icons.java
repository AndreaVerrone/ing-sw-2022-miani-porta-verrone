package it.polimi.ingsw.client.view.cli.fancy_cli.utils;

public enum Icons {

    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *    ─
     *    </font>
     */
    BOX_BORDER_SINGLE_HORIZONTAL("\u2500"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *    │
     *    </font>
     */
    BOX_BORDER_SINGLE_VERTICAL("\u2502"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *    ┌
     *    </font>
     */
    BOX_BORDER_SINGLE_TOP_LEFT("\u250C"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *    ┐
     *    </font>
     */
    BOX_BORDER_SINGLE_TOP_RIGHT("\u2510"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *    └
     *    </font>
     */
    BOX_BORDER_SINGLE_BOTTOM_LEFT("\u2514"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *    ┘
     *    </font>
     */
    BOX_BORDER_SINGLE_BOTTOM_RIGHT("\u2518"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *    ═
     *    </font>
     */
    BOX_BORDER_DOUBLE_HORIZONTAL("\u2550"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ║
     *     </font>
     */
    BOX_BORDER_DOUBLE_VERTICAL("\u2551"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╔
     *     </font>
     */
    BOX_BORDER_DOUBLE_TOP_LEFT("\u2554"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╗
     *     </font>
     */
    BOX_BORDER_DOUBLE_TOP_RIGHT("\u2557"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╚
     *     </font>
     */
    BOX_BORDER_DOUBLE_BOTTOM_LEFT("\u255A"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╝
     *     </font>
     */
    BOX_BORDER_DOUBLE_BOTTOM_RIGHT("\u255D"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╓
     *     </font>
     */
    BOX_BORDER_DOUBLE_VERTICAL_TOP_LEFT("\u2553"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╖
     *     </font>
     */
    BOX_BORDER_DOUBLE_VERTICAL_TOP_RIGHT("\u2556"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╙
     *     </font>
     */
    BOX_BORDER_DOUBLE_VERTICAL_BOTTOM_LEFT("\u2559"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╜
     *     </font>
     */
    BOX_BORDER_DOUBLE_VERTICAL_BOTTOM_RIGHT("\u255C"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╒
     *     </font>
     */
    BOX_BORDER_DOUBLE_HORIZONTAL_TOP_LEFT("\u2552"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╕
     *     </font>
     */
    BOX_BORDER_DOUBLE_HORIZONTAL_TOP_RIGHT("\u2555"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╘
     *     </font>
     */
    BOX_BORDER_DOUBLE_HORIZONTAL_BOTTOM_LEFT("\u2558"),
    /**
     * An icon for:
     * <p>
     * <font size="+2">
     *     ╛
     *     </font>
     */
    BOX_BORDER_DOUBLE_HORIZONTAL_BOTTOM_RIGHT("\u255B"),
    /**
     * An icon for displaying a pink flower emoji
     */
    FLOWER_EMOJI("\uD83C\uDF3A"),
    /**
     * An icon for displaying a ban emoji
     */
    BAN_EMOJI("\uD83D\uDEAB"),

    /**
     * An icon representing a pawn (it can be either a student or a professor)
     */
    PAWN("█"),

    /**
     * An icon representing a coin.
     */
    COIN("●"),

    /**
     * an icon representing the tower
     */
    TOWER("█"),
    ;

    private final String code;

    Icons(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
