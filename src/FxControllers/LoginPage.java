package FxControllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static utils.UserRW.ReadUser;

public class LoginPage implements Initializable {

    public TextField usrField;
    public PasswordField passField;
    public Button logBut;
    public Button regBut;
    public Button exitBut;

    private User loggedUser = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void validateUser(ActionEvent actionEvent) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        boolean userIsValid = false;
        String username = usrField.getText();
        String password = passField.getText();

        loggedUser = ReadUser(username);

        if(loggedUser != null){
            userIsValid = loggedUser.getPsw().equals(password);

            if(!userIsValid){
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.setContentText("wrong username or password");

                alert.showAndWait();
            }
            else{
                if(loggedUser.isModerator()){
                    LoadMainWindowAdmin();
                }
                else{
                    LoadMainWindowUser();
                }
            }
        }
        else{
            alert.setTitle(null);
            alert.setHeaderText(null);
            alert.setContentText("user does not exist");

            alert.showAndWait();
        }
    }

    public void ProgramOff(){
        Platform.exit();
    }

    public void LoadRegForm() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/Register.fxml"));

        Parent root = loader.load();

        Stage stage = (Stage)regBut.getScene().getWindow();
        stage.setTitle("Registration window");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void UserWasSuccessfullyCreated(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(null);
        alert.setHeaderText(null);
        alert.setContentText("User was created successfully");

        alert.showAndWait();
    }

    private void LoadMainWindowAdmin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/MainWindowAdmin.fxml"));

        Parent root = loader.load();

        MainWindowAdmin mainWindow = loader.getController();

        mainWindow.setUser(loggedUser);
        mainWindow.setLoggedAs(loggedUser.getLogin());

        Stage stage = (Stage)logBut.getScene().getWindow();
        stage.setTitle("Admin window");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private void LoadMainWindowUser() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/MainWindowUser.fxml"));

        Parent root = loader.load();

        MainWindowUser mainWindow = loader.getController();

        mainWindow.setUser(loggedUser);

        Stage stage = (Stage)logBut.getScene().getWindow();
        stage.setTitle("User window");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
