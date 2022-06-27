package it.polimi.ingsw.client.view.gui.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Collection;

public class ListOfGamesScreen {


    private Collection<String> gameIDs = new ArrayList<>();

    String selectedItem;

    //String [] gameIDs = {"item 1", "item 2", "item 3"};

    @FXML
    private Label label;



    @FXML
    private ListView<String> listOfGames;

    public void setComponents(Collection<String> gameIDs){
        // this.gameIDs=gameIDs;
        listOfGames.getItems().addAll(gameIDs);
        listOfGames.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                selectedItem = listOfGames.getSelectionModel().getSelectedItem();
                label.setText(selectedItem);
                System.out.println(selectedItem);
                //label.setText(listOfGames.getSelectionModel().getSelectedItem());
            }
        });
        // label.setText("Choose the game to join");
    }


    public void takeGameToJoin(){
        System.out.println("CLICK OK ");
        //selectedItem=listOfGames.getSelectionModel().getSelectedItem();
        //if(selectedItem==null) {
            //System.out.println("NULL");
        //}

        //listOfGames.getSelectionModel().selectedItemProperty().addListener((observableValue, s, t1) -> {
          //selectedItem=listOfGames.getSelectionModel().getSelectedItem();
            //System.out.println(selectedItem);
            //label.setText(selectedItem);
            //System.out.println(selectedItem);
        //});
    }



}
