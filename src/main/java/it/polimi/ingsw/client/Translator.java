package it.polimi.ingsw.client;

import it.polimi.ingsw.network.messages.responses.ErrorCode;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.TowerType;

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

    /* *************************************************************************************************************** *
     *                                      COMMON MESSAGES FOR BOTH CLI AND GUI                                       *
     * **************************************************************************************************************** */

    // GENERAL
    public static String getWaitMessage(){
        return isItalian ? "Per favore aspetta" : "Please wait";
    }

    public static String getMessageToExit(){
        return isItalian ? "esci" : "exit";
    }

    public static String getLeftGameMessage(){
        return isItalian ? " ha lasciato il gioco." : " left the game.";
    }

    // LAUNCHER
    public static String getInsertIPAddress(){
        return isItalian ?
                "Inserisci l'indirizzo IP del server":
                "Insert IP address of the server";
    }

    public static String getInsertPortNumber(){
        return isItalian ?
                "Inserisci il numero di porta del server":
                "Insert Port Number of the server";
    }

    // HOME SCREEN
    public static String getCreate(){
        return isItalian ? "Crea" : "Create";
    }

    public static String getJoin(){
        return isItalian ? "Unisciti" : "Join";
    }

    public static String getResume(){
        return isItalian ? "Riprendi" : "Resume";
    }

    // CREATE GAME
    public static String getChooseNumOfPlayers(){
        return isItalian ? "Scegli il numero di giocatori":"Choose number of players";
    }
    public static String getChooseDifficultyOfGame(){
        return isItalian ? "Scegli la difficoltà":"Choose difficulty";
    }


    // CHARACTER CARDS

    public static String getCardLabel(){
        return isItalian ? "Carta#" : "Card#";
    }

    public static String getCostLabel(){
        return isItalian ? "Costo: " : "Cost: ";
    }

    public static String getEffectDescription(CharacterCardsType card) {
        return isItalian ? getItalianEffectDescription(card) : getEnglishEffectDescription(card);
    }

    private static String getItalianEffectDescription(CharacterCardsType card) {
        return switch (card) {
            case CARD1 -> "All'inizio della partita, pescate 4 Studenti e piazzateli sopra questa carta.\n" +
                    "EFFETTO: Prendi 1 Studente dalla carta e piazzalo su un'Isola a tua scelta. " +
                    "Poi, pesca 1 Studente dal sacchetto e mettilo su questa carta.";
            case CARD2 -> "EFFETTO: Durante questo turno, prendi il controllo dei Professori anche se nella tua Sala" +
                    " hai lo stesso numero di Studenti del giocatore che li controlla in quel momento.";
            case CARD3 -> "EFFETTO: Puoi muovere Madre Natura fino a 2 Isole addizionali " +
                    "rispetto a quanto indicato sulla carta Assistente che hai giocato.";
            case CARD4 -> "EFFETTO: Scegli un'isola e calcola la maggioranza come se Madre Natura avesse " +
                    "terminato il suo movimento lì. In questo turno Madre Natura si muoverà come di consueto " +
                    "e nell'Isola dove terminerà il suo movimento la maggioranza verrà normalmente calcolata.";
            case CARD5 -> "All'inizio della partita, mettete le 4 tessere Divieto su questa carta.\n" +
                    "EFFETTO: Piazza una tessera Divieto su un'Isola a tua scelta. La prima volta che Madre Natura " +
                    "termina il suo movimento lì, rimettete la tessera Divieto sulla carta SENZA calcolare " +
                    "l'influenza su quell'isola né piazzare Torri.";
            case CARD6 -> "EFFETTO: Durante il conteggio dell'influenza su un'Isola (o su un gruppo di Isole), " +
                    "le Torri presenti non vengono calcolate.";
            case CARD7 -> "EFFETTO: In questo turno, durante il calcolo dell'Influenza " +
                    "hai 2 punti di influenza addizionali.";
            case CARD8 -> "EFFETTO: Scegli un colore di Studente; in questo turno, " +
                    "durante il calcolo dell'influenza quel colore non fornisce influenza.";
            case CARD9 -> "All'inizio della partita, pescate 6 Studenti e piazzateli su questa carta.\n" +
                    "EFFETTO: Puoi prendere fino a 3 Studenti da questa carta " +
                    "e scambiarli con altrettanti Studenti presenti nel tuo ingresso.";
            case CARD10 -> "EFFETTO: Puoi scambiare fra loro fino a 2 Studenti presenti nella tua Sala e nel tuo Ingresso.";
            case CARD11 -> "All'inizio della partita, pescate 4 Studenti e piazzateli su questa carta.\n" +
                    "EFFETTO: Prendi 1 Studente da questa carta e piazzalo nella tua Sala." +
                    " Poi pesca un nuovo Studente dal sacchetto e posizionalo su questa carta.";
            case CARD12 -> "EFFETTO: Scegli un colore di Studente; ogni giocatore (incluso te) " +
                    "deve rimettere nel sacchetto 3 Studenti di quel colore presenti nella sua Sala. " +
                    "Chi avesse meno di 3 Studenti di quel colore, rimetterà tutti quelli che ha.";
        };
    }

    private static String getEnglishEffectDescription(CharacterCardsType card) {
        return switch (card) {
            case CARD1 -> "In setup, draw 4 Students and place them on this card.\n" +
                    "EFFECT: Take 1 Student from this card and place it on an Island of your choice. " +
                    "Then, draw a new Student from the Bag and place it on this card.";
            case CARD2 -> "EFFECT: During this turn, you take control of any number of Professors even if you have " +
                    "the same number of Students as the player who currently controls them.";
            case CARD3 -> "EFFECT: You may move Mother Nature up to 2 additional Islands than is indicated " +
                    "by the Assistant card you've played.";
            case CARD4 -> "EFFECT: Choose an Island and resolve the Island as if Mother Nature had ended her " +
                    "movement there. Mother Nature will still move and the Island where she ends her movement " +
                    "will also be resolved.";
            case CARD5 -> "In Setup, put the 4 No Entry tiles on this card.\n" +
                    "EFFECT: Place a No Entry tile on an Island of your choice. " +
                    "The first time Mother Nature ends her movement there, put the No Entry tile back onto this card" +
                    " DO NOT calculate influence on that Island, or place any Towers.";
            case CARD6 -> "EFFECT: When resolving a Conquering on an Island, Towers do not count towards influence.";
            case CARD7 -> "EFFECT: During influence calculation this turn, you count as having 2 more influence.";
            case CARD8 -> "EFFECT: Choose a color of Student: during the influence calculation this turn, " +
                    "that color adds no influence.";
            case CARD9 -> "In Setup, draw 6 Students and place them on this card.\n" +
                    "EFFECT: You may take up to 3 students from this card and replace them " +
                    "with the same number of Students from your entrance.";
            case CARD10 -> "EFFECT: You may exchange up to 2 Students between your Entrance and your Dining Room.";
            case CARD11 -> "In Setup, draw 4 Students and place them on this card.\n" +
                    "EFFECT: Take 1 Student from this card and place it in your Dining Room. " +
                    "Then, draw a new Student from the Bag and place it on this card.";
            case CARD12 -> "EFFECT: Choose a type of Student: every player (including yourself) must return 3 " +
                    "Students of that type from their Dining Room to the bag. If any player has fewer than 3 " +
                    "Students of that type, return as many Students as you have.";
        };
    }

    /* *************************************************************************************************************** *
     *                                              CLIENT CONTROLLER                                                  *
     * **************************************************************************************************************** */
    public static String getItIsNotYourTurnMessage(){
        return isItalian ? "Non è il tuo turno!" : "It is not your turn!";
    }

    public static String getWrongMotherNatureMovementMessage(){
        return isItalian ? "Il valore non può essere negativo!" : "The value cannot be negative!";
    }

    public static String getInputOutOfRangeMessage(){
        return isItalian ? "Il valore non è ammesso" : "The value is out of range";
    }

    public static String getErrorConnectionMessage(){
        return isItalian ? "Impossibile connettersi al server. Riprova":"Can't connect to server. Try again";
    }

    /* *************************************************************************************************************** *
    *                                                    CLI                                                           *
    * ***************************************************************************************************************  */

    // ********************************************* INPUT READER ******************************************************
    public static String getWrongInputErrorMessage(){
        return isItalian ? "Il comando inserito non è corretto.":"The command is not correct.";
    }

    // ********************************************* CLI SCREENs *******************************************************

    // GENERAL

    public static String getMessageCurrentPlayer(){
        return isItalian ? "giocatore corrente":"current player";
    }

    public static String getConfirmExit(){
        return isItalian ? "Sei sicuro di voler uscire dal gioco? [yes/no]"
                : "Are you sure you want to exit the game? [yes/no]";
    }

    public static String getPressEnterToExit(){
        return isItalian ? "Premi INVIO per uscire dalla partita" : "Press ENTER to exit the game";
    }

    // LAUNCHER
    public static String getGameSubtitle(){
        return isItalian ? "Un magico mondo di isole volanti!" : "A magic world of floating islands!";
    }

    public static String getChooseLanguage(){
        return isItalian ? "Scegli una lingua" : "Choose a language";
    }

    public static String getConnectionError(){
        return isItalian ? "Errore di connessione. Attendi" : "Connection error. Wait";
    }

    // MATCH MAKING

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

    public static String getChooseGame(){
        return isItalian ? "Scegli una partita"+tabHelpIT : "Choose a game"+tabHelpEn;
    }

    public static String getNoGamesToJoin(){
        return isItalian ? "Non ci sono partite a cui ti puoi unire" : "There are no games you can join";
    }

    public static String getNickname(){
        return isItalian ? "Per favore scegli un nickname" : "Please provide a nickname";
    }

    public static String getBack(){
        return isItalian ? "Indietro" : "Back";
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

    // ACTION PHASE: CHARACTER CARDS

    public static String getChooseCard(){
        return isItalian ? "Scegli che carta usare" : "Choose a card to use";
    }

    public static String getChooseStudent(){
        return isItalian ? "Scegli uno studente" : "Choose a student";
    }

    public static String getChooseIsland(){
        return isItalian ? "Scegli un'isola" : "Choose an island";
    }

    public static String getChooseStudentIsland(){
        return isItalian ? "Scegli uno studente e l'isola su cui metterlo" : "Choose a student and the island to put it on";
    }
    
    public static String getChooseCoupleStudents(){
        return isItalian ? "Scegli una coppia di studenti" : "Choose a couple of students";
    }
    
    public static String getFirstCardSecondEntrance(){
        return isItalian ? "\nIl primo deve essere sulla carta e il secondo nel tuo ingresso"
                : "\nThe first one must be on the card, the second one in your entrance";
    }
    
    public static String getFirstDiningRoomSecondEntrance(){
        return isItalian ? "\nIl primo deve essere nella tua sala, il secondo nel tuo ingresso"
                : "\nThe first one must be in your dining room, the second in your entrance";
    }

    // ACTION PHASE: MOVE STUDENTS
    public static String getMoveStudentsPhaseName(){
        return isItalian ?
                "FASE D'AZIONE: spostamento degli studenti, scegliere lo studente da muovere":
                "ACTION PHASE: move students, choose the student to move";
    }
    public static String getMessageMoveStudentsPhase(){
        return isItalian ?
                """
                        inserisci il colore dello studente da muovere seguito dalla destinazione\s
                        Esempio:
                        se vuoi muovere uno studente blu sull'isola 1 scrivi: "blu Isola#1"
                        oppure se vuoi muovere uno studente verde nella sala scrivi " verde Sala\"""" :

                """
                        insert the color of the student to move followed by the destination\s
                        Example:
                        if you want to move the blue student on the island 1 insert: "blue Island#1"
                        or if you want to move the green student to the dining room insert: "green Dining_Room\"""";
    }

    public static String getDiningRoomLocationName(){
        return isItalian ? "Sala":"Dining_Room";
    }

    public static List<String> getColor(){
        return isItalian ?
                new ArrayList<>(List.of("blu", "verde", "giallo", "rosso", "rosa")) :
                new ArrayList<>(List.of("blue", "green", "yellow", "red", "pink"));
    }

    /**
     * this method will map the color to the pawn type
     *
     * @param color the string containing the color (in the chosen language)
     * @return the corresponding pawn type
     */
    public static PawnType parseColor(String color){
        if(color.equals(Translator.getColor().get(0))){
            return PawnType.BLUE_UNICORNS;
        }
        if(color.equals(Translator.getColor().get(1))){
            return PawnType.GREEN_FROGS;
        }
        if(color.equals(Translator.getColor().get(2))){
            return PawnType.YELLOW_GNOMES;
        }
        if(color.equals(Translator.getColor().get(3))){
            return PawnType.RED_DRAGONS;
        }
        if(color.equals(Translator.getColor().get(4))){
            return PawnType.PINK_FAIRIES;
        }
        return null;
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

    // *********************************************** WIDGETS *********************************************************

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

    // ISLAND
    public static String getIslandName(){
        return isItalian ? "Isola#" : "Island#";
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
        return (isItalian ? "Difficoltà: " : "Difficulty: ") + getDifficulty(isExpert);
    }

    public static String getDifficulty(boolean isExpert){
        return isItalian ? (isExpert ? "esperti" : "normale") : (isExpert ? "expert" : "standard");
    }

    public static String getLabelGameID(){
        return isItalian ? "Identificatore della partita: " : "Identifier of this game: ";
    }

    public static String getNextPhase(){
        return isItalian ? "Avanti" : "Next";
    }

    public static String getAvailableSuffix(){
        return isItalian ? " disponibili" : " available";
    }

    public static String getTowerLabel(){
        return isItalian ? "Torre" : "Tower";
    }
    public static String getTowerName(TowerType towerType){
        if (!isItalian)
            return towerType.name();
        return switch (towerType) {
            case BLACK -> "NERA";
            case WHITE -> "BIANCA";
            case GREY -> "GRIGIA";
        };
    }

    public static TowerType parseTowerType(String tower) throws IllegalArgumentException{
        if (!isItalian)
            return TowerType.valueOf(tower);
        return switch (tower) {
            case "NERA" -> TowerType.BLACK;
            case "BIANCA" -> TowerType.WHITE;
            case "GRIGIA" -> TowerType.GREY;
            default -> throw new IllegalArgumentException();
        };
    }

    public static String getWizardLabel(){
        return isItalian ? "Mago" : "Wizard";
    }
    public static String getWizardName(Wizard wizard) {
        return switch (wizard) {
            case W1 -> isItalian ? "MAGO 1" : "WIZARD 1";
            case W2 -> isItalian ? "MAGO 2" : "WIZARD 2";
            case W3 -> isItalian ? "MAGO 3" : "WIZARD 3";
            case W4 -> isItalian ? "MAGO 4" : "WIZARD 4";
        };
    }

    public static String getAskForInitialParameters(){
        return isItalian ? "Scegli la torre e il mago che vuoi usare durante la partita" + tabHelpIT
                : "Choose the tower and wizard that you want to use during the game" + tabHelpEn;
    }

    /* *************************************************************************************************************** *
     *                               MANAGEMENT OF ERROR MESSAGES COMING FROM ERROR CODES                              *
     * *************************************************************************************************************** */

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

    /* *************************************************************************************************************** *
    *                                                    GUI                                                           *
    * **************************************************************************************************************** */

    // ***************************************** MATCHMAKING AND LAUNCHER **********************************************

    // CHOOSE SERVER PARAMETERS SCREEN
    public static String getChooseAServer(){
        return isItalian ? "Scegli un server":"Choose a server";
    }

    public static String getWrongIPAddressMessage(){
        return isItalian ? "Indirizzo IP non valido":"IP address not valid";
    }

    public static String getWrongPortNumberMessage(){
        return isItalian ? "numero di porta non valido":"port number not valid";
    }

    // CREATE GAME SCREEN
    public static String getHeaderCreateGameScreen(){
        return isItalian ? "Creazione di un nuovo gioco":"Creation of a new game";
    }

    public static String getMissingParameterError(){
        return isItalian ? "non è opzionale":"it is not optional";
    }

    public static List<String> getDifficultyParameters(){
        return isItalian ?
                new ArrayList<>(List.of("normale","esperti")):
                new ArrayList<>(List.of("normal", "expert"));
    }

    // ASK NICKNAME SCREEN
    public static String getAskNickname(){
        return isItalian ? "Scegli un nickname":"Choose a nickname";
    }

    public static String getNoteOnNickname(){
        return isItalian ?
                "può contenere qualunque carattere eccetto lo spazio":
                "it can contains any character except for space";
    }

    public static String getMessageWrongNickname(){
        return isItalian ?
                "nickname non valido, non può contenere spazi":
                "it is not valid: you cannot insert spaces";
    }

    // CHOOSE WIZARD AND TOWER SCREEN
    public static String getHeaderChooseWizardAndTower(){
        return isItalian ?
                "Scegli il mago e la torre con cui vuoi giocare":
                "Choose the wizard and the tower to play with";
    }

    // CHOOSE GAME TO JOIN
    public static String getHeaderChooseGameToJoin(){
        return isItalian ? "Scegli il gioco a cui vuoi unirti":"choose the game to join";
    }

    // EXIT SCREEN
    public static String getExitButton(){
        return isItalian ? "Esci dal gioco":"Exit from game";
    }

    public static String getGameHasEndedMessage(){
        return isItalian ? "Il gioco è finito":"The game has ended";
    }

    public static String getHaveWonTheGameMessage(){
        return isItalian ? "hanno vinto la partita":"have won the game";
    }

    public static String getParityString(){
        return isItalian ? "PARITA'":"PARITY";
    }

    // *************************************** DIALOG SCREENS **********************************************************
    public static String getAlertTitle(){
        return isItalian ? "Esci dal gioco":"Exit from game";
    }

    public static String getAlertHeader(){
        return isItalian ? "Stai per uscire dal gioco":"You're about to exit from game";
    }

    public static String getAlertContent(){
        return isItalian ? "Vuoi uscire dal gioco ?":"Do you want to exit the game ?";
    }

    public static String getHeaderErrorAlert(){
        return isItalian ? "Errore":"Error";
    }

    public static String getTextOfCancelButton(){
        return isItalian ? "Annulla":"Cancel";
    }

}

