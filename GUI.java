import java.util.ArrayList;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


public class GUI extends Application
{
	Library library = new Library();
	
	CheckOutWindow cow;
	CheckInWindow ciw;
	BorderPane mainPane;

	GUI gui;
	
	public Library getLib()
	{
		return library;
	}
	
	
	
	public static void main(String[] args) {
		Application.launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		gui = new GUI();
		ListView<InventoryItem> checkOutBottomList = new ListView<InventoryItem>();
		// check-in
		ArrayList<InventoryItem> checkInLeftListArray = new ArrayList<>();
		ListView<InventoryItem> checkInLeftList = new ListView<>();
		
//		ListView<InventoryItem> checkOutRightList = new ListView<>();
//		
//		ArrayList<Integer> checkedOut = new ArrayList<>();
//		ListView<InventoryItem> checkingIn = new ListView<>();
//
//		ListView<InventoryItem> checkInRightList = new ListView<>();
		
		mainPane = new BorderPane();
		HBox mainTop = new HBox();
		mainTop.getChildren().add(
				createOptions(checkOutBottomList, checkInLeftList, checkInLeftListArray) );
		mainPane.setTop(mainTop);
		
		cow = new CheckOutWindow(gui.getLib(), gui.getLib().getMaster(), checkInLeftListArray);
		mainPane.setCenter(cow.getCheckOutPane());
	
		ciw = new CheckInWindow(checkInLeftListArray);

		Scene scene = new Scene(mainPane, 900, 370);
		primaryStage.setTitle("CheckIn/CheckOut"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
	}
	
	public ComboBox<String> createOptions(ListView<InventoryItem> checkOutBottomList, ListView<InventoryItem> checkInLeftList,
			ArrayList<InventoryItem> checkInLeftListArray )
	{
		ComboBox<String> options = new ComboBox<>();
		String[] optionList = {"Check-out", "Check-in", "View List"};
		options.getItems().addAll(optionList);
		options.setValue("Check-out");
		
		options.setOnAction(e->{
			int i = options.getSelectionModel().getSelectedIndex();
			if (i == 0)
			{
				cow.clearCheckOutBotListArray();
				mainPane.setCenter(cow.getCheckOutPane());							
			}
			else if (i == 1)
			{
				// fill check-in array appropriately
				ciw.fillLeftList(checkInLeftListArray);
				mainPane.setCenter(ciw.getCheckedInPane());;
				
			}
			else if (i == 2)
			{
				Platform.runLater(()->
				{
					ViewWindow vw = new ViewWindow(gui.getLib().getMaster());
					mainPane.setCenter(vw.getViewListPane());
				});

			}
		});
		return options;
	} 

}
