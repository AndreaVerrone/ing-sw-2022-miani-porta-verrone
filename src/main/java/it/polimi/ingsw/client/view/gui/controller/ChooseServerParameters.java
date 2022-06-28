package it.polimi.ingsw.client.view.gui.controller;

import it.polimi.ingsw.client.Translator;
import it.polimi.ingsw.client.view.gui.GuiScreen;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

/**
 * This class is the controller of the screen used to choose the server.
 */
public class ChooseServerParameters extends GuiScreen implements Initializable  {

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
        wrongIpErrorBox.setText("");
        wrongPortNumberErrorBox.setText("");

        // if both IP and port are correct send message
        if(parseIPAddress(ipAddress)&& parsePortNumber(portNumber)){
            // create connection
            getGui().getClientController().createConnection(ipAddress, Integer.parseInt(portNumber));
        }else{
            // there is at least one error --> sound emission
            System.out.print("\u0007");
            // if the IP is wrong:
            if(!parseIPAddress(ipAddress)) {
                // display that it is wrong and
                wrongIpErrorBox.setText(Translator.getWrongIPAddressMessage());
                // clear the text box
                serverIP.setText("");
            }
            // if the port is wrong:
            if(!parsePortNumber(portNumber)){
                // display that it is wrong and
                wrongPortNumberErrorBox.setText(Translator.getWrongPortNumberMessage());
                // clear the text box
                serverPort.setText("");
            }
        }
    }

    /**
     * This method is used to set all the labels.
     */
    private void setLabels(){
        headerLabel.setText(Translator.getChooseAServer());
        serverIPLabel.setText(Translator.getInsertIPAddress());
        serverPortLabel.setText(Translator.getInsertPortNumber());
    }

    /**
     * This method is called to check the validity of the IP address inserted by the user.
     * @param IPAddress the string inserted by the user to validate
     * @return true if the string inserted by the user is a valid IP address, false otherwise
     */
    private boolean parseIPAddress(String IPAddress){
        return Pattern.matches(
                "(([0-1]?\\d{1,2}\\.)|(2[0-4]\\d\\.)|(25[0-5]\\.)){3}(([0-1]?\\d{1,2})|(2[0-4]\\d)|(25[0-5]))",
                IPAddress
        );
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
