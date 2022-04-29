package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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
        try {
            coinsBag.takeCoin();
        } catch (NotEnoughCoinsException e) {
            fail();
        }
        assertEquals(19, coinsBag.getCoinsAvailable());
    }

    @Test
    public void takeCoin_WithEmptyBag_ShouldThrow(){
        try{
            for (int i = 0; i < 20; i++) {
                coinsBag.takeCoin();
            }
        } catch (NotEnoughCoinsException e){
            fail();
        }
        assertThrows(NotEnoughCoinsException.class,
                () -> coinsBag.takeCoin());
    }

    @Test
    public void addCoins_WithPositiveCorrectValue_ShouldAdd(){
        try{
            coinsBag.takeCoin();
            coinsBag.takeCoin();
        } catch (NotEnoughCoinsException e){
            fail();
        }
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