package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.view.gui.GuiScreen;
import it.polimi.ingsw.server.model.player.Assistant;

public class UseAssistantView extends GuiScreen {

    public void useAssistant(){
        getGui().getClientController().useAssistant(Assistant.CARD_1);
    }
}
