package cowpay;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
* Controller for the main GUI.
*/
public class MainWindow extends AnchorPane {
    @FXML 
    private ScrollPane scrollPane;
    @FXML 
    private VBox dialogContainer;
    @FXML 
    private TextField userInput;
    @FXML 
    private Button sendButton;

    private CowPay cowPay;

    //Taken from https://in.pinterest.com/pin/948781846489448568/
    private Image userImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/donkey.jpg"));
    //Taken from: https://www.four-paws.org/campaigns-topics/topics/farm-animals/10-facts-about-cattle
    private Image cowImage = new Image(this.getClass().getClassLoader().getResourceAsStream("images/cow.jpg"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the CowPay instance */
    public void setCowPay(CowPay c) {
        this.cowPay = c;
        dialogContainer.getChildren().add(
            DialogBox.getCowPayDialog(cowPay.welcome(), this.cowImage)
        );
    }


    @FXML
    private void handleUserInput() {

        String input = userInput.getText();

        if (!input.isEmpty()) {

            String response = this.cowPay.getResponse(input);
            dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, this.userImage),
                DialogBox.getCowPayDialog(response, this.cowImage)
            );

            userInput.clear();
        }
    }
}
