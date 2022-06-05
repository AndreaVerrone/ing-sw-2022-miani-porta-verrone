package it.polimi.ingsw.server.controller.game.expert;

import it.polimi.ingsw.server.controller.NotValidArgumentException;
import it.polimi.ingsw.server.controller.NotValidOperationException;
import it.polimi.ingsw.server.controller.PlayerLoginInfo;
import it.polimi.ingsw.server.controller.game.Game;
import it.polimi.ingsw.server.controller.game.expert.card_observers.CoinOnCardObserver;
import it.polimi.ingsw.server.controller.game.expert.card_observers.StudentsOnCardObserver;
import it.polimi.ingsw.server.controller.game.expert.cards.CharacterCardsFactory;
import it.polimi.ingsw.server.model.player.Player;
import it.polimi.ingsw.server.model.utils.exceptions.NotEnoughCoinsException;

import java.util.Collection;
import java.util.Map;

/**
 * A class to handle the game when the user requested to use the expert rules.
 * This allows the character cards to be used, as well as coins.
 */
public class ExpertGame extends Game {

    /**
     * Max number of character cards allowed by the rules
     */
    private final int NUMBER_OF_CHARACTER_CARDS = 3;
    
    /**
     * Map that contains the three card of the game in expert mode, reachable through their type
     */
    private final Map<CharacterCardsType, CharacterCard> cards;

    /**
     * It is true, if the current player can use the character card.
     * <p>
     * Note that a player can use only one time a character card during its turn in the action phase
     */
    private boolean canUseCharacterCard=true;

    /**
     * Creates a new game that uses the expert rules.
     * @param players the players in the game
     */
    public ExpertGame(Collection<PlayerLoginInfo> players) {
        super(players);

        cards = CharacterCardsFactory.createRandomCards(NUMBER_OF_CHARACTER_CARDS, this);
    }

    /**
     * This method allow to conclude the usage of a character card by saving the fact that
     * the current player has used a character card during his turn and by making it pay for the usage.
     * @param card the card that has been used
     */
    public void effectEpilogue(CharacterCard card){

        // 1. set that the current player has used a character card
        canUseCharacterCard = false;

        // 2. take the cost of the card from the player
        boolean putInBagAllCoins = card.isUsed();
        int cost = card.getCost();
        try {
            getModel().getCurrentPlayer().removeCoins(cost,putInBagAllCoins);
        } catch (NotEnoughCoinsException e) {
            // todo: how to manage?
            // it is impossible
            // I have checked before
        }
    }

    /**
     * @throws NotValidOperationException {@inheritDoc}
     * @throws NotValidArgumentException {@inheritDoc}
     */
    @Override
    public void useCharacterCard(CharacterCardsType cardType) throws NotValidOperationException, NotValidArgumentException {
        if(!cards.containsKey(cardType)) throw  new NotValidArgumentException("Card not present!");

        //Get the card
        CharacterCard characterCard = cards.get(cardType);

        // current player
        Player currentPlayer = getModel().getCurrentPlayer();

        // check that the player can use it since it is the first time that he use a
        // character card during its turn
        if(!canUseCharacterCard){
            throw new NotValidOperationException("you have already used a character card during this turn");
        }

        // check that the player can use it since it has enough money
        if(currentPlayer.getCoins() < characterCard.getCost()){
            throw new NotValidOperationException("you have not enough coin");
        }

        getState().useCharacterCard(characterCard);
    }

    @Override
    public void endOfTurn() {
        // RESET ALL THE STANDARD STRATEGY
        getModel().resetStrategy();

        // RESET THE POSSIBILITY TO USE A CHARACTER CARD
        canUseCharacterCard = true;
    }

    // METHODS TO ALLOW ATTACHING AND DETACHING OF OBSERVERS ON CHARACTER CARDS IF ANY

    /**
     * This method allows to add the observer, passed as a parameter, on the character cards in expert mode.
     * @param observer the observer to be added
     */
    @Override
    public void addStudentsOnCardObserver(StudentsOnCardObserver observer){
        for(CharacterCard card: cards.values()){
            addStudentsOnCardObserver(observer);
        }
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the character cards in expert mode.
     * @param observer the observer to be removed
     */
    @Override
    public void removeStudentsOnCardObserver(StudentsOnCardObserver observer){
        for(CharacterCard card: cards.values()){
            removeStudentsOnCardObserver(observer);
        }
    }

    /**
     * This method allows to add the observer, passed as a parameter, on the character cards in expert mode.
     * @param observer the observer to be added
     */
    @Override
    public void addCoinOnCardObserver(CoinOnCardObserver observer){
        for(CharacterCard card: cards.values()){
            addCoinOnCardObserver(observer);
        }
    }

    /**
     * This method allows to remove the observer, passed as a parameter, on the character cards in expert mode.
     * @param observer the observer to be removed
     */
    @Override
    public void removeCoinOnCardObserver(CoinOnCardObserver observer){
        for(CharacterCard card: cards.values()){
            removeCoinOnCardObserver(observer);
        }
    }
}
