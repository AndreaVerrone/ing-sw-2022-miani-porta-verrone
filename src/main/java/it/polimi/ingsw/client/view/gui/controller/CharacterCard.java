package it.polimi.ingsw.client.view.gui.controller;


import it.polimi.ingsw.client.view.gui.ClientGui;
import it.polimi.ingsw.client.view.gui.GUI;
import it.polimi.ingsw.client.view.gui.utils.image_getters.CharacterCardImageType;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import javafx.scene.image.ImageView;

/**
 * Class containing all the information of a character card on the view
 */
public class CharacterCard {

    /**
     * Type of the card
     */
    private final CharacterCardsType cardType;

    /**
     * {@code ImageView} of the card
     */
    private final ImageView characterCardView;

    /**
     * Students eventually present on the card
     */
    private final StudentList students;

    /**
     * Number of bans eventually present on the card
     */
    private int numberOfBans = 0;

    /**
     * Cost of the card
     */
    private int cost;

    /**
     * True if there is a coin on the card
     */
    private boolean coinOnCard;

    /**
     * Gui of the game
     */
    private final GUI gui;

    /**
     * This lass represents a character card on the view with all its informations
     * @param cardType type of the card on the view
     * @param characterCardView {@code ImageView} of the card
     * @param gui Gui of the game
     */
    public CharacterCard(GUI gui, CharacterCardsType cardType, ImageView characterCardView){
        this.gui = gui;
        this.cardType = cardType;
        this.characterCardView = characterCardView;
        this.cost = cardType.getCost();
        this.students = new StudentList();

        characterCardView.setOnMouseClicked(mouseEvent -> gui.useCharacterCard(this));

        if(cardType.equals(CharacterCardsType.CARD8) || cardType.equals(CharacterCardsType.CARD12)){
            for (PawnType color: PawnType.values()){
                try {
                    students.changeNumOf(color, 1);
                } catch (NotEnoughStudentException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }

    public ImageView getCharacterCardView() {
        return new ImageView(CharacterCardImageType.typeConverter(cardType).getImageBigger());
    }

    public CharacterCardsType getCardType() {
        return cardType;
    }

    public int getCost() {
        return cost;
    }

    public StudentList getStudents() {
        return students;
    }

    public int getNumberOfBans() {
        return numberOfBans;
    }

    /**
     * Returns true if there is a coin on hte card
     * @return true if there is a coin on the card
     */
    public boolean isCoinOnCard() {
        return coinOnCard;
    }

    /**
     * Method to set the cost of the card
     * @param cost
     */
    public void setCost(int cost){
        this.cost = cost;
    }

    /**
     * Sets the number of bans on the card
     * @param numberOfBans number of bans on the card
     */
    public void setNumberOfBans(int numberOfBans) {
        this.numberOfBans = numberOfBans;
    }

    /**
     * Puts a coin on the card
     * @param coinOnCard true to put acoin on the card
     */
    public void setCoinOnCard(boolean coinOnCard) {
        this.coinOnCard = coinOnCard;
    }

    /**
     * Fill the students on the card with the given students
     * @param students new students on the card
     */
    public void setStudents(StudentList students){
        this.students.empty();
        this.students.add(students);
    }

    /**
     * Method to increment the cost of a card
     */
    public void incrementCost(){
        this.cost ++;
        setCoinOnCard(true);

    }
}
