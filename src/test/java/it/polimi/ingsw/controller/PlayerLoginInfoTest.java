package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.TowerType;
import it.polimi.ingsw.model.player.Wizard;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerLoginInfoTest {

    PlayerLoginInfo playerLoginInfo = null;

    @BeforeEach
    void setUp() {
        playerLoginInfo = new PlayerLoginInfo("Player1");
    }

    @AfterEach
    void tearDown() {
        playerLoginInfo = null;
    }

    @Test
    void constructorNickname_ShouldSet(){
        assertEquals("Player1",playerLoginInfo.getNickname());
    }

    @Test
    void setWizard_W1_shouldSet(){
        playerLoginInfo.setWizard(Wizard.W1);
        assertEquals(Wizard.W1,playerLoginInfo.getWizard());
    }

    @Test
    void setTowerType_BLACK_shouldSet(){
        playerLoginInfo.setTowerType(TowerType.BLACK);
        assertEquals(TowerType.BLACK,playerLoginInfo.getTowerType());
    }
}