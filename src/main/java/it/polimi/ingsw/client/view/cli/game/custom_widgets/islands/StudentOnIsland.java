package it.polimi.ingsw.client.view.cli.game.custom_widgets.islands;

import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.StatelessWidget;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Text;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Widget;
import it.polimi.ingsw.server.model.utils.PawnType;

/**
 * A widget used to represent the number of students of a color on an island
 */
public class StudentOnIsland extends StatelessWidget {

    /**
     * The widget representing the number of students
     */
    private final Text student;

    /**
     * Creates a representation of the number of students of a particular type on an island
     * @param type the type of students
     * @param num the number of students of that type
     */
    StudentOnIsland(PawnType type, int num){
        student = new Text(String.format("%02d", num));
        setColors(type);
        create();
    }

    @Override
    protected Widget build() {
        return student;
    }

    private void setColors(PawnType type){
        switch (type){
            case YELLOW_GNOMES -> {
                student.setForegroundColor(Color.BLACK);
                student.setBackgroundColor(Color.BRIGHT_YELLOW);
            }
            case BLUE_UNICORNS -> {
                student.setForegroundColor(Color.WHITE);
                student.setBackgroundColor(Color.BLUE);
            }
            case GREEN_FROGS -> {
                student.setForegroundColor(Color.BLACK);
                student.setBackgroundColor(Color.BRIGHT_GREEN);
            }
            case RED_DRAGONS -> {
                student.setForegroundColor(Color.WHITE);
                student.setBackgroundColor(Color.RED);
            }
            case PINK_FAIRIES -> {
                student.setForegroundColor(Color.BLACK);
                student.setBackgroundColor(Color.BRIGHT_MAGENTA);
            }
        }
    }
}
