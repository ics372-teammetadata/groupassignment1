
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.File;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Library Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        final FileChooser fileChooser = new FileChooser();

        final Button openButton = new Button("Load Library File");

        openButton.setOnAction(
                new EventHandler<ActionEvent>() {
                    public void handle(final ActionEvent e) {
                        File file = fileChooser.showOpenDialog(primaryStage);
                        if (file != null) {
                            FileProcessor j = new FileProcessor();
                        }

                    }
                });
            primaryStage.show();
    }

    public static void main(String[] args) {
        Library library  = new Library();
        FileProcessor j = new FileProcessor();

        //collect data form file and populate library
        library = j.processData();
        launch(args);
    }
}

