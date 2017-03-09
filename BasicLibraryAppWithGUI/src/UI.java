import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created by Christopher on 2/2/2017.
 */
public class UI extends Application{
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("UI.fxml"));
        GridPane grid = loader.load();
        UIController controller = loader.getController();
        primaryStage.getIcons().add(new Image("/images.jpg"));
        primaryStage.setTitle("Basic Library System");
        Scene scene = new Scene(grid);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}
