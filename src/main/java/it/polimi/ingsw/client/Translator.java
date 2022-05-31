package it.polimi.ingsw.client;

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

    // ****************** CLI SCREENs *************************

    // GENERAL
    public static String getMessageToExit(){
        return isItalian ? "esci" : "exit";
    }

    // PLANING PAHSE
    public static String getPlanningPhaseName(){
        return isItalian ? "FASE DI PIANIFICAZIONE": "PLANNING PHASE";
    }
    public static String getMessagePlanningPhase(){
        return isItalian ?
                "inserisci il numero della carta assistente che vuoi usare" :
                "enter the number of the assistant card to play";
    }

    // ACTION PHASE: MOVE STUDENTS
    // PART 1
    public static String getMoveStudentsPhaseChooseStudentName(){
        return isItalian ?
                "FASE D'AZIONE: spostamento degli studenti, scegliere lo studente da muovere":
                "ACTION PHASE: move students, choose the student to move";
    }
    public static String getMessageToAskToChooseAColor(){
        return isItalian ? "scegli il colore dello studente che vuoi muovere":"choose the color of the student to move";
    }
    // PART 2
    public static String getMoveStudentsPhaseChooseDestinationName(){
        return isItalian ?
                "FASE D'AZIONE: spostamento degli studenti, scegliere la destinazione":
                "ACTION PHASE: move students, choose destination";
    }
    public static String getMessageToAskToChooseADestination(){
        return isItalian ?
                "inserisci \n" +
                        "1 per spostare lo studente nella sala \n" +
                        "2 per spostare lo studente su un'isola":

                "insert \n" +
                        "1 to move the student to the dining room \n" +
                        "2 to move it on an island";
    }

    public static String getMessageToAskIslandID(){
        return isItalian ?
                "inserisci il numero dell'isola su cui vuoi mettere lo studente":
                "insert the number of the island on which you want to put the student";
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

    // ****************** WIDGETS *************************

    // ASSISTANT CARD
    public static String getValueFieldAssistantCard(){
        return isItalian ? "valore: ":"value :";
    }

    public static String getRangeOfMotionFieldAssistantcard(){
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

}

