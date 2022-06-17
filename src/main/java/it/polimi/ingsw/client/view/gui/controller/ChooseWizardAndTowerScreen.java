package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.utils.image_getters.TowerImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.WizardImageType;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import javax.swing.text.Element;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class ChooseWizardAndTowerScreen {




    ArrayList<String> imagePaths = new ArrayList<>();

    ArrayList<String> towerImagePaths = new ArrayList<>();
    List<Image> towerImages = new ArrayList<>();

    int i=0;

    @FXML
    Label indicator;

    @FXML
    Label header;
    @FXML
    Pagination wizardSlider;

    @FXML
    ImageView immagine;

    @FXML
    ImageView imageViewTower;

    @FXML
    Label towerIndicator;


    List<Image> images = new ArrayList<>();


    Map<Integer,Wizard> wizardMap = new HashMap<>();
    Map<Integer,TowerType> towerTypeMap = new HashMap<>();





    public void fromWizardToImagePath(List<Wizard> wizardsAvailable){

        for(Wizard wizard: wizardsAvailable){
            imagePaths.add(WizardImageType.typeConverter(wizard).getPath());
            wizardMap.put(wizardsAvailable.indexOf(wizard),wizard);
            System.out.println(WizardImageType.typeConverter(wizard).getPath());
        }
    }

    public void fromTowerToImagePath(List<TowerType> towersAvailable){
        for(TowerType towerType: towersAvailable){
            towerImagePaths.add(TowerImageType.typeConverter(towerType).getPath());
            towerTypeMap.put(towersAvailable.indexOf(towerType),towerType);
            System.out.println(TowerImageType.typeConverter(towerType).getPath());
        }
    }


    public void creteImageList(List<String> imagePaths){
        for(String imagePath: imagePaths){
            images.add(new Image(getClass().getResourceAsStream(imagePath)));
            System.out.println("add");
        }
    }

    public void creteTowerImageList(List<String> imagePaths){
        for(String imagePath: imagePaths){
            towerImages.add(new Image(getClass().getResourceAsStream(imagePath)));
            System.out.println("add");
        }
    }

    public void setNextImage(){
        System.out.println("dopo");
        int index = images.indexOf(immagine.getImage());
        int newIndex = (index+1)%images.size();
        immagine.setImage(images.get(newIndex));
        indicator.setText(newIndex+1+"/"+images.size());
    }



    public void setPreviousImage(){
        System.out.println("prima");
        int index = images.indexOf(immagine.getImage());

        int newIndex;
        if(index-1<0){
            newIndex=images.size()-1;
        }else{
            newIndex=index-1;
        }
        immagine.setImage(images.get(newIndex));

        // immagine.setImage(images.get((index-1)%images.size()));
        indicator.setText(newIndex+1+"/"+images.size());
    }


    public void setUp(List<Wizard> wizardsAvailable, List<TowerType> towersAvailable){
        header.setText("Choose the wizard and the tower");
        // WIZARD
        fromWizardToImagePath(wizardsAvailable);
        creteImageList(imagePaths);
        immagine.setImage(images.get(0));
        indicator.setText("1/"+images.size());
        // TOWER
        fromTowerToImagePath(towersAvailable);
        creteTowerImageList(towerImagePaths);
        imageViewTower.setImage(towerImages.get(0));
        towerIndicator.setText("1/"+towerImages.size());
    }


    public void confirmChoice(ActionEvent actionEvent) {

        int numWizard = images.indexOf(immagine.getImage());
        Wizard wizardChosen = wizardMap.get(numWizard);

        int numTower = towerImages.indexOf(imageViewTower.getImage());
        TowerType towerTypeChosen = towerTypeMap.get(numTower);


        System.out.println("CHOOSEN:" + wizardChosen + " and " + towerTypeChosen);

    }

    public void setPreviusTower(ActionEvent actionEvent) {
        System.out.println("prima");
        int index = towerImages.indexOf(imageViewTower.getImage());

        int newIndex;
        if(index-1<0){
            newIndex=towerImages.size()-1;
        }else{
            newIndex=index-1;
        }
        imageViewTower.setImage(towerImages.get(newIndex));

        // immagine.setImage(images.get((index-1)%images.size()));
        towerIndicator.setText(newIndex+1+"/"+towerImages.size());
    }

    public void setNextTower(ActionEvent actionEvent) {
        System.out.println("dopo");
        int index = towerImages.indexOf(imageViewTower.getImage());
        int newIndex = (index+1)%towerImages.size();
        imageViewTower.setImage(towerImages.get(newIndex));
        towerIndicator.setText(newIndex+1+"/"+towerImages.size());
    }
}



  /*(pageIndex)->{
        Label label = new Label("ciao"+pageIndex);
        return new VBox(label);
    }
            );*/

    /*public void setUp(Collection<Wizard> wizards) {

            wizardSlider.setPageFactory((pageIndex)->{
                Label label = new Label("ciao"+pageIndex);
                //ImageView imageView = new ImageView((Element) WizardImageType.typeConverter(new ArrayList<>(wizards).get(0)).getImage());
                //i.setImage()
                return new VBox();
                }
            );

    }*/

//Image image = new Image("/assets/wizards/wizard1.png");

//Image i = new Image(getClass().getResourceAsStream("/assets/wizards/wizard1.png"));


//Pagination p = new Pagination(3);

        //wizardSlider.setPageFactory(n -> new ImageView(String.valueOf(getClass().getResourceAsStream(images.get(n)))));
//wizardSlider.setPageFactory(n -> new ImageView(String.valueOf(getClass().getResourceAsStream("/assets/wizards/wizard1.png"))));
        /*wizardSlider.setPageFactory((pageIndex)->{
            Label label = new Label("ciao"+pageIndex);
            //Image image = new Image("/assets/wizards/wizard1.png");
            return new VBox(label);
        });*/


//Image image = new Image("/assets/wizards/wizard1.png");
//immagine.getChildren().add(new ImageView(image));
//immagine.setImage(image);



