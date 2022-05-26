package it.polimi.ingsw.client.view.cli.game;

import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.InputReader;
import it.polimi.ingsw.client.view.cli.fancy_cli.inputs.Validator;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Border;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Canvas;
import it.polimi.ingsw.client.view.cli.fancy_cli.widgets.Column;
import it.polimi.ingsw.client.view.cli.game.custom_widgets.Deck;
import it.polimi.ingsw.server.model.player.Assistant;
import it.polimi.ingsw.server.observers.LastAssistantUsedObserver;
import org.jline.reader.Completer;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.reader.impl.completer.StringsCompleter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class PlanningPhase {

    private final String phaseName = "Planning Phase";

    private final String myNickname = "Io";

    private String currentPlayerNickName;

    private List<Assistant> cardUsed = new ArrayList<>();

    private List<Assistant> currentPlayerDeck = new ArrayList<>();

    private Canvas canvas = new Canvas();

    public PlanningPhase(String currentPlayerNickName, List<Assistant> cardUsed, List<Assistant> currentPlayerDeck) {
        this.currentPlayerNickName = currentPlayerNickName;
        this.cardUsed = cardUsed;
        this.currentPlayerDeck = currentPlayerDeck;
    }

    public void setCurrentPlayerNickName(String currentPlayerNickName) {
        this.currentPlayerNickName = currentPlayerNickName;
        displayPhase();
    }

    public void setCardUsed(List<Assistant> cardUsed) {
        this.cardUsed = cardUsed;
        displayPhase();
    }

    public void setCurrentPlayerDeck(List<Assistant> currentPlayerDeck) {
        this.currentPlayerDeck = currentPlayerDeck;
        displayPhase();
    }

    public void askCard() {

        /*InputReader inputReader = new InputReader();
        //inputReader.addCommandValidator(Validator.beginsWith("play "));
        inputReader.addCommandValidator("play \\d\\d?");
        inputReader.setNumOfArgsValidator(Validator.isOfNum(1));*/

        InputReader inputReader = new InputReader();
        //inputReader.addCommandValidator("play \\d\\d?");

        //inputReader.setNumOfArgsValidator(Validator.isOfNum(1));

        List<Assistant> assistants = currentPlayerDeck;
        Collection<Completer> completers = new ArrayList<>();
        for (Assistant assistant:  assistants){
            completers.add(new StringsCompleter("play" + " " + String.valueOf(assistant.getValue())));
            inputReader.addCommandValidator("play" + " " +String.valueOf(assistant));
        }
        inputReader.addCompleter(new AggregateCompleter(completers));

        while (true) {
            //try {
                //prompt the user to enter something and reads the input
                String[] inputs = inputReader.readInput("enter \"play\" followed by assistant number to use");
                String s = inputs[1];

                //if the argument of the command is "exit" closes the program
                if (Objects.equals(s, "exit")) {
                    return;
                }

                int numOfAssistant=0;

                try {
                    numOfAssistant=Integer.parseInt(s);
                }catch (NumberFormatException e){
                    continue;
                }


                switch (numOfAssistant) {
                    case 1 -> {
                        notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_1);
                        System.out.println("Sending to server ...");
                        return;
                    }
                    case 2 -> {
                        notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_2);
                        System.out.println("Sending to server ...");
                        return;
                    }
                    case 3 -> {
                        notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_3);
                        System.out.println("Sending to server ...");
                        return;
                    }
                    case 4 -> {
                        notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_4);
                        System.out.println("Sending to server ...");
                        return;
                    }
                    case 5 -> {
                        notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_5);
                        System.out.println("Sending to server ...");
                        return;
                    }
                    case 6 -> {
                        notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_6);
                        System.out.println("Sending to server ...");
                        return;
                    }
                    case 7 -> {
                        notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_7);
                        System.out.println("Sending to server ...");
                        return;
                    }
                    case 8 -> {
                        notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_8);
                        System.out.println("Sending to server ...");
                        return;
                    }
                    case 9 -> {
                        notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_9);
                        System.out.println("Sending to server ...");
                        return;
                    }
                    case 10 -> {
                        notifyLastAssistantUsedObservers(myNickname,Assistant.CARD_10);
                        System.out.println("Sending to server ...");
                        return;
                    }
                    default -> inputReader.showErrorMessage("card not in your deck");
                }
            //} catch (UserRequestExitException e) {
                //return;
            //}

            //manually update the canvas as the Text widget does not anymore respond to updates
            canvas.show();

        }
    }

    public void askAssistantCard(){

        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("play \\d");
        inputReader.setNumOfArgsValidator(Validator.isOfNum(1));

        while (true) {
            //try {
                //prompt the user to enter something and reads the input
                String[] inputs = inputReader.readInput("enter \"play\" followed by assistant number to use");
                String s = inputs[1];

                //if the argument of the command is "exit" closes the program
                if (Objects.equals(s, "exit")) {
                    return;
                }


            //} catch (UserRequestExitException e) {
                //throw new RuntimeException(e);
            //}
        }
    }

    public void askMotherNatureMovement() {

        InputReader inputReader = new InputReader();
        inputReader.addCommandValidator("moveMN \\d|use card \\d|exit");
        //inputReader.setNumOfArgsValidator(Validator.isOfNum(1));

        while (true) {

            //try {
                //prompt the user to enter something and reads the input
                String[] inputs = inputReader.readInput(
                        "- enter \"moveMN\" followed by the number of movements \n" +
                                "- or if you want to use a character card enter \"use card\" followed by the number of the card \n" +
                                "- or enter \"exit\" to exit the game\n");
                String s = inputs[0];


                if (s.equals("moveMN")){
                    // send message to move MN
                    System.out.println("Sending to server MN movement ...");
                    return;
                }

                if(s.equals("use card")){
                    // send message to use card
                    System.out.println("Sending to server card usage ...");
                    return;
                }

                if(s.equals("exit")){
                    // esci dal gioco
                    System.out.println("exiting from game ...");
                    return;
                }

            //} catch (UserRequestExitException e) {
                //throw new RuntimeException(e);
            //}
        }
    }


        public void displayPhase() {

        canvas.setTitle(phaseName);
        canvas.setSubtitle("Current player: " + currentPlayerNickName);

        // DECK DEL CURRENT
        Deck deckCurrentPlayer = new Deck(currentPlayerDeck, "ASSISTANT DECK");
        Deck deckCardUsed = new Deck(cardUsed, "CARD USED");
        Column column = new Column(List.of(deckCardUsed, deckCurrentPlayer));
        Border border = new Border(column);
        canvas.setContent(border);
        canvas.show();

    }

    // MANAGEMENT OF OBSERVERS ON LAST ASSISTANT USED
    /**
     * List of the observer on the assistant deck.
     */
    private final List<LastAssistantUsedObserver> lastAssistantUsedObservers = new ArrayList<>();

    /**
     * This method allows to add the observer, passed as a parameter, on last assistant.
     * @param observer the observer to be added
     */
    public void addLastAssistantUsedObserver(LastAssistantUsedObserver observer){
        lastAssistantUsedObservers.add(observer);
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on last assistant.
     * @param observer the observer to be removed
     */
    public void removeLastAssistantUsedObserver(LastAssistantUsedObserver observer){
        lastAssistantUsedObservers.remove(observer);
    }

    /**
     * This method notify all the attached observers that a change has been happened on last assistant.
     * @param nickName the nickname of the player that has the deck that has been changed
     * @param actualLastAssistant the actual last assistant
     */
    private void notifyLastAssistantUsedObservers(String nickName, Assistant actualLastAssistant){
        for(LastAssistantUsedObserver observer : lastAssistantUsedObservers)
            observer.lastAssistantUsedObserverUpdate(nickName,actualLastAssistant);
    }

}

