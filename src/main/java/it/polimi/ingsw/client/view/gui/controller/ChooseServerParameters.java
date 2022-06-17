package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

import java.util.regex.Pattern;

public class ChooseServerParameters {

    @FXML
    Label headerLabel;

    @FXML
    Label serverIPLabel;

    @FXML
    Label serverPortLabel;

    @FXML
    TextField serverIP;

    @FXML
    TextField serverPort;

    @FXML
    Label wrongIpErrorBox;

    @FXML
    Label wrongPortNumberErrorBox;

    @FXML
    AnchorPane backgroundPane;


    public void setLabels(){
        // todo: add translation
        headerLabel.setText("Choose a server");
        serverIPLabel.setText("Insert IP address of the server");
        serverPortLabel.setText("Insert Port Number of the server");

        // backgroundPane.setBackground(Background.fill(Color.CYAN));
    }

    public void getServerParameters(){

        String ipAddress = serverIP.getText();
        String portNumber = serverPort.getText();

        wrongPortNumberErrorBox.setText("");
        wrongIpErrorBox.setText("");

        if(parseIPAddress(ipAddress)&& parsePortNumber(portNumber)){
            System.out.println("connecting to: "+serverIP.getText() + "" + serverPort.getText());
            ClientApplication.getSwitcher().goToHomeScreen();
            // getClientController().createConnection(ipAddress, Integer.parseInt(portNumber)); // todo: similar to actual code
        }else{
            if(!parseIPAddress(ipAddress)) {
                wrongIpErrorBox.setText("IP address not valid");
                serverIP.setText("");
                System.out.println("ERROR");
            }
            if(!parsePortNumber(portNumber)){
                wrongPortNumberErrorBox.setText("port number not valid");
                serverPort.setText("");
                System.out.println("ERROR");
            }
        }
    }

    private boolean parseIPAddress(String IPAddress){
        return Pattern.matches("(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))",IPAddress);

        /*if(Pattern.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}", IPAddress)){
            String[] elements = IPAddress.split(",",0);
            for (String element : elements) {
                int number = Integer.parseInt(element);
                if (number < 0 || number > 255) {
                    return false;
                }
            }
            return true;
        }
        return false;*/
    }

    private boolean parsePortNumber(String portNumber){
        return Pattern.matches("\\d{4,5}", portNumber);
    }

}
