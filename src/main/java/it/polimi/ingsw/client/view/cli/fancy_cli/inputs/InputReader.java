package it.polimi.ingsw.client.view.cli.fancy_cli.inputs;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.Color;
import it.polimi.ingsw.client.view.cli.fancy_cli.utils.ConsoleCli;
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

    private static final Predicate<String> identityORValidator = s -> false;
    /**
     * The validator that checks if the input matches all the provided pattern.
     */
    private Predicate<String> strictValidator = s -> true;
    /**
     * The validator to check if the input matches at least one provided pattern
     */
    private Predicate<String> validator = identityORValidator;

    /**
     * The validator to check if the number of arguments are correct.
     */
    private Predicate<Integer> numOfArgsValidator = i -> true;

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
     */
    public String[] readInput(String request) {
        try (Terminal terminal = TerminalBuilder.terminal()){
            LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).completer(completer).build();
            return readInput(lineReader, request);
        } catch (IOException e){
            //If terminal can't be opened, use Scanner
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println(request);
                String line = scanner.nextLine();
                line = line.trim().replaceAll("( )+", " ");
                if (isValid(line)) {
                    scanner.close();
                    return line.split(" ");
                }
                showErrorMessage();
            }
        }
    }

    private String[] readInput(LineReader lineReader, String request) {
        while (true) {
            System.out.println(request);
            String line;
            try {
                line = lineReader.readLine();
            } catch (UserInterruptException | EndOfFileException e){
                continue;
            }
            line = line.trim().replaceAll("( )+", " ");
            if (isValid(line)) {
                String[] inputs = line.split(" ");
                int args = inputs.length - 1;
                if (numOfArgsValidator.test(args))
                    return inputs;
            }
            showErrorMessage();
        }
    }

    private boolean isValid(String input){
        Predicate<String> checker = strictValidator;
        if (validator != identityORValidator)
            checker = checker.and(validator);
        return checker.test(input);
    }

    /**
     * Adds a validator to this InputReader. This validator is logically-ORed with the other not strict ones,
     * so failing this validator does not guarantee to not accept the input.
     * For a more strict validator see
     * {@link #addStrictCommandValidator(Validator)}, {@link #addStrictCommandValidator(String)}.
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
     * Adds a validator to this InputReader. This validator is logically-ORed with the other not strict ones,
     * so failing this validator does not guarantee to not accept the input.
     * For a more strict validator see
     * {@link #addStrictCommandValidator(Validator)}, {@link #addStrictCommandValidator(String)}.
     * @param validator a validator representing the validation to apply
     */
    public void addCommandValidator(Validator<String> validator){
        this.validator = this.validator.or(validator.getPredicate());
    }

    /**
     * Adds a validator to this InputReader. This validator is logically-ANDed with the one previously there,
     * so failing this validator will reject the input.
     * For a less strict validator see {@link #addCommandValidator(Validator)}, {@link #addCommandValidator(String)}.
     * @param regex the regex representing the validation to apply
     */
    public void addStrictCommandValidator(String regex){
        try {
            Pattern.compile(regex);
        } catch (PatternSyntaxException e){
            return;
        }
        Predicate<String> predicate = s -> s.matches(regex);
       strictValidator = strictValidator.and(predicate);
    }

    /**
     * Adds a validator to this InputReader. This validator is logically-ANDed with the one previously there,
     * so failing this validator will reject the input.
     * For a less strict validator see {@link #addCommandValidator(Validator)}, {@link #addCommandValidator(String)}.
     * @param validator a validator representing the validation to apply
     */
    public void addStrictCommandValidator(Validator<String> validator){
        this.strictValidator = this.strictValidator.or(validator.getPredicate());
    }

    /**
     * Sets the validator to check if the number of arguments are correct.
     * Because this checks only the arguments number, the command itself should be
     * excluded from the count.
     * @param numOfArgsValidator the validator to check if the number of arguments of
     *                           a command are correct
     */
    public void setNumOfArgsValidator(Validator<Integer> numOfArgsValidator) {
        this.numOfArgsValidator = numOfArgsValidator.getPredicate();
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
     * It will also emit a sound.
     * @param additionalInfo additional info to display
     */
    public void showErrorMessage(String additionalInfo){
        AnsiConsole.systemInstall();
        System.out.print(Color.RED+errorMessage);
        if (!additionalInfo.isEmpty())
            System.out.print("\n"+additionalInfo);
        ConsoleCli.resetStyle();
        System.out.print("\u0007"); // sound emission
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
