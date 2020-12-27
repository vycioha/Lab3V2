import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

    public class StartApp extends Application {

        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception{
            //Primary stage (mum automatiškai sugeneruojamas) -> STARTINIS LANGAS, nuo kurio prasideda programos gyvenimas.

            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML/LoginPage.fxml"));

            Parent root = loader.load();

            primaryStage.setTitle("Nood.le");
            primaryStage.setScene(new Scene(root, 300, 400 ));
            primaryStage.show();
        }

        //TEST
        //TEST

    }

