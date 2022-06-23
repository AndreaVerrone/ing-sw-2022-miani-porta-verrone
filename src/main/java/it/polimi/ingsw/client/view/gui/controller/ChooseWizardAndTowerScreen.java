package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ScreenBuilder;
import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import it.polimi.ingsw.client.view.gui.utils.image_getters.TowerImageType;
import it.polimi.ingsw.client.view.gui.utils.image_getters.WizardImageType;
import it.polimi.ingsw.server.model.player.Wizard;
import it.polimi.ingsw.server.model.utils.TowerType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.util.*;

/**
 * This class is the controller of the screen used to ask
 * the user to choose a wizard and a tower.
 */
public class ChooseWizardAndTowerScreen extends GuiScreen implements Initializable {

    /**
     * This is the header of the screen.
     * It is used to ask the user to choose a tower and a wizard.
     */
    @FXML
    Label header;

    /**
     * This is the current wizard imageView on the screen.
     */
    @FXML
    ImageView imageViewWizard;

    /**
     * This is the current tower image view on thre screen.
     */
    @FXML
    ImageView imageViewTower;

    /**
     * This is a label used to indicate the index
     * of the wizard image on the list
     */
    @FXML
    Label wizardIndicator;

    /**
     * This is a label used to indicate the index
     * of the tower image on the list
     */
    @FXML
    Label towerIndicator;

    /**
     * This is a list containing the images of the wizards
     */
    private final List<Image> wizardImages = new ArrayList<>();

    /**
     * This is a list containing the images of the towers.
     */
    private final List<Image> towerImages = new ArrayList<>();

    /**
     * This is a map that maps the index of the list of the images of the wizard
     * with the corresponding wizard.
     */
    private final Map<Integer,Wizard> wizardMap = new HashMap<>();

    /**
     * This is a map that maps the index of the images of the towers with
     * the corresponding tower type.
     */
    private final Map<Integer,TowerType> towerTypeMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabels();
    }

    /**
     * This method is used to set up the screen.
     * It will create the list of images of wizard and towers
     * starting from the list of available wizards and available towers
     * @param wizardsAvailable list of wizards available
     * @param towersAvailable list of available towers
     */
    public void setUp(List<Wizard> wizardsAvailable, List<TowerType> towersAvailable){
        // WIZARD
        // create the list
        createWizardImageList(wizardsAvailable);
        // set the image
        imageViewWizard.setImage(wizardImages.get(0));
        // set the label
        wizardIndicator.setText("1/"+wizardImages.size());
        // TOWER
        // create the list
        createTowerImageList(towersAvailable);
        // set the image
        imageViewTower.setImage(towerImages.get(0));
        // set the label
        towerIndicator.setText("1/"+towerImages.size());
    }

    /**
     * This method is called when the user press the button to go
     * to the next wizard.
     * It allows to display the next wizard.
     */
    public void setNextWizard(){
        setNextImage(wizardImages,imageViewWizard,wizardIndicator);
    }

    /**
     * This method is called when the user press the button to go
     * to the previous wizard.
     * It allows to display the previous wizard.
     */
    public void setPreviousWizard(){
        setPreviousImage(wizardImages,imageViewWizard,wizardIndicator);
    }

    /**
     * This method is called when the user press the button to go
     * to the next tower.
     * It allows to display the next tower.
     */
    public void setNextTower() {
        setNextImage(towerImages,imageViewTower,towerIndicator);
    }

    /**
     * This method is called when the user press the button to go
     * to the previous tower.
     * It allows to display the previous tower.
     */
    public void setPreviousTower() {
        setPreviousImage(towerImages,imageViewTower,towerIndicator);
    }

    /**
     * This method is called when the OK button is pressed.
     * It allows to confirm the choice of the user.
     */
    public void confirmChoice() {
        // take the wizard
        int numWizard = wizardImages.indexOf(imageViewWizard.getImage());
        Wizard wizardChosen = wizardMap.get(numWizard);
        // take the tower
        int numTower = towerImages.indexOf(imageViewTower.getImage());
        TowerType towerTypeChosen = towerTypeMap.get(numTower);
        System.out.println("CHOOSEN:" + wizardChosen + " and " + towerTypeChosen); // todo: testing only
        // todo: actual code
        // Go to idle matchmaking
        getGui().getScreenBuilder().build(ScreenBuilder.Screen.IDLE); // todo: it should be lobby, but now it is not available
        getGui().run();
    }

    /**
     * This method will set up the label of the screen.
     */
    private void setLabels(){
        header.setText(Translator.getHeaderChooseWizardAndTower());
    }

    /**
     * This method will create the list of wizard images, and it will
     * fill the map with wizards.
     * It will take in input the list of wizards that are available.
     * @param wizardsAvailable list of wizards that are available
     */
    private void createWizardImageList(List<Wizard> wizardsAvailable){
        // create a list of images paths and the map
        List<String> wizardImagePaths = new ArrayList<>();
        for(Wizard wizard: wizardsAvailable){
            // add path to the list of paths
            wizardImagePaths.add(WizardImageType.typeConverter(wizard).getPath());
            // add element to the map
            wizardMap.put(wizardsAvailable.indexOf(wizard),wizard);
            System.out.println(WizardImageType.typeConverter(wizard).getPath()); // todo: testing only
        }
        // create the list of images
        creteImageList(wizardImagePaths,this.wizardImages);
    }

    /**
     * This method will create the list of images path and the map.
     * It will take in input the list of towers that are available.
     * @param towersAvailable list of towers that are available
     */
    private void createTowerImageList(List<TowerType> towersAvailable){
        // create a list of images paths and the map
        ArrayList<String> towerImagePaths = new ArrayList<>();
        for(TowerType towerType: towersAvailable){
            // add path to the list of paths
            towerImagePaths.add(TowerImageType.typeConverter(towerType).getPath());
            // add element to the map
            towerTypeMap.put(towersAvailable.indexOf(towerType),towerType);
            System.out.println(TowerImageType.typeConverter(towerType).getPath()); // todo: testing only
        }
        // create the list of images
        creteImageList(towerImagePaths,this.towerImages);
    }

    /**
     * This method will create a list of images starting from a list
     * of paths.
     * @param imagePaths list of path to create the list of images
     */
    private void creteImageList(List<String> imagePaths, List<Image> images){
        for(String imagePath: imagePaths){
            images.add(new Image(getClass().getResourceAsStream(imagePath)));
            System.out.println("add"); // todo: testing code
        }
    }

    /**
     * This method allows to go to the next image in the list.
     * @param images the considered list of images
     * @param imageView the imageVied that has to contain the image to show
     * @param indicator the label that hold the information of the position of the image to display in the list
     */
    private void setNextImage(List<Image> images, ImageView imageView, Label indicator){
        int index = images.indexOf(imageView.getImage());
        int newIndex = (index+1)%images.size();
        imageView.setImage(images.get(newIndex));
        indicator.setText(newIndex+1+"/"+images.size());
    }

    /**
     * This method allows to show the previous image in the list.
     * @param images the considered list of images
     * @param imageView the imageVied that has to contain the image to show
     * @param indicator the label that hold the information of the position of the image to display in the list
     */
    private void setPreviousImage(List<Image> images, ImageView imageView, Label indicator){
        int index = images.indexOf(imageView.getImage());

        int newIndex;
        if(index-1<0){
            newIndex=images.size()-1;
        }else{
            newIndex=index-1;
        }
        imageView.setImage(images.get(newIndex));

        indicator.setText(newIndex+1+"/"+images.size());
    }

}




