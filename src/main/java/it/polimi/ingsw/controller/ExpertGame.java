package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.NotEnoughCoinsException;
import it.polimi.ingsw.model.player.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A class to handle the game when the user requested to use the expert rules.
 * This allows the character cards to be used, as well as coins.
 */
public class ExpertGame extends Game{

    /**
     * Map that contains the three card of the game in expert mode, reachable through their type
     */
    private final Map<CharacterCardsType, CharacterCard> cards  = new HashMap<>(3, 1);

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

        createThreeRandomCards();
    }

    /**
     * Create the three initial cards of the game
     */
    private void createThreeRandomCards(){
        CharacterCardsFactory cardsFactory = new CharacterCardsFactory(this);
        while (cards.size() < 3){
            int random = new Random().nextInt(CharacterCardsType.values().length);
            CharacterCardsType cardType = CharacterCardsType.values()[random];
            cards.putIfAbsent(cardType, cardsFactory.chooseCard(cardType));
        }
    }

    public boolean getCanUseCharacterCard() {
        return canUseCharacterCard;
    }

    public void setCanUseCharacterCard(boolean canUseCharacterCard) {
        this.canUseCharacterCard = canUseCharacterCard;
    }

    /**
     * This method allow to conclude the usage of a character card by saving the fact taht
     * the current player has used a character card during his turn and by making it pay for the usage.
     * @param card the card that has been used
     */
    public void effectEpilogue(CharacterCard card){

        // 1. set that the current player has used a character card
        setCanUseCharacterCard(false);

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
        if(!getCanUseCharacterCard()){
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
        setCanUseCharacterCard(true);
    }
}
