import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class CheckInWindow 
{
	BorderPane checkedInPane = new BorderPane();
	Button confirm = new Button("confirm");
	//ArrayList<InventoryItem> master;
	ArrayList<InventoryItem> pending = new ArrayList<>();
	ArrayList<InventoryItem> checkInObjects = new ArrayList<>();
	ListView<InventoryItem> checkInRightList = new ListView<>();
	ListView<InventoryItem> checkInLeftList = new ListView<>();
	ArrayList<InventoryItem> checkInLeftListArray;
	
	public CheckInWindow(ArrayList<InventoryItem> checkInLeftListArray)
	{
		// checkInLeftListArray is filled from checkOutWindow
		this.checkInLeftListArray = checkInLeftListArray;
		BorderPane confirmPane = new BorderPane();

		// create confirm button and set in check-in
		confirmPane.setRight(confirm);
		checkedInPane.setBottom(confirmPane);

		setConfirmButton();	
		setCheckInLeftListListener();
	}
	
	public void fillLeftList(ArrayList<InventoryItem> checkInLeftListArray)
	{
		checkInLeftList.setItems(FXCollections.observableArrayList(checkInLeftListArray));
		checkedInPane.setLeft(checkInLeftList);
	}
	
	public BorderPane getCheckedInPane()
	{
		return checkedInPane;
	}
	
	public void setConfirmButton()
	{
		// confirm button
		// when pressed all objects in checkInRightList will be checked in (dueDate == null)
		// and removed from checkInRightList and removed from pending
		confirm.setOnAction(e->
		{

			for (int i = 0; i < pending.size(); i++)
			{
					pending.get(i).setDueDate(null);         // when we press confirm the dueDate is set to null
			}

			pending.clear();
			checkInObjects.clear();
			
			checkInRightList.setItems(FXCollections.observableArrayList(checkInObjects));
			checkInRightList.setCellFactory(new Callback<ListView<InventoryItem>, ListCell<InventoryItem>>()
			{
				/*
				 * note that it is expected for this call function to be called 16 times. this is standard for
				 * ListCell call, due to there being multiple rows of the list on the screen (plus extra off-screen), not what is
				 * currently filled with data
				 * http://stackoverflow.com/questions/18159303/listview-in-javafx-adds-multiple-cells
				 */
				@Override
				public ListCell<InventoryItem> call(ListView<InventoryItem> arg0)
				{
					return getCCell();
				}
		
			});
			checkedInPane.setCenter(checkInRightList);
		});
	}
	
	public void setCheckInLeftListListener()
	{
		// when pressing checkInLeftList, the item goes to checkInRightList
		checkInLeftList.getSelectionModel().selectedItemProperty().addListener(e->
		{
			
			InventoryItem x = checkInLeftList.getSelectionModel().getSelectedItem();
			// I think you can't remove from the list while the selectionModel is
			// changing the selected items (during clicking on the list), but if you 
			// use Platform.runLater the program will remove things at a later time
			// http://stackoverflow.com/questions/27769583/calling-clear-on-observablelist-causes-indexoutofboundsexception
			Platform.runLater(()->
			{
				checkInLeftListArray.remove(x);
				checkInLeftList.setItems(FXCollections.observableArrayList(checkInLeftListArray));
			});
			
			if (x != null)
			{
				checkInObjects.add(x);
				checkInRightList.setItems(FXCollections.observableArrayList(checkInObjects));
				
				checkInRightList.setCellFactory(new Callback<ListView<InventoryItem>, ListCell<InventoryItem>>()
				{
					/*
					 * note that it is expected for this call function to be called 16 times. this is standard for
					 * ListCell call, due to there being multiple rows of the list on the screen (plus extra off-screen), not what is
					 * currently filled with data
					 * http://stackoverflow.com/questions/18159303/listview-in-javafx-adds-multiple-cells
					 */
					@Override
					public ListCell<InventoryItem> call(ListView<InventoryItem> arg0)
					{
						return getCCell();
					}
			
				});
				checkedInPane.setCenter(checkInRightList);
			}
		});	
	}
	
	public ListCell<InventoryItem> getCCell()
	{
		CheckInCell cInCell = new CheckInCell();
		cInCell.setPending(pending);
		cInCell.setCheckInLeftListArray(checkInLeftListArray);
		cInCell.setCheckInLeftList(checkInLeftList);
		cInCell.setCheckInRightList(checkInRightList);
		cInCell.setCheckInObjects(checkInObjects);
		return cInCell;
	}
}
