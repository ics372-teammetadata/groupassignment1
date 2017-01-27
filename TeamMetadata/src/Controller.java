import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable{
    Library lib;

    @FXML
    VBox itemBox;

    @FXML
    public void openFile(ActionEvent e){

    }

    @FXML
    public void save(ActionEvent e){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Feature not supported");
        a.setHeaderText(null);
        a.setContentText("This Feature is not yet supported");

        a.showAndWait();
    }

    @FXML
    public void close(ActionEvent e){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Closing Application");
        a.setHeaderText("You are about to close the application. Be sure to save your data.");
        Optional<ButtonType> confirmed = a.showAndWait();
        if (confirmed.get() == ButtonType.OK) Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lib = new Library();
        lib.init();
        lib.printLibrary();
    }
}
