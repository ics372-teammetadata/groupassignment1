import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class UI extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("UI.fxml"));
        primaryStage.setTitle("Media Library");
        primaryStage.setScene(new Scene(root, 600, 300));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}