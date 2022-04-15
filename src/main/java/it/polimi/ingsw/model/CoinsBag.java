package it.polimi.ingsw.model;

public class CoinsBag {

    private final int MAX_COINS = 20;

    /**
     * The number of coins available on the table.
     * The starting value is 20.
     */
    private int coinsAvailable = MAX_COINS;

    public int getCoinsAvailable() {
        return coinsAvailable;
    }

    public void takeCoin() throws NotEnoughCoinsException {
        if (coinsAvailable <= 0)
            throw new NotEnoughCoinsException();
        coinsAvailable --;
    }

    public void addCoins(int coins){
        assert coins > 0 : "You can't add a negative value of coins";
        assert coinsAvailable + coins <= MAX_COINS : "The coins added are too much";

        coinsAvailable += coins;
    }
}
