package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import java.net.URL;
import java.util.*;

/**
 * This class is used to represent the lobby of the match making
 */
public class LobbyScreen extends GuiScreen implements Initializable {

    /**
     * This is the header of the screen.
     */
    @FXML
    private Label headerLabel;

    /**
     * This label is used to represent the identifier of the game.
     */
    @FXML
    private Label gameIDLabel;

    /**
     * This label is used to represent the actual number of players in the game
     * over the total number of players of that game.
     */
    @FXML
    private Label numOfPlayersLabel;

    @FXML
    private Label difficultyLabel;

    /**
     * This label is used to display the choices of
     * wizard and tower of each player.
     */
    @FXML
    private Label playersChoiceLabel;

    /**
     * This label is used to tell the user to wait.
     */
    @FXML
    private Label waitLabel;

    private final Map<String, PlayerView> map = new HashMap<>();

    private int numberOfPlayers;

    /**
     * This method is used to set up labels.
     */
    private void setLabels(){
        headerLabel.setText(Translator.getLobbyHeader());
        waitLabel.setText(Translator.getWaitMessage());
    }

    @Override
    public void setUp(String gameID,int totalNumOfPlayers, boolean isExpert,List<PlayerView> playerViewList){
        numberOfPlayers = totalNumOfPlayers;
        Platform.runLater(()->gameIDLabel.setText(Translator.getGameIdentifier()+"\t"+gameID));
        Platform.runLater(()->numOfPlayersLabel.setText(Translator.getNumOfPlayersString()+"\t"+playerViewList.size() + "/"+ numberOfPlayers));
        if(isExpert){
            Platform.runLater(()->difficultyLabel.setText(Translator.getDifficultyString()+" "+Translator.getDifficultyParameters().get(1)));
        }
        else {
            Platform.runLater(()->difficultyLabel.setText(Translator.getDifficultyString()+" "+Translator.getDifficultyParameters().get(0)));
        }

        for(PlayerView playerView: playerViewList){
            map.put(playerView.getNickname(),playerView);
        }
        setPlayersChoiceLabel();

    }

    private void setPlayersChoiceLabel(){
        StringBuilder string = new StringBuilder();

        for(PlayerView playerView: map.values()){
            string.append(playerView.getNickname()).append(":");
            if(playerView.getWizard()!=null){
                string.append("\t\t").append(Translator.getWizard()).append(" ").append(playerView.getWizard().get());
            }
            if(playerView.getTowerType()!=null){
                string.append("\t\t").append(Translator.getTower()).append(" ").append(Translator.getTowerName(playerView.getTowerType().get()));
            }
            string.append("\n");
        }
        Platform.runLater(() -> playersChoiceLabel.setText(string.toString()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabels();
    }

    @Override
    public void updateTowerType(String nickname, TowerType newTower){
        map.get(nickname).setTowerType(newTower);
        setPlayersChoiceLabel();
    }

    @Override
    public void updateWizard(String nickname, Wizard newWizard){
        map.get(nickname).setWizard(newWizard);
        setPlayersChoiceLabel();
    }

    @Override
    public boolean updatePlayerList(Collection<PlayerView> playerViews){
        map.values().clear();
        for(PlayerView playerView: playerViews){
            map.put(playerView.getNickname(),playerView);
        }
        Platform.runLater(() -> numOfPlayersLabel.setText(Translator.getNumOfPlayersString()+"\t"+playerViews.size() + "/"+ numberOfPlayers));
        setPlayersChoiceLabel();
        return map.size() == numberOfPlayers;
    }
}
