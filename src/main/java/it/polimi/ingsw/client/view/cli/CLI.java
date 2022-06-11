package it.polimi.ingsw.client.view.cli;

import it.polimi.ingsw.client.ClientController;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.reduced_model.ReducedCloud;
import it.polimi.ingsw.client.reduced_model.ReducedIsland;
import it.polimi.ingsw.client.reduced_model.ReducedSchoolBoard;
import it.polimi.ingsw.client.reduced_model.TableRecord;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.Validator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.ConsoleCli;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.Table;
import it.polimi.ingsw.network.VirtualView;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.TowerType;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.EnumCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.*;

/**
 * A class to handle the client ui in the console
 */
public class CLI implements VirtualView, Runnable {

    /**
     * The controller of the client of this view
     */
    private ClientController clientController;

    /**
     * The current screen that must be shown to the client
     */
    private CliScreen currentScreen = new IdleScreen(this);

    /**
     * The next screen that must be shown to the client
     */
    private CliScreen nextScreen;

    /**
     * the table of the game
     */
    Table table;

    public Table getTable() {
        return table;
    }

    public void setTable(TableRecord tableRecord) {
         this.table = new Table(tableRecord);

        // todo: testing code
        //  <-- from here
        /*
        StudentList stud = new StudentList();
        try {
            stud.changeNumOf(PawnType.BLUE_UNICORNS,3);
            stud.changeNumOf(PawnType.GREEN_FROGS,2);
            stud.changeNumOf(PawnType.PINK_FAIRIES,2);
        } catch (NotEnoughStudentException e) {
            throw new RuntimeException(e);
        }

        StudentList stud2 = new StudentList();
        try {
            stud2.changeNumOf(PawnType.BLUE_UNICORNS,1);
            stud2.changeNumOf(PawnType.GREEN_FROGS,1);
            stud2.changeNumOf(PawnType.RED_DRAGONS,1);
            stud2.changeNumOf(PawnType.PINK_FAIRIES,1);
        } catch (NotEnoughStudentException e) {
            throw new RuntimeException(e);
        }

        Map<String, Assistant> map0 = new HashMap<>();
        map0.put("player 1", Assistant.CARD_9);
        map0.put("player 2", Assistant.CARD_1);
        // map0.put("player 3", Assistant.CARD_3);

        Map<String,StudentList> map = new HashMap<>();
        map.put("player 1",stud);
        map.put("player 2",stud);
        map.put("player 3",stud);

        Map<String, Collection<PawnType>> map2 = new HashMap<>();
        map2.put("player 1", List.of(PawnType.BLUE_UNICORNS,PawnType.GREEN_FROGS));
        map2.put("player 2", List.of(PawnType.GREEN_FROGS));
        map2.put("player 3", List.of());

        Map<String, TowerType> map3 = new HashMap<>();
        map3.put("player 1", TowerType.BLACK);
        map3.put("player 2", TowerType.WHITE);
        map3.put("player 3", TowerType.GREY);

        Map<String, Integer> map4 = new HashMap<>();
        map4.put("player 1", 8);
        map4.put("player 2", 7);
        map4.put("player 3", 6);

        Map<Integer,StudentList> map5 = new HashMap<>();
        map5.put(1,stud);
        map5.put(2,stud2);
        map5.put(3,stud2);

        ReducedIsland r1 = new ReducedIsland(1,stud,TowerType.BLACK,1,0);
        ReducedIsland r2 = new ReducedIsland(2,stud2,TowerType.BLACK,1,2);
        ReducedIsland r3 = new ReducedIsland(3,stud2,null,1,1);
        ReducedIsland r4 = new ReducedIsland(4,stud,TowerType.GREY,1,1);
        ReducedIsland r5 = new ReducedIsland(5,stud2,TowerType.WHITE,1,1);
        ReducedIsland r6 = new ReducedIsland(6,stud2,TowerType.WHITE,1,1);
        ReducedIsland r7 = new ReducedIsland(7,stud2,TowerType.WHITE,1,1);
        ReducedIsland r8 = new ReducedIsland(8,stud2,TowerType.WHITE,1,1);
        ReducedIsland r9 = new ReducedIsland(9,stud2,TowerType.WHITE,1,1);
        ReducedIsland r10 = new ReducedIsland(10,stud2,TowerType.WHITE,1,1);
        ReducedIsland r11 = new ReducedIsland(11,stud2,TowerType.WHITE,1,1);
        ReducedIsland r12 = new ReducedIsland(0,stud2,TowerType.WHITE,1,1);
        Collection<ReducedIsland> reducedIslands1 = new ArrayList<>(List.of(r1,r2,r3,r4,r5,r6,r7,r8,r9,r10,r11,r12));

        /* WITHOUT RECORD
        this.table = new Table(
                List.of(Assistant.CARD_1, Assistant.CARD_9),
                map0,
                //List.of(Assistant.CARD_1, Assistant.CARD_9),
                //List.of(Assistant.values()),
                map5,
                map,
                map,
                map2,
                map3,
                map4,
                map4,
                List.of("player 1", "player 2", "player 3"),
                reducedIslands1
        );//<-- to here */

        /*this.table=new Table(
                new TableRecord(
                    List.of(Assistant.CARD_1, Assistant.CARD_9),
                    map0,
                    List.of(new ReducedCloud(1,stud),new ReducedCloud(2,stud),new ReducedCloud(3,stud)),
                    List.of(
                            new ReducedSchoolBoard(
                                    "player 1",
                                    stud,
                                    List.of(PawnType.BLUE_UNICORNS,PawnType.GREEN_FROGS),
                                    stud,
                                    TowerType.BLACK,0,8
                            ),
                            new ReducedSchoolBoard(
                                    "player 2",
                                    stud,
                                    List.of(PawnType.BLUE_UNICORNS),
                                    stud,
                                    TowerType.GREY,2,0
                            ),
                            new ReducedSchoolBoard(
                                    "player 3",
                                    stud,
                                    List.of(),
                                    stud,
                                    TowerType.WHITE,1,8
                            )
                    ),
                reducedIslands1
                )
        );*/
    }

    /**
     * The title of the application
     */
    public static final String APP_TITLE = """
            ███████╗██████╗ ██╗ █████╗ ███╗   ██╗████████╗██╗   ██╗███████╗
            ██╔════╝██╔══██╗██║██╔══██╗████╗  ██║╚══██╔══╝╚██╗ ██╔╝██╔════╝
            █████╗  ██████╔╝██║███████║██╔██╗ ██║   ██║    ╚████╔╝ ███████╗
            ██╔══╝  ██╔══██╗██║██╔══██║██║╚██╗██║   ██║     ╚██╔╝  ╚════██║
            ███████╗██║  ██║██║██║  ██║██║ ╚████║   ██║      ██║   ███████║
            ╚══════╝╚═╝  ╚═╝╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝   ╚═╝      ╚═╝   ╚══════╝
            """;

    private boolean shouldStop = false;

    @Override
    public void run() {
        while (!shouldStop){
            if (nextScreen == null){
                currentScreen = new IdleScreen(this);
            } else {
                currentScreen = nextScreen;
                nextScreen = null;
            }
            currentScreen.show();
        }
    }

    /**
     * Attach this view to the specified controller, if not already attached to one.
     * @param clientController the controller of the client
     */
    public void attachTo(ClientController clientController){
        if (this.clientController == null)
            this.clientController = clientController;
    }

    public ClientController getClientController(){
        return clientController;
    }

    public void setNextScreen(CliScreen screen){
        nextScreen = screen;
        currentScreen.setStop();
    }

    public Canvas getBaseCanvas(){
        Canvas canvas = new Canvas();
        canvas.setTitle(APP_TITLE);
        canvas.setTitleColor(Color.BRIGHT_CYAN);
        canvas.setSubtitle(Translator.getGameSubtitle());
        return canvas;
    }

    /**
     * Prompt the user to choose in which language he wants to play.
     */
    public void chooseLanguage(){
        InputReader inputReader = new InputReader();
        for (Translator.Language language : Translator.Language.values()){
            for (String code : language.getCodes())
                inputReader.addCommandValidator(code);
        }
        inputReader.addCompleter(new EnumCompleter(Translator.Language.class));
        String language = inputReader.readInput(Translator.getChooseLanguage())[0];
        Translator.setLanguage(Translator.Language.fromCode(language));
    }

    /**
     * Prompt the user to confirm that he want to close the application
     */
    public void confirmExit(){
        InputReader inputReader = new InputReader();
        inputReader.addCompleter(new AggregateCompleter(new StringsCompleter("yes"), new StringsCompleter("no")));
        inputReader.setNumOfArgsValidator(Validator.isOfNum(0));
        String input = inputReader.readInput(Translator.getConfirmExit())[0];
        if (parseBoolean(input)) {
            shouldStop = true;
            currentScreen.setStop();
            clientController.closeApplication();
            return;
        }
        setNextScreen(currentScreen);
    }

    private boolean parseBoolean(String bool){
        return switch (bool.toLowerCase(Locale.ROOT)){
            case "y", "yes", "s", "si" -> true;
            default -> false;
        };
    }

    /**
     * this method will print in red the message passed in the parameters,
     * and it will emit a sound
     * @param errorMessage string containing the error message to print
     */
    public void displayErrorMessage(String errorMessage){
        // print the message in red
        printColorMessage(Color.RED,errorMessage);
        // emit a sound
        System.out.print("\u0007");
    }

    /**
     * this method will print in yellow the message passed in the parameters
     * @param message string containing the message to print
     */
    public void displayMessage(String message){
        printColorMessage(Color.BRIGHT_YELLOW,message);
    }

    /**
     * This method allow to print a colored message
     * @param color the color to use to print the message
     * @param message the message to print
     */
    private void printColorMessage(Color color, String message){
        AnsiConsole.systemInstall();
        System.out.print(color + message);
        ConsoleCli.resetStyle();
        System.out.print("\n");
        AnsiConsole.systemUninstall();
    }
}
