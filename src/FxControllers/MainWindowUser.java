package FxControllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

//implements Initializable -> Inteface, kuris pradiniam fxml formos langui priskirs reikšmes.
public class MainWindowUser implements Initializable {

    public Button logOutBtn;
    public Text profName;
    public Text profSurname;
    public Text profUsername;
    public Text profYear;
    public Text profBank;
    private User user;
    public Text loggedAs;
    public Text setProfileInformation = null;

    public void setUser(User user){
        this.user = user;
    }

    public void setLoggedAs(String username){
        loggedAs.setText("logged as: " + username);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void LogOut() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../FXML/LoginPage.fxml"));

        Parent root = loader.load();

        Stage stage = (Stage)logOutBtn.getScene().getWindow();
        stage.setTitle("Login page");
        stage.setScene(new Scene(root));
        stage.show();
    }

}
