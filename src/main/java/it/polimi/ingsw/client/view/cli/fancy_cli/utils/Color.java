package it.polimi.ingsw.client.view.cli.fancy_cli.utils;

/**
 * The color that can be applied to be displayed
 */
@SuppressWarnings("MissingJavadoc")
public enum Color {
    DEFAULT(""),
    BLACK(30),
    WHITE(97),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    MAGENTA(35),
    CYAN(36),
    BRIGHT_GREY(37),
    GREY(90),
    BRIGHT_RED(91),
    BRIGHT_GREEN(92),
    BRIGHT_YELLOW(93),
    BRIGHT_BLUE(94),
    BRIGHT_MAGENTA(95),
    BRIGHT_CYAN(96);

    public final String foreground;
    public final String background;

    Color(int code){
        String base = "\u001b[";
        foreground = base + code + "m";
        background = base + (code+10) + "m";
    }

    Color(String s){
        foreground = s;
        background = s;
    }

    @Override
    public String toString() {
        return foreground;
    }
}
