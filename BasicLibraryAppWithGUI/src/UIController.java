

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Created by Christopher on 2/2/2017.
 */
public class UIController  implements Initializable{

    private Library library  = new Library();
    private InventoryItem item;
    private FileProcessor j;  // = new FileProcessor();
    private  boolean fileLoaded = false;
    private File file;
    private boolean reload = false;


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

    @Override
    public void initialize(URL url, ResourceBundle rb){
        //textArea1.setText("CLick the 'Load' button to load a library file and use the dropdown menu to select an item.");

    }

    //Actions
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
    @FXML
     void load(ActionEvent e) {
            if (!fileLoaded) {
                loadFile();
            } else {
                clearComboBox();
                loadFile();
            }
    }
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
    @FXML
    void getItemInfo(ActionEvent event){
        writeToTextArea();
    }

    private void writeToTextArea() {
        if(!reload) {
            String selectedItemText = comboBox1.getSelectionModel().getSelectedItem().toString();
            int index = selectedItemText.indexOf(' ');
            String parsedID = selectedItemText.substring(0, index);
            String checkOutString;
            String dueDateString;

            item = library.getItemByID(parsedID);

            if(item.isCheckedOut()){
                checkOutString = "Item is checked out\n";
            } else {
                checkOutString = "Item is checked in\n";
            }
            if(item.getDueDate() == null){
                dueDateString = "";
            } else {
                dueDateString = "Item is due on " + item.getDueDate() + "\n";
            }

            textArea1.setText("ID : " + item.getID() + "\n" +
                    "Item : " + item.getName() + "\n" +
                    "Type : " + item.getType() + "\n" +
                    checkOutString +
                    dueDateString );
            if (item.isCheckedOut()) {
                checkoutButton.setDisable(true);
                checkinButton.setDisable(false);
            } else {
                checkoutButton.setDisable(false);
                checkinButton.setDisable(true);
            }
        }
    }
    private void writeToCheckOutTextArea(){
        String info = "";
        for(InventoryItem i : library){
            if(i.isCheckedOut()){
                info += i.getName() + "(" + i.getType() + ") is checked out with the due date of " + i. getDueDate() + "\n";
            }
        }
        checkedOutTextArea.setText(info);
    }

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
                //library.viewList();

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

    private void clearComboBox(){
        //comboBox1.valueProperty().set(null);
        //comboBox1.getItems().removeAll();
        reload = true;
        textArea1.setText("");
        checkedOutTextArea.setText("");
        comboBox1.setPromptText("Reload Library");
        int sz = comboBox1.getItems().size();
        ObservableList l  = comboBox1.getItems();
        checkoutButton.setDisable(true);
        checkinButton.setDisable(true);
        l.clear();
    }

    private void activate(){
        comboBox1.setDisable(false);
        textArea1.setDisable(false);
        checkedOutTextArea.setDisable(false);
    }
    private void deactivate(){
        comboBox1.setDisable(true);
        textArea1.setDisable(true);
        checkedOutTextArea.setDisable(true);
    }
}

