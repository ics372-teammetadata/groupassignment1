import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by Christopher on 2/2/2017.
 */
public class UI2 extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("UI2.fxml"));
        TabPane grid = loader.load();
        UIController controller = loader.getController();
        primaryStage.getIcons().add(new Image("/images.jpg"));
        primaryStage.setTitle("Library System 2.0");
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
