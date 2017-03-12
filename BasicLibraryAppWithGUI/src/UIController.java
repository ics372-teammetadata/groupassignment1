import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeParseException;
import java.util.*;

import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * Created by Christopher on 2/2/2017.
 **/
public class UIController implements Initializable{

    //variables
    private Library library  = new Library();
    private InventoryItem item;
    private FileProcessor loadedJsonFile = null;
    private FileProcessor loadedXMLFile = null;
    private boolean fileLoaded = false;
    private File file = null;
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

    /**
     * Method name:  initialize()
     *
     * Initializes the UI by calling the deactivateUIElements() method
     * @param url : default parameter passed to initialize method
     * @param rb : default parameter passed to initialize method
     */

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //disable buttons
        deactivateUIElements();
    }

    /**
     * Method name:  load()
     * Calls the chooseLibrary() method
     * @param e : Event triggered when "Load Library" button is clicked
     */

    @FXML
     public void load(ActionEvent e) {
            if (!fileLoaded) {
                chooseLibrary();
            } else {
                clearComboBox();
                chooseLibrary();
            }
    }

    /**
     * Method name:  chooseLibrary()
     * Popup is called when the "Load Library" button is clicked
     * Allows the user to select between the JSON or XML library
     */

    private void chooseLibrary(){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle("Choose Library");
        a.setHeaderText("Choose which library file you would like to load");
        a.setContentText("Choose your option.");
        ButtonType parentLibraryButton = new ButtonType("This library");
        ButtonType sisterLibraryButton = new ButtonType("Sister Library");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        a.getButtonTypes().setAll(parentLibraryButton, sisterLibraryButton, buttonTypeCancel);

        Optional<ButtonType> result = a.showAndWait();
        if (result.get() == parentLibraryButton){
            loadJSONFile();
        } else if (result.get() == sisterLibraryButton) {
            loadXMLFile();
        } else {
            //user clicked the "Cancel" button
        }
    }
     /**
     * Method name:  checkOutItem()
     * Displays a confirmation box, runs the checkout method on the selected inventory item, saves changes to the file, and writes updated info to the text area
     *
     * @param event : Checkout button is clicked
     */

    @FXML
    public void checkOutItem(ActionEvent event){
        Optional result = basicConfirmationWarning("Check-out", "Are you sure you want to check out this item?  Changes will be automatically saved to the current library file.");
        if (result.get() == ButtonType.OK){
            item.checkOut();
            save();
            writeToTextArea();
        }
    }

    /**
     * Method name:  returnItem()
     * Displays a confirmation box, runs the checkin method on the selected inventory item, saves changes to the file, and writes updated info to the text area
     *
     * @param event : Checkin button is clicked
     */

    @FXML
    public void returnItem(ActionEvent event){
        Optional result = basicConfirmationWarning("Check-in", "Are you sure you want to return this item?  Changes will be automatically saved to the current library file.");
        if (result.get() == ButtonType.OK){
            item.checkIn();
            save();
            writeToTextArea();
        }
    }

    /**
     * Method name:  getItemInfo()
     * Populates the text area with information about the select inventory item
     *
     * @param event : Action is triggered when a new combo-box item is selected
     */

    @FXML
    public void getItemInfo(ActionEvent event){
        writeToTextArea();
    }

    /**
     * Method name:  displayWarning()
     * Displays a warning message to the user
     *
     * @param title :  Alert title
     * @param text : Alert text
     */

    private void displayWarning(String title, String text){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        Optional<ButtonType> result = alert.showAndWait();
    }

    /**
     * Method name:  basicConfirmationWarning()
     *
     * @param title :  Alert title
     * @param text : Alert text
     */
    private Optional basicConfirmationWarning(String title, String text){
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(text);
        Optional<ButtonType> result = a.showAndWait();
        return result;
    }

    /**
     * Method name:  loadJSONFile()
     * This method is called when 'Load Library File' button is clicked in the UI
     * Loads JSON files
     * Uses FileChooser class to select a file
     * Catches parse exceptions (incorrect filetype or poorly formatted JSON data)
     * Instantiates a FileProcessor object and calls it's processJSONData method which processes JSON file data and generates library items from JSON object info and returns a Library list
     * Loops through the Library (library) list and adds an entry for each InventoryItem to the comboBoxForInventoryItemSelection
     */

    private void loadJSONFile(){
        //prevent saving to wrong file
        loadedXMLFile = null;
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

                writeToItemSelectionComboBox();
                writeToCheckOutTextArea();
            }
        }catch(ParseException e){
            deactivateUIElements();
            displayWarning("File load error","An incorrect file type was detected.  Please load a properly formatted JSON file.");
        }catch(DateTimeParseException e){
            deactivateUIElements();
            displayWarning("File load error","An improperLy formated Date was detected.  Please load a properly formatted JSON file.");
        }catch(FileNotFoundException e){
            deactivateUIElements();
            displayWarning("File load error (FileNotFoundException)","File not found  Please load a properly formatted JSON file.");
        } catch(IOException e){
            deactivateUIElements();
            displayWarning("File load error (IOException)","There was an error when loading the file. Please load a properly formatted JSON file.");
        }
    }

    /**
     * Method name:  loadXMLFile()
     * This method is called when 'Load Library File' button is clicked in the UI
     * Loads XML library files
     * Uses FileChooser class to select a file
     * Catches parse exceptions (incorrect filetype or poorly formatted XML data)
     * Instantiates a FileProcessor object and calls it's processXMLata method which processes XML file data and generates library items and returns a Library list
     * Loops through the Library (library) list and adds an entry for each InventoryItem to the comboBoxForInventoryItemSelection
     */

    private void loadXMLFile(){
        try {
            //prevent saving to wrong file
            loadedJsonFile = null;

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open XML File");
            file = fileChooser.showOpenDialog(new Stage());
            if (file == null) {
                //deactivateUIElements buttons and hide text
                deactivateUIElements();
            } else {
                fileLoaded = true;
                activateUIElements();
                loadedXMLFile = new FileProcessor(file);
                library = loadedXMLFile.processXMLData();

                writeToItemSelectionComboBox();
                writeToCheckOutTextArea();
            }
        }catch(ParserConfigurationException e){
            deactivateUIElements();
            displayWarning("File load error (ParserConfigurationException)","An improperLy formated file was detected.  Please load a properly formatted XML file.");
        }catch(SAXException e){
            deactivateUIElements();
            displayWarning("File load error (SAXException)","An improperLy formated file was detected.  Please load a properly formatted XML file.");
        }catch(IOException e){
            deactivateUIElements();
            displayWarning("File load error (IOException)","An improperLy formated file was detected.  Please load a properly formatted XML file.");
        }catch(DateTimeParseException e){
            deactivateUIElements();
            displayWarning("File load error","An improperLy formated Date was detected.  Please load a properly formatted XML file.");
        }
    }

    /**
     * Method name:  writeToItemSelectionComboBox()
     * Populates the itemSelection combo box with library item info
     */

    private void writeToItemSelectionComboBox(){
        //Loops through the Library (library) list and adds an entry to each InventoryItem to the comboBoxForInventoryItemSelection
        for (InventoryItem i : library) {
            comboBoxForInventoryItemSelection.getItems().add(i.getID() + " : " + i.getName() + " : " + i.getType());
        }
        comboBoxForInventoryItemSelection.setPromptText("Select an item");
        reload = false;
    }

    /**
     * Method name: writeToTextArea()
     * Populates the text area, using the InventoryItem's (and/or child item's) toString method,
     * with information about InventoryItem that has been selected in the comboBoxForInventoryItemSelection
    */

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

    /**
     * Method name: writeToCheckOutTextArea()
     * Populates "Check out Text area" with a list of checked out items
     */

    private void writeToCheckOutTextArea(){
        String info = "";
        for(InventoryItem item : library){
            if(item.isCheckedOut()){
                info += item.getName() + "(" + item.getType() + ") is checked out with the due date of " + item. getDueDate() + "\n";
            }
        }
        checkedOutTextArea.setText(info);
    }

    /**
     * Method name save()
     * Called each time a change of state (checkin/out occurs)
     * Calls the FileProcessor writeJSONData() or writeXMLData() methods
     * which writes data to JSON file or XML and loops through Library list, adding each InventoryItem
     */

    private void save(){
        if (loadedJsonFile!= null) {
            try {
                loadedJsonFile.writeJSONData(library);
                writeToCheckOutTextArea();
            }catch(IOException e){
                displayWarning("File save error (IOException)","The file was unable to be saved.");
            }
        }else if(loadedXMLFile != null){
            try {
                loadedXMLFile.writeXMLData(library);
            }catch(ParserConfigurationException e){
                displayWarning("File save error (ParserConfigurationException)","The file was unable to be saved.");
            }catch(TransformerException e){
                displayWarning("File save error (TransformerException)","The file was unable to be saved.");
            }
            writeToCheckOutTextArea();
        }
        else {
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

