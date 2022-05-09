package it.polimi.ingsw.client;

import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Assistant;
import it.polimi.ingsw.model.player.Wizard;
import it.polimi.ingsw.network.messages.clienttoserver.game.*;
import it.polimi.ingsw.network.messages.clienttoserver.launcher.*;
import it.polimi.ingsw.network.messages.clienttoserver.matchmaking.*;


public class Controller {
    private String nickNameOwner;
    private String nickNameCurrentPlayer;

    public Controller(){

    }

    public void setNickNameCurrentPlayer(String nickNameCurrentPlayer) {
        this.nickNameCurrentPlayer = nickNameCurrentPlayer;
    }

    private boolean wrongPlayerTurn(){
        if(!nickNameOwner.equals(nickNameCurrentPlayer)){
            //TODO: Update view, not your turn!
            return true;
        }
        return false;
    }


    public void createGame(String numberOfPlayersString, String wantExpertString){
        int numberOfPlayers;
        boolean wantExpert;
        try {
            numberOfPlayers = Integer.parseInt(numberOfPlayersString);
        }
        catch(NumberFormatException e){
            //TODO: wrong input
            return;
        }
        if(wantExpertString.length() > 0 && wantExpertString.charAt(0) == 'Y'){
            wantExpert = true;
        }
        else {
            if (wantExpertString.length() > 0 && wantExpertString.charAt(0) == 'N') {
                wantExpert = false;
            } else {
                //TODO wrong input
                return;
            }
        }
        ConnectionHandler.getInstance().sendMessage(new CreateNewGame(numberOfPlayers, wantExpert));
    }

    public void enterGame(String nickName, String gameId){
        nickNameOwner = nickName;
        ConnectionHandler.getInstance().sendMessage(new EnterGame(nickName, gameId));
    }

    public void resumeGame(){
        ConnectionHandler.getInstance().sendMessage(new ResumeGame());
    }

    public void sendUserIdentifier(String ID){
        ConnectionHandler.getInstance().sendMessage(new SendUserIdentifier(ID));
    }

    public void ChangeNumPlayers(String newNumberPlayersString){
        if(wrongPlayerTurn()) return;
        int newNumberPlayers;
        try {
            newNumberPlayers = Integer.parseInt(newNumberPlayersString);
        }
        catch(NumberFormatException e){
            //TODO: wrong input
            return;
        }
        ConnectionHandler.getInstance().sendMessage(new ChangeNumPlayers(newNumberPlayers));
    }

    public void exitFromGame(){
        ConnectionHandler.getInstance().sendMessage(new ExitFromGame(nickNameOwner));
    }

    public void nextPhase() {
        if(wrongPlayerTurn()) return;
        ConnectionHandler.getInstance().sendMessage(new NextPhase());
    }

    public void setTower(String tower){
        if(wrongPlayerTurn()) return;
        TowerType towerType;
        try {
            towerType = TowerType.valueOf(tower);
        }
        catch(IllegalArgumentException e){
            //TODO: wrong input
            return;
        }
        ConnectionHandler.getInstance().sendMessage(new SetTower(towerType));
    }

    public void setWizard(String wizard){
        if(wrongPlayerTurn()) return;
        Wizard wizardType;
        try {
            wizardType = Wizard.valueOf(wizard);
        }
        catch(IllegalArgumentException e){
            //TODO: wrong input
            return;
        }
        ConnectionHandler.getInstance().sendMessage(new SetWizard(wizardType));
    }

    public void moveMotherNature(String movementsString){
        if(wrongPlayerTurn()) return;
        int movements;
        try {
            movements = Integer.parseInt(movementsString);
        }
        catch(NumberFormatException e){
            //TODO: wrong input
            return;
        }
        if(movements<=0){
            //TODO: wrong input
            return;
        }
        ConnectionHandler.getInstance().sendMessage(new MoveMotherNature(movements));
    }

    public void quitGame(){
        ConnectionHandler.getInstance().sendMessage(new QuitGame());
    }

    public void takeStudentFromCloud(String cloudIdString){
        if(wrongPlayerTurn()) return;
        int cloudId;
        try {
            cloudId = Integer.parseInt(cloudIdString);
        }
        catch(NumberFormatException e){
            //TODO: wrong input
            return;
        }
        if(cloudId<0 || cloudId>3){
            //TODO: wrongInput
            return;
        }
        ConnectionHandler.getInstance().sendMessage(new TakeStudentsFromCloud(cloudId));
    }

    public void useAssistant(String assistantString){
        if(wrongPlayerTurn()) return;
        Assistant assistant;
        try {
            assistant = Assistant.valueOf(assistantString);
        }
        catch(IllegalArgumentException e){
            //TODO: wrong input
            return;
        }
        ConnectionHandler.getInstance().sendMessage(new UseAssistant(assistant));
    }
}
