package it.polimi.ingsw.view.cli.fancy_cli.inputs;

import it.polimi.ingsw.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.view.cli.fancy_cli.utils.ConsoleCli;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.*;
import org.jline.reader.impl.completer.NullCompleter;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.util.Scanner;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 * A class used to read inputs from the console. Validators and Completer can be added to
 * suggest the user which command he can use and check if these respect a decided pattern.
 */
public class InputReader {

    private static final Predicate<String> identityValidator = s -> false;
    /**
     * The validator that checks if the input matches a provided pattern.
     */
    private Predicate<String> validator = identityValidator;

    /**
     * A generic message shown when the input is not correct
     */
    private static final String errorMessage = Color.RED + "The command is not correct.";

    /**
     * The completer that suggests the commands to the user
     */
    private Completer completer = NullCompleter.INSTANCE;

    /**
     * Shows a message and reads an input from the user until he types
     * a correct command based on the possible validator of this reader
     * @param request the message to show to the user
     * @return an array containing the command and arguments that the user typed
     * @throws UserRequestExitException if the user request to close the program (i.e. with CTRL+C)
     */
    public String[] readInput(String request) throws UserRequestExitException {
        try (Terminal terminal = TerminalBuilder.terminal()){
            LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).completer(completer).build();
            return readInput(lineReader, request);
        } catch (IOException e){
            //If terminal can't be opened, use Scanner
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println(request);
                String line = scanner.nextLine();
                if (isValid(line)) {
                    scanner.close();
                    return line.split(" ");
                }
                showErrorMessage();
            }
        }
    }

    private String[] readInput(LineReader lineReader, String request) throws UserRequestExitException {
        while (true) {
            System.out.println(request);
            String line;
            try {
                line = lineReader.readLine();
            } catch (UserInterruptException | EndOfFileException e){
                throw new UserRequestExitException();
            }
            if (isValid(line))
                return line.split(" ");
            showErrorMessage();
        }
    }

    private boolean isValid(String input){
            if (validator == identityValidator)
                return true;
            return validator.test(input);
    }

    /**
     * Adds a validator to this InputReader. This validator is logically-ORed with the one previously there,
     * so failing this validator does not guarantee to not accept the input.
     * @param regex the regex representing the validation to apply
     */
    public void addCommandValidator(String regex){
        try {
            Pattern.compile(regex);
        } catch (PatternSyntaxException e){
            return;
        }
        Predicate<String> predicate = s -> s.matches(regex);
        validator = validator.or(predicate);
    }

    /**
     * Adds a completer to this InputReader used to show the user suggestions of possible commands based
     * on the characters he has already written.
     * @param completer the completer to add
     */
    public void addCompleter(Completer completer){
        this.completer = completer;
    }

    /**
     * Shows an error message on the console, with the additional info at the bottom of it.
     * @param additionalInfo additional info to display
     */
    public void showErrorMessage(String additionalInfo){
        AnsiConsole.systemInstall();
        System.out.print(Color.RED+errorMessage);
        if (!additionalInfo.isEmpty())
            System.out.print("\n"+additionalInfo);
        ConsoleCli.resetStyle();
        System.out.print("\n");
        AnsiConsole.systemUninstall();
    }

    /**
     * Shows a generic error message in the console
     */
    public void showErrorMessage(){
        showErrorMessage("");
    }
}
