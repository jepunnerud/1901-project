package ui;

import core.main.User;
import data.DataHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    private DataHandler datahandler;

    @FXML
    private AnchorPane loginAnchor;

    @FXML
    private Button loginButton, createNewUserButton;

    @FXML
    private TextField loginField;

    @FXML
    private void initialize() {
        if (!(Globals.windowHeight == 0)) {
            loginAnchor.setPrefSize(Globals.windowWidth, Globals.windowHeight);
        }
        datahandler = new DataHandler();
        createWindowSizeListener();
    }

    @FXML
    private void handleLoginButton(ActionEvent ae) throws IOException {
        User user = datahandler.getUser(loginField.getText());
        Globals.user = user;
        switchScreen(ae, "Script.fxml");
    }

    @FXML
    private void handleCreateNewUserButton(ActionEvent ae) throws IOException {
        try {
            User user = new User(loginField.getText());
            datahandler.write(user);
            Globals.user = user;
            switchScreen(ae, "Script.fxml");
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Invalid username");
            alert.setHeaderText("Username can only contain characters A-Z, a-z, 0-9, _ and .");
            alert.show();
            if (!loginField.getText().isBlank()) {
                User user = datahandler.getUser(loginField.getText());
                if (!(user == null)) {
                    Globals.user = user;
                    switchScreen(ae, "Script.fxml");
                } else {
                    newUser(ae);
                }
            }
        }
    }

    @FXML
    private void handleEnter(KeyEvent ke) {
        if (ke.getCode().equals(KeyCode.ENTER)) {
            if (!loginButton.isDisabled()) {
                loginButton.fire();
            }
            if (!createNewUserButton.isDisabled()) {
                createNewUserButton.fire();
            }
        }
    }

    private void newUser(ActionEvent ae) throws IOException {
        User user = new User(loginField.getText());
        datahandler.write(user);
        Globals.user = user;
        switchScreen(ae, "Script.fxml");
    }

    private void switchScreen(ActionEvent ae, String file) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(file));
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private Boolean checkIfBlankUsername() {
        return loginField.getText().isBlank();
    }

    @FXML
    private void checkUsername() {
        createNewUserButton.setDisable(
                (!(!checkIfBlankUsername() && datahandler.getUser(loginField.getText()) == null)));
        loginButton.setDisable(
                (!(!checkIfBlankUsername() && datahandler.getUser(loginField.getText()) != null)));
    }

    private void createWindowSizeListener() {
        loginAnchor.widthProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowWidth = (double) newVal;
        });
        loginAnchor.heightProperty().addListener((obs, oldVal, newVal) -> {
            Globals.windowHeight = (double) newVal;
        });
    }

    public Button getLoginButton() {
        return loginButton;
    }
}