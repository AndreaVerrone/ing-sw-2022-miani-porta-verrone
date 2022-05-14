package it.polimi.ingsw.view.cli.fancy_cli.utils;

@SuppressWarnings("MissingJavadoc")
public enum TextStyle {
    BOLD(1),
    ITALIC(3),
    UNDERLINE(4);

    private final String style;

    TextStyle(int code) {
        this.style = "\u001b[" + code + "m";
    }

    @Override
    public String toString() {
        return style;
    }
}
