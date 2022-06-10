package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.responses.ErrorCode;

import java.util.*;

/**
 * A class that give the possibility to internationalize the application
 */
public class Translator {

    /**
     * An enum representing the supported languages of the application
     */
    public enum Language{
        ITALIANO(List.of("italiano", "it", "ita", "italian")),
        ENGLISH(List.of("english", "en", "eng", "inglese", "ing"));

        /**
         * The possible lower-case strings that correspond to this language
         */
        private final Collection<String> codes;
        Language(Collection<String> codes){
            this.codes = codes;
        }

        /**
         * Gets a language from the specified code.
         * If the code does not correspond to any language, it defaults to English.
         * @param code the code of the language
         * @return the language corresponding to the code
         */
        public static Language fromCode(String code){
            if (ITALIANO.codes.contains(code.toLowerCase(Locale.ROOT)))
                return ITALIANO;
            return ENGLISH;
        }

        public Collection<String> getCodes(){
            Collection<String> allCodes = new ArrayList<>();
            for (String code : codes){
                allCodes.add(code);
                allCodes.add(code.toUpperCase(Locale.ROOT));
            }
            return Collections.unmodifiableCollection(allCodes);
        }

    }

    /**
     * If the selected language is italian.
     */
    private static boolean isItalian = false;

    private static final String tabHelpIT = "\nPremi TAB per avere dei suggerimenti";
    private static final String tabHelpEn = "\nPress TAB to see some suggestions";

    public static void setLanguage(Language language){
        isItalian = language == Language.ITALIANO;
    }

    public static String getGameSubtitle(){
        return isItalian ? "Un magico mondo di isole volanti!" : "A magic world of floating islands!";
    }

    public static String getChooseLanguage(){
        return isItalian ? "Scegli una lingua" : "Choose a language";
    }

    public static String getWaitMessage(){
        return isItalian ? "Per favore aspetta" : "Please wait";
    }
    public static String getConfirmExit(){
        return isItalian ? "Sei sicuro di voler uscire dal gioco? [yes/no]"
                : "Are you sure you want to exit the game? [yes/no]";
    }
    public static String getChooseIP(){
        return isItalian ? "Scegli l'indirizzo IP del server" : "Choose the IP address of the server";
    }

    public static String getChoosePort(){
        return isItalian ? "Scegli la porta del server" : "Choose the port of the server";
    }

    public static String getExit(){
        return isItalian ? "Esci" : "Exit";
    }

    public static String getChooseHomeAction(){
        return isItalian ? "Cosa vuoi fare?" + tabHelpIT : "What do you want to do?" + tabHelpEn ;
    }

    public static String getCreateGame(){
        return isItalian ? "Crea una nuova partita" : "Create new game";
    }

    public static String getJoinGame(){
        return isItalian ? "Unisciti a una partita" : "Join a game";
    }

    public static String getResumeGame(){
        return isItalian ? "Riprendi una partita" : "Resume a game";
    }

    public static String getChooseNumPlayers(){
        return isItalian ? "Scegli il numero di giocatori" : "Choose the number of players";
    }

    public static String getChooseDifficulty(){
        return isItalian ? "Scegli la difficoltà" : "Choose the difficulty";
    }

    public static String getChooseGame(){
        return isItalian ? "Scegli una partita"+tabHelpIT : "Choose a game"+tabHelpEn;
    }

    public static String getNoGamesToJoin(){
        return isItalian ? "Non ci sono partite a cui ti puoi unire" : "There are no games you can join";
    }

    public static String getNickname(){
        return isItalian ? "Per favore scegli un nickname" : "Please provide a nickname";
    }

    public static String getIslandName(){
        return isItalian ? "Isola#" : "Island#";
    }

    // ****************** INPUT READER *************************
    public static String getWrongInputErrorMessage(){
        return isItalian ? "Il comando inserito non è corretto.":"The command is not correct.";
    }

    // ****************** CLI SCREENs *************************

    // GENERAL
    public static String getMessageToExit(){
        return isItalian ? "esci" : "exit";
    }

    // PLANNING PAHSE
    public static String getPlanningPhaseName(){
        return isItalian ? "FASE DI PIANIFICAZIONE": "PLANNING PHASE";
    }

    public static String getMessagePlanningPhase(){
        return isItalian ?
                "inserisci il numero della carta assistente che vuoi usare" :
                "enter the number of the assistant card to play";
    }

    // ACTION PHASE: MOVE STUDENTS
    public static String getMoveStudentsPhaseName(){
        return isItalian ?
                "FASE D'AZIONE: spostamento degli studenti, scegliere lo studente da muovere":
                "ACTION PHASE: move students, choose the student to move";
    }
    public static String getMessageMoveStudentsPhase(){
        return isItalian ?
                "inserisci il colore dello studente da muovere seguito dalla destinazione \n" +
                "Esempio:\n" +
                "se vuoi muovere uno studente blu sull'isola 1 scrivi: \"blu Isola#1\"\n" +
                "oppure se vuoi muovere uno studente verde nella sala scrivi \" verde Sala\"" :

                "insert the color of the student to move followed by the destination \n" +
                "Example:\n" +
                "if you want to move the blue student on the island 1 insert: \"blue Island#1\"\n" +
                "or if you want to move the green student to the dining room insert: \"green Dining_Room\"";
    }

    public static String getDiningRoomLocationName(){
        return isItalian ? "Sala":"Dining_Room";
    }

    public static List<String> getColor(){
        return isItalian ?
                new ArrayList<>(List.of("blu", "verde", "giallo", "rosso", "rosa")) :
                new ArrayList<>(List.of("blue", "green", "yellow", "red", "pink"));
    }

    // ACTION PHASE: MOVE MOTHER NATURE
    public static String getMoveMotherNaturePhaseName(){
        return isItalian? "FASE D'AZIONE: Spostamento di madre natura": "ACTION PHASE: move mother nature";
    }
    public static String getMessageMoveMotherNaturePhase(){
        return isItalian ?
                "inserisci il numero di passi che vuoi far fare a madre natura" :
                "insert the number of step to move mother nature";
    }

    // ACTION PHASE: CHOOSE CLOUD
    public static String getMessageChooseCloudPhase(){
        return isItalian ?
                "inserisci l'ID della nuvola da cui vuoi prendere gli studenti" :
                "insert the ID of the cloud from which take the students";
    }

    // END OF GAME
    public static String getEndGamePhaseName(){
        return isItalian ? "FINE DEL GIOCO" : "END OF THE GAME";
    }

    public static String getMessageChooseEndPhase(){
        return isItalian ?
                "inserisci \"esci\" per uscire dal gioco":
                "insert \"exit\" to exit from the game";
    }

    public static String getMessageForTheWinner(){
        return isItalian ?
                "congratulazioni, hai vinto la partita!":
                "congratulation, you have won the game!";
    }

    public static String getMessageForTheLosers(){
        return isItalian ? "ha vinto la partita":"has won the game";
    }

    public static String getMessageForParity(){
        return isItalian ?
                "la partita è finita in parità, i vincitori sono":
                "the game ended in a draw, the winners are";
    }

    // ****************** WIDGETS *************************

    // ASSISTANT CARD
    public static String getValueFieldAssistantCard(){
        return isItalian ? "valore: ":"value :";
    }

    public static String getRangeOfMotionFieldAssistantCard(){
        return isItalian ? "range di movimento: ":"range of motion: ";
    }

    // CLOUD SETS
    public static String getHeaderNameOfClouSet(){
        return isItalian ? "NUVOLE":"CLOUDS";
    }

    // CLOUD VIEW
    public static String getCloudNamePrefixCloudView(){
        return isItalian ? "NUVOLA ":"CLOUD ";
    }

    // COIN COUNTER
    public static String getCoinCounterHeader(){
        return isItalian ? "MONETE":"COINS";
    }

    // DINING ROOM
    public static String getDiningRoomViewHeader(){
        return isItalian ? "SALA":"DINING ROOM";
    }

    // ENTRANCE
    public static String getEntranceHeader(){
        return isItalian ? "INGRESSO":"ENTRANCE";
    }

    // PROF TABLE
    public static String getProfTableHeader(){
        return isItalian ? "TAVOLO DEI PROFESSORI":"PROFESSOR TABLE";
    }

    // SCHOOLBOARD LIST
    public static String getSchoolBoardListHeader(){
        return isItalian ? "Scuole":"School boards";
    }

    // SCHOOLBOARD VIEW
    public static String getSchoolBoardViewHeader(){
        return isItalian ? "Scuola":"School board";
    }

    // TABLE
    public static String getHeaderOfTable(){
        return isItalian ? "TAVOLO DI GIOCO" : "GAME TABLE";
    }

    public static String getPlayerDeckName(){
        return isItalian ? "MAZZO DI CARTE ASSISTENTE" : "DECK OF ASSISTANT CARDS";
    }

    public static String getCardUsedDeckName(){
        return isItalian ? "CARTE ASSISTENTE GIOCATE" : "ASSISTANTS USED";
    }

    // TOWER LOCATION
    public static String getTowerLocationHeader(){
        return isItalian ? "TORRI":"TOWERS";
    }

    // MESSAGES
    public static String getLastRoundMessage() {
        return  isItalian ? "Questo è l'ultimo round":"This is the last round";
    }

    public static String getNumOfPlayers(){
        return isItalian ? "Numero di giocatori: " : "Number of players: ";
    }
    public static String getDisplayDifficulty(boolean isExpert){
        return isItalian ? "Difficoltà: " + (isExpert ? "per esperti" : "normale") :
                "Difficulty: " + (isExpert ? "expert mode" : "standard");
    }

    // ****************** MANAGEMENT OF ERROR MESSAGES *************************

    /**
     * Gets a description of the error occurred passed as a parameter
     * @param errorCode the code of the error
     * @return a string describing the error
     */
    public static String getErrorMessage(ErrorCode errorCode){
        return isItalian ? getErrorMessageIT(errorCode) : getErrorMessageEN(errorCode);
    }

    private static String getErrorMessageIT(ErrorCode errorCode){
        return switch (errorCode) {
            case NONE -> "";
            case GENERIC_INVALID_ARGUMENT -> "C'è stato un problema nell'eseguire la richiesta.\n" +
                    "Controlla i parametri e riprova.";
            case GENERIC_INVALID_OPERATION -> "Non lo puoi fare ora!";
            case GAME_NOT_EXIST -> "La partita richiesta non esiste! Per favore riprova.";
            case GAME_IS_FULL -> "La partita selezionata ha già raggiunto il numero massimo di giocatori!";
            case NICKNAME_TAKEN -> "Il nickname scelto è già usato!";
            case NUMBER_PLAYERS_NOT_SUPPORTED ->
                    "Il numero di giocatori scelto non è supportato!\nSono consentiti 2 o 3 giocatori.";
            case PLAYER_NOT_IN_TURN -> "Non è il tuo turno!";
            case TOWER_NOT_AVAILABLE -> "La torre scelta non è disponibile!";
            case WIZARD_NOT_AVAILABLE -> "Il mago scelto non è disponibile!";
            case ASSISTANT_NOT_EXIST -> "Non hai la carta assistente scelta nel tuo mazzo!";
            case ASSISTANT_NOT_USABLE -> "La carta assistente scelta è già in uso da un altro giocatore, " +
                    "quindi non la puoi usare anche tu!";
            case STUDENT_NOT_PRESENT -> "Lo studente scelto non è presente nella posizione indicata!";
            case ISLAND_NOT_EXIST -> "L'isola scelta non esiste!";
            case DININGROOM_FULL -> "Non è possibile aggiungere un altro studente del tipo scelto alla sala da pranzo "
                    + "perché il tavolo corrispondente è già pieno!";
            case MN_MOVEMENT_WRONG -> "Il movimento specificato per Madre Natura non è corretto!\n"+
                    "Assicurati che il valore inserito sia compreso tra zero e il numero riportato sulla tua carta assistente";
            case CLOUD_EMPTY -> "La nuvola scelta è vuota!";
            case CLOUD_NOT_EXIST -> "La nuvola scelta non esiste!";
            case CHARACTER_CARD_NOT_EXIST -> "La carta scelta non esiste!";
            case CHARACTER_CARD_EXPENSIVE -> "Non hai abbastanza monete per usare la carta scelta!";
            case CHARACTER_CARD_ALREADY_USED -> "Puoi usare una sola carta personaggio per turno!";
            case NO_BANS_ON_CARD -> "Non puoi usare questa carta, non ci sono divieti sopra!";
        };
    }

    private static String getErrorMessageEN(ErrorCode errorCode){
        return switch (errorCode) {
            case NONE -> "";
            case GENERIC_INVALID_ARGUMENT -> "An error occurred while processing the request.\n" +
                    "Check all the parameters and try again.";
            case GENERIC_INVALID_OPERATION -> "You can't do this right now!";
            case GAME_NOT_EXIST -> "The selected game doesn't exist. Please try again.";
            case GAME_IS_FULL -> "The selected game already reached the maximum amount of players!";
            case NICKNAME_TAKEN -> "The chosen nickname is already taken!";
            case NUMBER_PLAYERS_NOT_SUPPORTED ->
                    "The new number of players is not supported!\nPossible values are 2 or 3.";
            case PLAYER_NOT_IN_TURN -> "It's not your turn!";
            case TOWER_NOT_AVAILABLE -> "The selected tower is not available!";
            case WIZARD_NOT_AVAILABLE -> "The selected wizard is not available!";
            case ASSISTANT_NOT_EXIST -> "You don't have the specified assistant card in your deck!";
            case ASSISTANT_NOT_USABLE -> "The selected assistant is already chosen by another player, therefore " +
                    "you cannot use it!";
            case STUDENT_NOT_PRESENT -> "The student chosen is not present in the selected location!";
            case ISLAND_NOT_EXIST -> "The specified island doesn't exist!";
            case DININGROOM_FULL -> "A student of the selected type can't be added to the dining room since " +
                    "the corresponding table is already full!";
            case MN_MOVEMENT_WRONG -> "The specified movement for Mother Nature is not correct!\n" +
                    "Check that the value is between zero and the number on your assistant card.";
            case CLOUD_EMPTY -> "The selected cloud is empty!";
            case CLOUD_NOT_EXIST -> "The selected cloud doesn't exist!";
            case CHARACTER_CARD_NOT_EXIST -> "The card selected doesn't exist!";
            case CHARACTER_CARD_EXPENSIVE -> "You don't have enough coins to use the selected card!";
            case CHARACTER_CARD_ALREADY_USED -> "You can use only one character card per turn!";
            case NO_BANS_ON_CARD -> "This card cannot be used, there are no bans on it!";
        };
    }
}

