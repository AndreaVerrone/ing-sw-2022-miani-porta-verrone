package it.polimi.ingsw.server.controller.game.expert.cards;

import it.polimi.ingsw.client.reduced_model.ReducedCharacter;
import it.polimi.ingsw.server.controller.game.expert.CharacterCard;
import it.polimi.ingsw.server.controller.game.expert.CharacterCardsType;
import it.polimi.ingsw.server.controller.game.expert.ExpertGame;
import it.polimi.ingsw.server.controller.game.expert.card_states.UseCharacterCard5State;
import it.polimi.ingsw.server.observers.game.table.BanRemovedFromIslandObserver;

public class CharacterCard5 extends CharacterCard implements BanRemovedFromIslandObserver {

    /**
     * This is the Game class
     * @see ExpertGame
     */
    private final ExpertGame game;

    /**
     * the number of ban cards that are present on the island.
     */
    private int numOfBans;
    /**
     * Creates a new character card.
     * @param game the Game class
     */
    CharacterCard5(ExpertGame game) {
        super(CharacterCardsType.CARD5);
        this.game=game;
        numOfBans=4;

        // attach the character card 5 to the list of the observers of invocation of conquerIsland method of GameModel
        game.getModel().addConquerIslandObserver(this);
    }

    @Override
    protected ReducedCharacter reduce() {
        return new ReducedCharacter(getCardType(), isUsed(), numOfBans);
    }

    @Override
    public void effect() {
        game.setState(new UseCharacterCard5State(game,game.getState(),this));
    }

    public int getNumOfBans() {
        return numOfBans;
    }

    /**
     * This method allow to add a ban on the card.
     * <p>
     * Note:
     * The number of bans on this card cannot be more than 4
     */
    private void addBan() {
        assert numOfBans<=4 : "bans are too much";
        this.numOfBans++;
    }

    /**
     * This method allow to remove a bran from the card
     */
    public void removeBan() {
        if(numOfBans<=0){
            numOfBans=0;
        }else {
            this.numOfBans--;
        }
    }

    /**
     * This method is the {@code update()} method of the observer pattern.
     * It is called by the subject in order to notify a change to all its attached observers.
     */
    @Override
    public void conquerIslandObserverUpdate() {
        // if the method conquerIsland has been invoked when there was a ban on the island
        // the ban should be removed by the island and added to the card
        addBan();
    }
}
