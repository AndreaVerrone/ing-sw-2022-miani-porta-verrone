package it.polimi.ingsw.controller;

public abstract class UseCharacterCardState {

    /**
     * This is the Game class
     * @see Game
     */
    private final Game game;

    /**
     * This is the state from which the character card has been used and
     * this is the state in which the game have to return after the usage of the card.
     */
    private final State originState;

    /**
     * The character card that uses this state
     */
    private final CharacterCard characterCard;

    /**
     * The constructor of the class.
     * @param game the Game class
     * @param originState the state from which the character card has been used
     * @param characterCard the character card that uses this state
     */
    protected UseCharacterCardState(Game game, State originState, CharacterCard characterCard) {
        this.game = game;
        this.originState = originState;
        this.characterCard = characterCard;
    }

    /**
     * This method allow to finalize the usage of the character card.
     */
    public void finalizeCardUsed(){
        game.effectEpilogue(characterCard);
        characterCard.setAsUsed();
    }

    /**
     * This method allows to go back to the state at which the character card has been used.
     */
    public void returnBack(){
        game.setState(originState);
    }

}
