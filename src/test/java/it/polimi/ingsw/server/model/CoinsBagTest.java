package it.polimi.ingsw.server.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CoinsBagTest {

    CoinsBag coinsBag;

    @BeforeEach
    void setUp() {
        coinsBag = new CoinsBag();
    }

    @AfterEach
    void tearDown() {
        coinsBag = null;
    }

    @Test
    public void takeCoin_WithEnoughCoins_ShouldRemove(){
        coinsBag.takeCoin();
        assertEquals(19, coinsBag.getCoinsAvailable());
    }

    @Test
    public void takeCoin_WithEmptyBag_ShouldDoNothing(){

        for (int i = 0; i < 20; i++) {
            coinsBag.takeCoin();
        }
        assertEquals(0,coinsBag.getCoinsAvailable());
    }

    @Test
    public void addCoins_WithPositiveCorrectValue_ShouldAdd(){
        coinsBag.takeCoin();
        coinsBag.takeCoin();
        int oldCoins = coinsBag.getCoinsAvailable();
        coinsBag.addCoins(1);
        assertEquals(oldCoins+1, coinsBag.getCoinsAvailable());
    }

    @Test
    public void addCoins_WithNegativeValue_ShouldThrow(){
        assertThrows(AssertionError.class,
                () -> coinsBag.addCoins(-100));
    }

    @Test
    public void addCoins_WithPositiveExcessiveValue_ShouldThrow(){
        assertThrows(AssertionError.class,
                () -> coinsBag.addCoins(100));
    }
}