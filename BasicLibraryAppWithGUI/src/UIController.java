

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Created by Christopher on 2/2/2017.
 */
public class UIController  implements Initializable{

    //variables
    private Library library  = new Library();
    private InventoryItem item;
    private FileProcessor j;
    private  boolean fileLoaded = false;
    private File file;
    private boolean reload = false;

    //FXML varialbles
    @FXML
    private GridPane libraryUIGridPane;
    @FXML
    private TableColumn<?, ?> idColumn;
    @FXML
    private TableColumn<?, ?> nameColumn;
    @FXML
    private Button loadButton;
    @FXML
    private Button checkoutButton;
    @FXML
    private Button checkinButton;
    @FXML
    private TextArea textArea1;
    @FXML
    private ComboBox<String> comboBox1;
    @FXML
    private TextArea checkedOutTextArea;
    @FXML
    private Label selectedLabel;
    @FXML
    private Label checkOutLabel;

    //initialize methods
    @Override
    public void initialize(URL url, ResourceBundle rb){
        //disable buttons
        deactivate();
    }

    //Save method - is called each time a change of state (checkin/oyt occurs)
    void save(){
            if (fileLoaded) {
                j.writeData(library);
                writeToCheckOutTextArea();
            } else {
                Alert a = new Alert(Alert.AlertType.WARNING);
                a.setTitle("Library not found");
                a.setHeaderText(null);
                a.setContentText("Please select a file by clicking the 'Load' button");
                a.showAndWait();
            }
    }

    //Load method - called when 'Load File' buttons is clicked
    @FXML
     void load(ActionEvent e) {
            if (!fileLoaded) {
                loadFile();
            } else {
                clearComboBox();
                loadFile();
            }
    }

    //Check-out function - displays a confirmation box, runs the checkout method on the selected inventory item, saves changes to the file, and writes updated info to the text area
    @FXML
    void checkOutItem(ActionEvent event){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Check-out");
        a.setHeaderText(null);
        a.setContentText("Are you sure you want to check out this item?  Changes will be automatically saved to the current library file.");
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK){
            item.checkOut();
            save();
            writeToTextArea();
        }
    }
    //Check-in function - displays a confirmation box, runs the checkin method on the selected inventory item, saves changes to the file, and writes updated info to the text area
    @FXML
    void returnItem(ActionEvent event){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Check-in");
        a.setHeaderText(null);
        a.setContentText("Are you sure you want to return this item?  Changes will be automatically saved to the current library file.");
        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == ButtonType.OK){
            item.checkIn();
            save();
            writeToTextArea();
        }
    }

    //Called when a new combo-box item is selected - Populates the text area with information about the select inventory item
    @FXML
    void getItemInfo(ActionEvent event){
        writeToTextArea();
    }

    //Populates the text area with information about the select inventory item
    private void writeToTextArea() {
        if(!reload) {
            String selectedItemText = comboBox1.getSelectionModel().getSelectedItem().toString();
            int index = selectedItemText.indexOf(' ');
            String parsedID = selectedItemText.substring(0, index);
            String checkOutString;
            String dueDateString;

            item = library.getItemByID(parsedID);

            //write to Text Area
            textArea1.setText(item.toString());

            //set button states
            if (item.isCheckedOut()) {
                checkoutButton.setDisable(true);
                checkinButton.setDisable(false);
            } else {
                checkoutButton.setDisable(false);
                checkinButton.setDisable(true);
            }
        }
    }

    //Populate "Check out Text area" with a list of checked out items
    private void writeToCheckOutTextArea(){
        String info = "";
        for(InventoryItem i : library){
            if(i.isCheckedOut()){
                info += i.getName() + "(" + i.getType() + ") is checked out with the due date of " + i. getDueDate() + "\n";
            }
        }
        checkedOutTextArea.setText(info);
    }

    //load JSON file
    private void loadFile(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open JSON File");
            file = fileChooser.showOpenDialog(new Stage());
            if (file == null) {
            } else {
                fileLoaded = true;
                activate();
                j = new FileProcessor(file);
                library = j.processData();

                for (InventoryItem i : library) {
                    comboBox1.getItems().add(i.getID() + " : " + i.getName() + " : " + i.getType());
                }
                comboBox1.setPromptText("Select an item");
                reload = false;
                writeToCheckOutTextArea();
            }
        }catch(Exception e){
            deactivate();
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("File load error");
            a.setHeaderText(null);
            a.setContentText("Please load a properly formatted JSON file.");
            Optional<ButtonType> result = a.showAndWait();
        }
    }

    //clear combo box when a new file is loaded
    private void clearComboBox(){
        reload = true;
        textArea1.setText("");
        checkedOutTextArea.setText("");
        deactivate();
        comboBox1.setDisable(false);
        comboBox1.setPromptText("Reload Library");
        int sz = comboBox1.getItems().size();
        ObservableList l  = comboBox1.getItems();
        checkoutButton.setDisable(true);
        checkinButton.setDisable(true);
        l.clear();
    }

    //activate buttons once a file is loaded
    private void activate(){
        comboBox1.setDisable(false);
        textArea1.setDisable(false);
        checkedOutTextArea.setDisable(false);
        selectedLabel.setStyle("-fx-text-fill: white");
        checkOutLabel.setStyle("-fx-text-fill: white");
    }

    //deactivate buttons and hide text when no load is loaded or if a file load is cancelled
    private void deactivate(){
        comboBox1.setDisable(true);
        textArea1.setDisable(true);
        checkedOutTextArea.setDisable(true);
        selectedLabel.setStyle("-fx-text-fill:  SteelBlue");
        checkOutLabel.setStyle("-fx-text-fill:  SteelBlue");
    }
}

