package it.polimi.ingsw.model;

/**
 * A class describing the 20 coins in the game.
 */
public class CoinsBag {

    /**
     * The maximum amount of coins that can exist in a game.
     */
    private final int MAX_COINS = 20;

    /**
     * The number of coins available on the table.
     * The starting value is 20.
     */
    private int coinsAvailable = MAX_COINS;

    public int getCoinsAvailable() {
        return coinsAvailable;
    }

    /**
     * Takes a coin from this bag.
     * @throws NotEnoughCoinsException if this bag is empty
     */
    public void takeCoin() throws NotEnoughCoinsException {
        if (coinsAvailable <= 0)
            throw new NotEnoughCoinsException();
        coinsAvailable --;
    }

    /**
     * Adds {@code coins} in this bag.
     * <p>
     * The {@code coins} can't be negative and the number of coins in this bag can't exceed 20.
     * @param coins the number of coins to add
     */
    public void addCoins(int coins){
        assert coins > 0 : "You can't add a negative value of coins";
        assert coinsAvailable + coins <= MAX_COINS : "The coins added are too much";

        coinsAvailable += coins;
    }
}
