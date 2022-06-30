package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GuiScreen;
import it.polimi.ingsw.client.view.gui.utils.image_getters.AssistantCardImageType;
import it.polimi.ingsw.server.model.player.Assistant;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.*;

/**
 * this is the controller of the screen that is used to allow the user to play an assistant card.
 */
public class UseAssistantView extends GuiScreen {

    /**
     * This is the current assistant imageView on the screen.
     */
    @FXML
    ImageView assistantImageView;

    /**
     * This is a label used to indicate the index
     * of the assistant image in the list
     */
    @FXML
    Label indicator;

    /**
     * The name of the assistant card that it is currently on yje screen.
     */
    @FXML
    Label assistantName;

    /**
     * This is a list containing the images of the assistants.
     */
    List<Image> assistantImages = new ArrayList<>();

    /**
     * This is a map that maps the index of the list of the images of the wizard
     * with the corresponding wizard.
     */
    Map<Integer,Assistant> map = new HashMap<>();

    /**
     * This method is used to set up the screen.
     * It will create the list of images of assistant cards
     * starting from the list of available assistants cards
     * @param deck the deck (i.e., list of assistant cards available)
     */
    public void setUp(Collection<Assistant>deck){
        System.out.println("CALLING OF METHOD !");
        // create the list
        createAssistantImageList(new ArrayList<>(deck));
        // set the image
        assistantImageView.setImage(assistantImages.get(0));
        System.out.println("set first image");
        // set the label
        indicator.setText("1/"+assistantImages.size());
        System.out.println("set the label");
    }

    /**
     * This method is called when the user
     */
    public void useAssistant(){
        Assistant assistantChosen = assistantOnScreen();
        Platform.runLater(()->{
            getGui().getUseAssistantStage().close();
            getGui().getClientController().useAssistant(assistantChosen);
            getGui().getStage().setFullScreen(true);
        });
        // todo: next phase of the game
    }

    /**
     * This method is called when the user press the button to go
     * to the next assistant.
     * It allows to display the next assistant.
     */
    public void setNext() {
        setNextImage(this.assistantImages,assistantImageView,indicator);
    }

    /**
     * This method is called when the user press the button to go
     * to the previous assistant.
     * It allows to display the previous assistant.
     */
    public void setPrevious() {
        setPreviousImage(this.assistantImages,assistantImageView,indicator);
    }

    /**
     * This method will return the assistant that is currently on the screen.
     * @return the assistant on the screen.
     */
    private Assistant assistantOnScreen(){
        int numAssistant = assistantImages.indexOf(assistantImageView.getImage());
        return map.get(numAssistant);
    }

    /**
     * This method will create the list of images path and the map.
     * It will take in input the list of assistants that are available.
     * @param deck the list of assistants that are available
     */
    private void createAssistantImageList(List<Assistant> deck){
        // create a list of images paths and the map
        ArrayList<String> imagePaths = new ArrayList<>();
        for(Assistant assistant: deck){
            // add path to the list of paths
            imagePaths.add(AssistantCardImageType.typeConverter(assistant).getPath());
            System.out.println(AssistantCardImageType.typeConverter(assistant).getPath()); // todo: testing only
            // add element to the map
            map.put(deck.indexOf(assistant),assistant);
        }
        // create the list of images
        creteImageList(imagePaths,this.assistantImages);
        System.out.println(this.assistantImages);
    }

    /**
     * This method will create a list of images starting from a list
     * of paths.
     * @param imagePaths list of path to create the list of images
     */
    private void creteImageList(List<String> imagePaths, List<Image> images){
        for(String imagePath: imagePaths){
            images.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
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
        assistantName.setText(assistantOnScreen().name());
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
        assistantName.setText(assistantOnScreen().name());
    }

}
