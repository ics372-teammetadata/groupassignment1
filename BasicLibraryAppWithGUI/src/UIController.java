

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.net.URL;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

/**
 * Created by Christopher on 2/2/2017.
 **/
public class UIController  implements Initializable{

    //variables
    private Library library  = new Library();
    private InventoryItem item;
    private FileProcessor loadedJsonFile;
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
    private TextArea mainTextAreaForInventoryItemDescription;
    @FXML
    private ComboBox<String> comboBoxForInventoryItemSelection;
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
        deactivateUIElements();
    }

    //////////////////////////
    // Action Event Methods
    /////////////////////////

    //Load method - called when 'Load Library File' button is clicked
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

    ////////////
    // Methods
    ////////////

    /**
     *      Method name:  displayWarning()
     *      Displays a warning message to the user
     *      @param title
     *      @param text
     */
    private void displayWarning(String title, String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();
    }


    /**
     *      Method name:  loadFile()
     *      This method is called when 'Load Library File' button is clicked in the UI
     *      Loads JSON files
     *      Uses FileChooser class to select a file
     *      Catches parse exceptions (incorrect filetype or poorly formatted JSON data)
     *      Instantiates a FileProcessor object and calls it's processJSONData method which processes JSON file data and generates library items from JSON object info and returns a Library list
     *      Loops through the Library (library) list and adds an entry for each InventoryItem to the comboBoxForInventoryItemSelection
     **/
    private void loadFile(){
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open JSON File");
            file = fileChooser.showOpenDialog(new Stage());
            if (file == null) {
                //deactivateUIElements buttons and hide text
                deactivateUIElements();
            } else {
                fileLoaded = true;
                activateUIElements();
                loadedJsonFile = new FileProcessor(file);
                library = loadedJsonFile.processJSONData();

                //Loops through the Library (library) list and adds an entry to each InventoryItem to the comboBoxForInventoryItemSelection
                for (InventoryItem i : library) {
                    comboBoxForInventoryItemSelection.getItems().add(i.getID() + " : " + i.getName() + " : " + i.getType());
                }
                comboBoxForInventoryItemSelection.setPromptText("Select an item");
                reload = false;
                writeToCheckOutTextArea();
            }
        }catch(ParseException e){
            deactivateUIElements();
            displayWarning("File load error","An incorrect file type was detected.  Please load a properly formatted JSON file.");
        }catch(DateTimeParseException e){
            deactivateUIElements();
            displayWarning("File load error","An improperLy formated Date was detected.  Please load a properly formatted JSON file.");
        }
    }

    //Populates the text area, using the InventoryItem's (and/or child item's) toString method,
    // with information about InventoryItem that has been selected in the comboBoxForInventoryItemSelection
    private void writeToTextArea() {
        if(!reload) {
            String selectedItemText = comboBoxForInventoryItemSelection.getSelectionModel().getSelectedItem();
            int index = selectedItemText.indexOf(' ');
            String parsedID = selectedItemText.substring(0, index);
            String checkOutString;
            String dueDateString;

            item = library.getItemByID(parsedID);

            //write to Text Area
            mainTextAreaForInventoryItemDescription.setText(item.toString());

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

    //Populates "Check out Text area" with a list of checked out items
    private void writeToCheckOutTextArea(){
        String info = "";
        for(InventoryItem item : library){
            if(item.isCheckedOut()){
                info += item.getName() + "(" + item.getType() + ") is checked out with the due date of " + item. getDueDate() + "\n";
            }
        }
        checkedOutTextArea.setText(info);
    }

    //Save method - is called each time a change of state (checkin/oyt occurs)
    //Calls the FileProcessor (loadedJsonFile) writeJSONData method,
    // which writes data to JSON file and loops through Library list, adding each InventoryItem to a JSON array
    private void save(){
        if (fileLoaded) {
            loadedJsonFile.writeJSONData(library);
            writeToCheckOutTextArea();
        } else {
            displayWarning("Library not found", "Please select a file by clicking the 'Load' button");
        }
    }

    ////////////////////
    // UI house keeping
    ////////////////////

    //Clears the comboBoxForInventoryItemSelection when a new file is loaded
    private void clearComboBox(){
        reload = true;
        mainTextAreaForInventoryItemDescription.setText("");
        checkedOutTextArea.setText("");
        deactivateUIElements();
        comboBoxForInventoryItemSelection.setDisable(false);
        comboBoxForInventoryItemSelection.setPromptText("Reload Library");
        int sz = comboBoxForInventoryItemSelection.getItems().size();
        ObservableList l  = comboBoxForInventoryItemSelection.getItems();
        checkoutButton.setDisable(true);
        checkinButton.setDisable(true);
        l.clear();
    }

    //Activates buttons once a file is loaded
    private void activateUIElements(){
        comboBoxForInventoryItemSelection.setDisable(false);
        mainTextAreaForInventoryItemDescription.setDisable(false);
        checkedOutTextArea.setDisable(false);
        selectedLabel.setStyle("-fx-text-fill: white");
        checkOutLabel.setStyle("-fx-text-fill: white");
    }

    //Deactivates buttons and hide text when no load is loaded or if a file load is cancelled
    private void deactivateUIElements(){
        comboBoxForInventoryItemSelection.setDisable(true);
        mainTextAreaForInventoryItemDescription.setDisable(true);
        checkedOutTextArea.setDisable(true);
        selectedLabel.setStyle("-fx-text-fill:  SteelBlue");
        checkOutLabel.setStyle("-fx-text-fill:  SteelBlue");
    }
}

