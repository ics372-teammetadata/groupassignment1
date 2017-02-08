import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.util.Callback;

// CheckInCell is used to create a new cell in checkInRightList
// where all items will have name on left side and dueDate and
// and X button on right side of cell
// the xButton is used to move items from checkInRightList to checkInLeftList
class CheckInCell extends ListCell<InventoryItem>
{
	// hboxCheckIn holds the name, a pane (for spacing), dueDate and X button
	// name holds name of item
	// spacing is used to separate name from dueDate and xButton
	// xButton is is used to move items from checkInRightList to checkInLeftList
	// due is the due date of the item
	// last item is for simplicity as it is the item received from updateItem parameter
	HBox hboxCheckIn = new HBox();
	Label name = new Label();
	Label artOrAut = new Label();
	Pane spacing = new Pane();
	Button xButton = new Button("X");
	Label due = new Label();
	//InventoryItem theItem;
	
	ArrayList<InventoryItem> pending = new ArrayList<>();
	ArrayList<InventoryItem> checkInLeftListArray = new ArrayList<>();
	ListView<InventoryItem> checkInLeftList = new ListView<>();
	ArrayList<InventoryItem> checkInObjects = new ArrayList<>();
	ListView<InventoryItem> checkInRightList = new ListView<>();
	
	// constructor
	public CheckInCell()
	{
		super();
		hboxCheckIn.getChildren().addAll(name, artOrAut, spacing);
		HBox.setHgrow(spacing,  Priority.ALWAYS); // Priority.ALWAYS causes spacing to have priority and split up name
	}											  // from due and xButton, thus getting a physically wider space between them
	
	// used by checkInLeftList by removing InventoryItem item, which is the object clicked on in checkInLeftList
	// and giving it to checkInRightList with appropriate cell
	// may also move back from checkInRightList to checkInLeftList with xButton
	// note: moving object to checkInRightList does not check in item. That requires the object be 
	// in checkInRightList and pressing confirm button
	public void updateItem(InventoryItem theItem, boolean empty)
	{
		super.updateItem(theItem, empty);
		// updateItem is run at multiple different stages so we limit removals to only when item != null
		// and when it does not already contain due date
		// this solves the duplicate children problem
		if (theItem != null && !hboxCheckIn.getChildren().contains(due))
		{
			name.setMinWidth(170);
			// adds object's properties to hboxCheckIn which is used in checkInRightList
			// hboxCheckIn instance cannot have the same due and xButton instances added to it more than once
			// (no duplicate children), but it must be added once. Seeing as how updateItem is called 16 times
			// we bypass this by only entering if this if statement
			// for some reason updateItem is called even when item == null
			due.setText(theItem.getDueDate().toString());  
			hboxCheckIn.getChildren().addAll(due, xButton);
			
			setPending(theItem);
			
			// set all changes to hboxCheckIn to a graphic display
			setGraphic(hboxCheckIn);
		}
		setXButtion(theItem);
	}
	
	public void setPending(InventoryItem theItem)
	{
		// get name for cell and add object to pending so it may be
		// moved back and forth or wait to be checked out by confirm button
		if (!pending.contains(theItem))  // only add lastItem to pending if it is not already there
		{								  // updateItem function will update at different times
			pending.add(theItem);
		}
		
		/*
		these instanceof's can't go into
		the above if. If they do, their text will
		not be set. This is due to setCellFactory in CheckInWindow.
		*/
		if (theItem instanceof CD)
		{
			name.setText( ((CD)theItem).getName());
			artOrAut.setText(((CD)theItem).getArtistName());
		}
		else if (theItem instanceof DVD)
			name.setText( ((DVD)theItem).getName() );
		else if (theItem instanceof Magazine)
			name.setText(((Magazine)theItem).getName());
		else if (theItem instanceof Book)
		{
			name.setText(((Book)theItem).getName());
			artOrAut.setText(((Book)theItem).getAuthorName());
		}
	}
	
	public void setXButtion(InventoryItem theItem)
	{
		// xButton to move object from checkInRightList to checkInLeftList
		// also removes object from pending
		xButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e)
			{
				checkInLeftListArray.add(theItem);
				checkInLeftList.setItems(FXCollections.observableArrayList(checkInLeftListArray));
				
				checkInObjects.remove(theItem);
				pending.remove(theItem); 
				
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
						CheckInCell cInCell = new CheckInCell();
						cInCell.setPending(pending);
						cInCell.setCheckInLeftListArray(checkInLeftListArray);
						cInCell.setCheckInLeftList(checkInLeftList);
						cInCell.setCheckInRightList(checkInRightList);
						cInCell.setCheckInObjects(checkInObjects);
						return cInCell;
					}
			
				});
			}
		});
	}
	
	public void setPending(ArrayList<InventoryItem> pending)
	{
		this.pending = pending;
	}
	
	public void setCheckInLeftListArray(ArrayList<InventoryItem> checkIn)
	{
		checkInLeftListArray = checkIn;
	}
	
	public void setCheckInLeftList(ListView<InventoryItem> checkInLeftList)
	{
		this.checkInLeftList = checkInLeftList;
	}
	
	public void setCheckInObjects(ArrayList<InventoryItem> checkInObjects)
	{
		this.checkInObjects = checkInObjects;
	}
	
	public void setCheckInRightList(ListView<InventoryItem> checkInRightList)
	{
		this.checkInRightList = checkInRightList;
	}
	
}