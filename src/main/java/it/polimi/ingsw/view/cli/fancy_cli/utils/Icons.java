package it.polimi.ingsw.view.cli.fancy_cli.utils;

public enum Icons {
    ;

    private final String code;

    Icons(String code){
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
