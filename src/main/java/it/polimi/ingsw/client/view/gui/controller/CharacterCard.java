package it.polimi.ingsw.client.view.gui.controller;


import it.polimi.ingsw.client.ClientApplication;
import it.polimi.ingsw.client.view.gui.utils.image_getters.CharacterCardImageType;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.model.utils.PawnType;
import it.polimi.ingsw.server.model.utils.StudentList;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughStudentException;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;

import javafx.scene.input.MouseEvent;
public class CharacterCard {

    private final CharacterCardsType cardType;
    private final ImageView characterCardView;

    private StudentList students;

    private int numberOfBans = 0;

    int cost;

    boolean coinOnCard;

    public CharacterCard(CharacterCardsType cardType, ImageView characterCardView){
        this.cardType = cardType;
        this.characterCardView = characterCardView;
        this.cost = cardType.getCost();

        characterCardView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ClientApplication.getSwitcher().goToCharacterCardView(CharacterCard.this);
            }
        });

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

    public void setNumberOfBans(int numberOfBans) {
        this.numberOfBans = numberOfBans;
    }

    public void setCoinOnCard(boolean coinOnCard) {
        this.coinOnCard = coinOnCard;
    }
}
