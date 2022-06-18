package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.ClientApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * This class is the controller of the screen used to choose the server.
 */
public class ChooseServerParameters implements Initializable {

    /**
     * label for the header of the screen.
     */
    @FXML
    Label headerLabel;

    /**
     * label to ask to insert IP address.
     */
    @FXML
    Label serverIPLabel;

    /**
     * label to ask to insert port number.
     */
    @FXML
    Label serverPortLabel;

    /**
     * text field used to allow the user to insert the IP address.
     */
    @FXML
    TextField serverIP;

    /**
     * text field used to allow the user to insert the port number.
     */
    @FXML
    TextField serverPort;

    /**
     * label used to display the error message if the IP address inserted is wrong.
     */
    @FXML
    Label wrongIpErrorBox;

    /**
     * label used to display the error message if the port number inserted is wrong.
     */
    @FXML
    Label wrongPortNumberErrorBox;

    // this method is called when the scene is opened
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // set labels
        setLabels();
    }

    /**
     * This method is called when the {@code OK} button has been pressed.
     * It will send the IP and the port number inserted by the user if they are valid,
     * otherwise an error message will be displayed.
     */
    public void getServerParameters(){

        // USER INPUTS
        // the IP address written
        String ipAddress = serverIP.getText();
        // the port number written
        String portNumber = serverPort.getText();

        // reset to void the labels for errors
        // LABELS TO DIPLAY ERROR MESSAGES

        wrongIpErrorBox.setText("");
        // label for wrong port number
        wrongPortNumberErrorBox.setText("");

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


    /**
     * This method is used to set all the labels.
     */
    public void setLabels(){
        // todo: add translation
        headerLabel.setText("Choose a server");
        serverIPLabel.setText("Insert IP address of the server");
        serverPortLabel.setText("Insert Port Number of the server");
    }



    /**
     * This method is called to check the validity of the IP address inserted by the user.
     * @param IPAddress the string inserted by the user to validate
     * @return true if the string inserted by the user is a valid IP address, false otherwise
     */
    private boolean parseIPAddress(String IPAddress){
        return Pattern.matches(
                "(([0-1]?[0-9]{1,2}\\.)|(2[0-4][0-9]\\.)|(25[0-5]\\.)){3}(([0-1]?[0-9]{1,2})|(2[0-4][0-9])|(25[0-5]))",
                IPAddress
        );

        /* ALTERNATIVE METHOD
        if(Pattern.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}", IPAddress)){
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

    /**
     * This method is called to check the validity of the port number inserted by the user.
     * @param portNumber the string inserted by the user to validate
     * @return true if the string inserted by the user is a valid port number, false otherwise
     */
    private boolean parsePortNumber(String portNumber){
        return Pattern.matches("\\d{4,5}", portNumber);
    }

}
