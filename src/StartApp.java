import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.databaseUtil;

import java.sql.Connection;

public class StartApp extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception{
            //Primary stage (mum automatiškai sugeneruojamas) -> STARTINIS LANGAS, nuo kurio prasideda programos gyvenimas.

//            Connection connection = databaseUtil.connectToDb();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/LoginPage.fxml"));

            Parent root = loader.load();

            primaryStage.setTitle("Nood.le");
            primaryStage.setScene(new Scene(root, 300, 400 ));
            primaryStage.show();
        }

    }

