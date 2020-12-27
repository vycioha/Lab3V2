package FxControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void RegisterUser(ActionEvent actionEvent) throws IOException {

        String firstName = nameField.getText();
        String surname = surnameField.getText();
        String username = usrField.getText();
        String password = passField.getText();
        Integer year = Integer.parseInt(yearField.getText());
        String bankAccount = bankAccountField.getText();

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
