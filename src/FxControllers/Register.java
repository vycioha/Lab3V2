package FxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Course;
import model.User;

import java.io.IOException;
import java.util.ArrayList;

import static utils.UserRW.WriteUserDataToFile;

public class Register {

    public TextField usrField;
    public PasswordField passField;
    public Button regInitBut;
    public Button exitBut;
    public TextField surnameField;
    public TextField nameField;
    public TextField bankAccountField;
    public TextField yearField;

    public static boolean isParsable(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    public void RegisterUser(ActionEvent actionEvent) throws IOException {

        String firstName = nameField.getText();
        String surname = surnameField.getText();
        String username = usrField.getText();
        String password = passField.getText();
        Integer year = null;
        if(isParsable(yearField.getText()) ){
            year = Integer.parseInt(yearField.getText());
        }
        String bankAccount = bankAccountField.getText();
        if(firstName != null &&
                surname != null &&
                username != null &&
                password != null &&
                year != null &&
                bankAccount != null){
            User user = new User(firstName,
                    surname,
                    username,
                    password,
                    year,
                    new ArrayList<Course>(),
                    false,
                    new ArrayList<Course>(),
                    bankAccount);

            WriteUserDataToFile(user);

            BackToLoginScreenAfterRegistration();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);


            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Register form is filled incorrectly");
            alert.showAndWait();
        }
    }

    public void BackToLoginScreenAfterRegistration() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/LoginPage.fxml"));

        Parent root = loader.load();

        LoginPage loginPage = loader.getController();

        loginPage.UserWasSuccessfullyCreated();

        Stage stage = (Stage)exitBut.getScene().getWindow();
        stage.setTitle("Login page");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void BackToLoginScreen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/LoginPage.fxml"));

        Parent root = loader.load();

        Stage stage = (Stage)exitBut.getScene().getWindow();
        stage.setTitle("Login page");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
