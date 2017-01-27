import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    private Library lib;
    private final String formatString = "%-10s\t%-50s\t%-30s";
    HashMap<String, Label> dueDates;

    @FXML
    private GridPane outputGrid;

    @FXML
    public void openFile(ActionEvent e){

    }

    @FXML
    public void save(ActionEvent e){
        Alert a = new Alert(Alert.AlertType.WARNING);
        a.setTitle("Saving not supported");
        a.setHeaderText(null);
        a.setContentText("This Feature is not yet supported");

        a.showAndWait();
    }

    @FXML
    public void close(ActionEvent e){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Closing Application");
        a.setHeaderText("You are about to close the application. Would you like to save your data?");
        Optional<ButtonType> confirmed = a.showAndWait();
        if (confirmed.get() == ButtonType.OK) save(new ActionEvent());
        Platform.exit();
    }

    @FXML
    public void checkout(ActionEvent e){
        custButton b = (custButton)e.getSource();
        if (lib.checkOut(b.getCustId())){
            dueDates.get(b.getCustId()).setText(lib.getDueDate(b.getCustId()).toString());

        }
        else {
            //Alert user that there was a checkout error
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Checkout Error");
            a.setHeaderText(null);
            a.setContentText("The chosen item is already checked out");
            a.showAndWait();
        }
    }

    public void checkin(ActionEvent e){
        custButton b = (custButton)e.getSource();
        lib.checkIn(b.getCustId());
        dueDates.get(b.getCustId()).setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lib = new Library();
        lib.init();
        dueDates = new HashMap<>();

        // Add label row to grid
        outputGrid.addRow(0, new Label("ID"),new Label("Media Type"), new Label("Title"), new Label("Artist/Author"), new Label("Due"));

        // Iterate through library items to populate grid
        for (int row = 1; lib.hasNext(); row++){
            // retrieve item data
            String[] data = lib.get(lib.next().toString()).split(";");

            // create custom checkout/checkin buttons
            custButton checkoutButton = new custButton("Check Out", data[0]);
            checkoutButton.setOnAction(this::checkout);
            custButton checkinButton = new custButton("Check In", data[0]);
            checkinButton.setOnAction(this::checkin);

            // add dueDate label to dueDates hashmap
            dueDates.put(data[0], new Label(""));

            outputGrid.addRow(row, new Label(data[0]),new Label(lib.type(data[0])), new Label(data[1]), new Label(data.length == 3 ? data[2] : null), dueDates.get(data[0]),checkoutButton, checkinButton);
        }
    }
}
