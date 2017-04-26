import com.metadata.LibraryDomain.InventoryItem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Andrew on 4/23/2017.
 */
public class DetailsController {

    // Links to items in ui
    @FXML
    private TableView<InventoryItemWrapper> listTable;
    @FXML
    private TableColumn<InventoryItemWrapper, String> nameColumn;
    @FXML
    private TableColumn<InventoryItemWrapper, String> authorColumn;
    @FXML
    private TableColumn<InventoryItemWrapper, String> typeColumn;
    @FXML
    private TableColumn<InventoryItemWrapper, String> statusColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label authorLabel;
    @FXML
    private Label miscLabel;
    @FXML
    private Label miscStatusLabel;
    @FXML
    private Button checkinCheckoutButton;
    @FXML
    private Button statusButton;

    @FXML
    Label userDisplay;

    // Information on currently logged in user
    private String userName;
    private String userID;
    private PrivilegeType privilege;

    private ObservableList<InventoryItemWrapper> itemList;

    // Called by UI when setting up Details.fxml
    public DetailsController(String userName, String userID, PrivilegeType privilege){
        this.userName = userName;
        this.userID = userID;
        this.privilege = privilege;
    }

    // sets up UI for initial display (before anything is selected)
    void initialize(){
        userDisplay.setText(String.format("Logged in as %s", userName));
        checkinCheckoutButton.setDisable(true);

        itemList = FXCollections.observableArrayList();

        InventoryItem item;

        // Populate list of items
        for (int i = 0; i < UI.localLib.size(); i++){
            item = UI.localLib.get(i);
            itemList.add(new InventoryItemWrapper(item.getID(),"local", item.getName(), item.getAuthor(), item.getType(), item.getStatus()));
        }
        for (int i = 0; i < UI.offsiteLib.size(); i++){
            item = UI.offsiteLib.get(i);
            itemList.add(new InventoryItemWrapper(item.getID(), "offsite", item.getName(), item.getAuthor(), item.getType(), item.getStatus()));
        }

        // set cell factories
        nameColumn.setCellValueFactory(cellData-> cellData.getValue().itemNameProperty());
        authorColumn.setCellValueFactory(cellData -> cellData.getValue().itemArtistProperty());
        typeColumn.setCellValueFactory(cellData -> cellData.getValue().itemTypeProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().itemStatusProperty());

        // hook into tableview's selection
        listTable.getSelectionModel().selectedItemProperty().addListener(
                ((observable, oldValue, newValue) -> displayItemData(newValue))
        );

        listTable.setItems(itemList);

        clearItemData();

        if (privilege == PrivilegeType.Staff){
            statusButton.setVisible(true);
            statusButton.setDisable(true);
        }
        else statusButton.setVisible(false);
    }

    // display the data in the currently selected item
    private void displayItemData(InventoryItemWrapper item){
        InventoryItem i;
        if(item.getItemLocation() == "local") i = UI.localLib.getItemByID(item.getItemId());
        else i = UI.offsiteLib.getItemByID(item.getItemId());

        nameLabel.setText(i.getName() + ((i.getVolume()==null||i.getVolume().equals(""))?"": " v" + i.getVolume()));
        authorLabel.setText(item.getItemArtist());
        typeLabel.setText(i.getType());
        statusButton.setDisable(false);
        if ( item.getItemStatus().equals("Checked Out")) {// item is checked out and unavailable
            if (i.isCheckedOut()) {
                miscLabel.setText("Due Date: ");
                miscStatusLabel.setText(i.getDueDate());
                checkinCheckoutButton.setText("Check In");
                checkinCheckoutButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        checkinItem();
                    }
                });
                checkinCheckoutButton.setDisable(false);
            }
        }
        else { // item is either checked in or some other state
            miscLabel.setText("Status");
            if (item.getItemStatus().equals("Available")){
                miscStatusLabel.setText("Available");
                checkinCheckoutButton.setDisable(false);
            }
            else {
                miscStatusLabel.setText(item.getItemStatus());
                checkinCheckoutButton.setDisable(true);
            }
            if (i.getStatus().equals("Available")){
                checkinCheckoutButton.setText("Check Out");
                checkinCheckoutButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        checkoutItem();

                    }
                });
            }
        }


    }

    // empties data fields
    private void clearItemData(){
        nameLabel.setText("");
        authorLabel.setText("");
        typeLabel.setText("");
        miscLabel.setText("Status");
        miscStatusLabel.setText("");
    }

    // Returns the InventoryItem represented by the currently selected row
    private InventoryItem getSelectedItem(){
        InventoryItemWrapper itemWrapper = listTable.getSelectionModel().getSelectedItem();
        String itemId = itemWrapper.getItemId();
        return (itemWrapper.getItemLocation().equals("local"))?UI.localLib.getItemByID(itemId):UI.offsiteLib.getItemByID(itemId);
    }

    // check in the currently selected item
    private void checkinItem(){
        InventoryItemWrapper itemWrapper = listTable.getSelectionModel().getSelectedItem();
        String itemId = itemWrapper.getItemId();
        InventoryItem item = getSelectedItem();
        // only allow checkin if user is the one that checked out item or is a staff memeber
        if (userID.equals(item.getCheckedOutToUserCardNumber())||privilege==PrivilegeType.Staff) {
            item.checkIn();
            itemWrapper.setItemStatus("Available");
            displayItemData(itemWrapper);
            updateFile();
        }
        else {
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setTitle("Wrong user");
            a.setHeaderText(null);
            a.setContentText("Items can only be checked in by the user that checked them out or a staff member");
            a.showAndWait();
        }
    }

    // write current data to file
    private void updateFile() {
        InventoryItemWrapper itemWrapper = listTable.getSelectionModel().getSelectedItem();
        try {
            if (itemWrapper.getItemLocation().equals("local"))UI.localProcessor.writeJSONData(UI.localLib);
            else UI.offsiteProcessor.writeXMLData(UI.offsiteLib);
        }catch (IOException e){
            showFileWriteError("local");
        } catch (ParserConfigurationException |TransformerException e) {
            showFileWriteError("offsite");
        }
    }

    // Check out the currently selected item
    private void checkoutItem(){
        InventoryItemWrapper itemWrapper = listTable.getSelectionModel().getSelectedItem();
        String itemId = itemWrapper.getItemId();
        InventoryItem item = getSelectedItem();
        item.checkOut(userID);
        itemWrapper.setItemStatus("Checked Out");
        displayItemData(itemWrapper);
        updateFile();
    }

    @FXML // Staff only action that changes the status of an item that is not currently checked out
    private void changeItemStatus(){
        InventoryItemWrapper itemWrapper = listTable.getSelectionModel().getSelectedItem();
        if (itemWrapper.getItemStatus().equals("Checked Out")){
            Alert a = new Alert(Alert.AlertType.WARNING);
            a.setContentText("Item's status cannot be changed until it has been checked in");
            a.setHeaderText(null);
            a.setTitle("Warning");
            a.showAndWait();
        }
        else {
            InventoryItem item = getSelectedItem();

            List<String> choices = new ArrayList<>();
            choices.add("Available");
            choices.add("Missing");
            choices.add("Shelving");
            choices.add("Removed from Circulation");
            choices.add("Reference Only");
            ChoiceDialog<String> choiceDialog = new ChoiceDialog<>("Available",choices);
            choiceDialog.setTitle("Set Status");
            choiceDialog.setHeaderText(null);
            choiceDialog.setContentText("New status:");

            Optional<String> result = choiceDialog.showAndWait();
            if (result.isPresent()){
                item.setStatus(result.get());
                itemWrapper.setItemStatus(result.get());
            }
            displayItemData(itemWrapper);
            updateFile();
        }
    }

    // display error if unable to write to file
    private void showFileWriteError(String libLocation){
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle("Critical Error");
        a.setHeaderText(null);
        a.setContentText(String.format("Unable to write information to %s library file. Changes made during this session cannot be saved.\nPlease check your files or contact your administrator", libLocation));
        a.showAndWait();
        Platform.exit();
    }

    @FXML // log out user
    private void Logout() {
        UI.showLoginPage();
    }

}
