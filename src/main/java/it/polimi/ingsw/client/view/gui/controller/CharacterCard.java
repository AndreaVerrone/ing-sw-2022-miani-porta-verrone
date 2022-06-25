package it.polimi.ingsw.client.view.gui.controller;


import it.polimi.ingsw.client.view.gui.ClientGui;
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
    private StudentList students;

    /**
     * Nummber of bans eventually present on the card
     */
    private int numberOfBans = 0;

    /**
     * Cost of the card
     */
    int cost;

    /**
     * True if there is a coin on the card
     */
    boolean coinOnCard;

    /**
     * This lass represents a character card on the view with all its informations
     * @param cardType type of the card on the view
     * @param characterCardView {@code ImageView} of the card
     */
    public CharacterCard(CharacterCardsType cardType, ImageView characterCardView){
        this.cardType = cardType;
        this.characterCardView = characterCardView;
        this.cost = cardType.getCost();

        characterCardView.setOnMouseClicked(mouseEvent -> ClientGui.getSwitcher().goToCharacterCardView(CharacterCard.this));

        try {
            students = new StudentList();
            students.changeNumOf(PawnType.RED_DRAGONS, 4);
            students.changeNumOf(PawnType.GREEN_FROGS, 1);
            students.changeNumOf(PawnType.BLUE_UNICORNS, 1);
            students = new StudentList();

        } catch (NotEnoughStudentException e) {
            throw new RuntimeException(e);
        }
        setNumberOfBans(4);
        setCoinOnCard(true);
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

    public boolean isCoinOnCard() {
        return coinOnCard;
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
