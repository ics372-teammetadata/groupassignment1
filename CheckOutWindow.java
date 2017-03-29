import java.util.ArrayList;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;

public class CheckOutWindow
{
	BorderPane checkOutPane = new BorderPane();
	BorderPane bottom = new BorderPane();
	ArrayList<InventoryItem> checkOutBotListArray = new ArrayList<InventoryItem>();
	ListView<InventoryItem> checkOutBottomList = new ListView<>();
	ArrayList<InventoryItem> MList;
	

	GridPane checkOutTopRightGrid = new GridPane();
	Button CDButton = new Button("CD");
	Button DVDButton = new Button("DVD");
	Button BookButton = new Button("Book");
	Button MagButton = new Button("Magazine");
	ListView<Artist> checkOutArtistList;
	ListView<Author> checkOutAuthorList;
	ListView<CD> checkOutCDList;
	ListView<Book> checkOutBookList;
	ListView<Magazine> checkOutMagList;
	ListView<DVD> checkOutDVDList;
	ListView<Object> checkOutLeftList = new ListView<Object>(); // may change InventoryItem to Library
	
	ArrayList<InventoryItem> checkInArray; // used to update list in check-in window
	
	ListView<InventoryItem> checkOutRightList = new ListView<>();
	
	ArrayList<Artist> checkOutArtistArrayList;
	ArrayList<Author> checkOutAuthorArrayList;
	ArrayList<DVD> checkOutDVDArrayList;
	ArrayList<Magazine> checkOutMagArrayList;
	
	public CheckOutWindow(Library lib, ArrayList<InventoryItem> master,
			ArrayList<InventoryItem> checkInLeftListArray)
	{
		MList = master;
		checkInArray = checkInLeftListArray;
		
		BorderPane topOfCheckOut = new BorderPane();
		GridPane checkOutTopLeftGrid = new GridPane();
		
		checkOutArtistArrayList = lib.getArtistList();
		checkOutAuthorArrayList = lib.getAuthorList();
		checkOutDVDArrayList = lib.getDVDList();
		checkOutMagArrayList = lib.getMagList();
		checkOutDVDList = new ListView<DVD>(FXCollections.observableArrayList(checkOutDVDArrayList));
		checkOutMagList = new ListView<Magazine>(FXCollections.observableArrayList(checkOutMagArrayList));
		
		checkOutTopLeftGrid.add(CDButton, 0, 1);
		checkOutTopLeftGrid.add(DVDButton, 1, 1);
		checkOutTopLeftGrid.add(BookButton, 2, 1);
		checkOutTopLeftGrid.add(MagButton, 3, 1);
		topOfCheckOut.setLeft(checkOutTopLeftGrid);
		topOfCheckOut.setRight(checkOutTopRightGrid);
		checkOutPane.setTop(topOfCheckOut);
		
		// begin by setting the left list in the check-out window to artists
		checkOutLeftList = new ListView<Object>(FXCollections.observableArrayList(checkOutArtistArrayList));
		checkOutPane.setLeft(checkOutLeftList);
		
		defineButtons();
		
		checkOutPane.setBottom(bottom);
		// create empty checkOutBottomList
		checkOutBottomList = new ListView<InventoryItem>();
		checkOutBottomList.setPrefSize(700, 100);
		bottom.setLeft(checkOutBottomList);
		checkOutBotListArray = new ArrayList<InventoryItem>();
		
		pressLeftList();
		pressRightList();
		idSearch();
	}
	
	public void defineButtons()
	{
		// set the buttons to populate checkOutLeftList and have the rightList null
		CDButton.setOnAction(e ->{
			checkOutPane.setRight(null);
			checkOutLeftList.setItems(FXCollections.observableArrayList(checkOutArtistArrayList));
			checkOutPane.setLeft(checkOutLeftList);
		});
		
		DVDButton.setOnAction(e->{
			checkOutPane.setRight(null);
			checkOutLeftList.setItems(FXCollections.observableArrayList(checkOutDVDArrayList));
			checkOutPane.setLeft(checkOutLeftList);
		});
		
		BookButton.setOnAction(e ->{
			checkOutPane.setRight(null);
			checkOutLeftList.setItems(FXCollections.observableArrayList(checkOutAuthorArrayList));
			checkOutPane.setLeft(checkOutLeftList);
		});
		
		MagButton.setOnAction(e ->{
			checkOutPane.setRight(null);
			checkOutLeftList.setItems(FXCollections.observableArrayList(checkOutMagArrayList));
			checkOutPane.setLeft(checkOutLeftList);
		});
	}
	
	public void pressLeftList()
	{
		checkOutLeftList.getSelectionModel().selectedItemProperty().addListener(e ->
		{
			Object x = checkOutLeftList.getSelectionModel().getSelectedItem();
			if (x instanceof Artist)
			{
				checkOutRightList.setItems(FXCollections.observableArrayList( ((Artist)x).getCDList() ));
				checkOutPane.setRight(checkOutRightList);
			}
			else if (x instanceof Author)
			{
				checkOutRightList.setItems(FXCollections.observableArrayList(((Author)x).getBookList()));
				checkOutPane.setRight(checkOutRightList);
			}
			else if (x instanceof DVD)
			{
				if (!(((DVD)x).getDueDate() != null))
				{
					((NotBook)x).checkOut();
					checkOutBotListArray.add((DVD)x);
					checkOutBottomList.setItems(FXCollections.observableArrayList(checkOutBotListArray));
					getCell();
					checkInArray.add((DVD)x);  // adding the item to the check-in array
				}
			}
			else if (x instanceof Magazine)
			{
				if (!(((Magazine)x).getDueDate() != null))
				{
					((NotBook)x).checkOut();
					checkOutBotListArray.add((Magazine)x);
					checkOutBottomList.setItems(FXCollections.observableArrayList(checkOutBotListArray));
					getCell();
					checkInArray.add((Magazine)x);  // adding the item to the check-in array
				}
			}
			Platform.runLater(()->
			{    
				checkOutLeftList.getSelectionModel().clearSelection();
			});
		});
	}
	
	public void pressRightList()
	{
		checkOutRightList.getSelectionModel().selectedItemProperty().addListener(e->		// listener for list on right side of pane
		{
			InventoryItem x = checkOutRightList.getSelectionModel().getSelectedItem();
			if (x != null)
			{
				// if InventoryItem x is not checked out (does not have a due date) check out and 
				// put in checkOutBottomList and add to checkInLeftListArray
				// else output "Already in cart"
				if (x.getDueDate() == null)
				{
					// checkOut x
					if (x instanceof NotBook)
					{
						((NotBook)x).checkOut();
					}
					else if (x instanceof Book)
					{
						((Book)x).checkOut();
					}
					
					// add x to checkOutBottomList
					checkOutBotListArray.add(x);
					checkOutBottomList.setItems(FXCollections.observableArrayList(checkOutBotListArray));
					getCell();
					bottom.setLeft(checkOutBottomList);
					
					// add x to checkInLeftListArray		
					checkInArray.add(x);  // adding the item to the check-in array
				}
				
				// automatically deselects from list when clicked
				// in this way we may click on an item in the list twice in a row
				Platform.runLater(()->
				{    
					checkOutRightList.getSelectionModel().clearSelection();
				});
			}
		});
	}
	
	public void idSearch()
	{
		TextField tf = new TextField("enter id");
		Button go = new Button("GO");
		checkOutTopRightGrid.add(tf, 0, 1);
		checkOutTopRightGrid.add(go, 1, 1);
		// search by id
		go.setOnAction(e->{
			String id = tf.getText();
			try
			{
				// get user number id input
				InventoryItem obj;
				int index;
				
				// find user number id associated with master list
				for (index = 0; index < MList.size(); index++)
				{
					obj = MList.get(index);
					
					// if number id is in list and is already checked out (has a due date)
					// print "Already in cart"
					if (id.equals(obj.getID()) && obj.getDueDate() != null)
					{
						break;
					}
					// else if number id is in list and is not checked out (has no due date)
					// add to checkOutBottomList and add to checkInLeftListArray
					else if ( id.equals(obj.getID()) && obj.getDueDate() == null)
					{
						checkInArray.add(MList.get(index));  // adding the item to the check-in array
						
						// checking out item
						if (obj instanceof NotBook)
						{
							((NotBook)obj).checkOut();
						}
						else if (obj instanceof Book)
						{
							((Book)obj).checkOut();
						}
						
						// adding item to checkOutBottomList
						checkOutBotListArray.add(MList.get(index));
						checkOutBottomList.setItems(FXCollections.observableArrayList(checkOutBotListArray));
						getCell();
						bottom.setLeft(checkOutBottomList);
						break;
					}
				}

			}
			catch(NumberFormatException ex) // user didn't enter a number
			{
				
			}	
		});
	}
	
	public BorderPane getCheckOutPane()
	{
		return checkOutPane;
	}
	
	public void clearCheckOutBotListArray()
	{
		checkOutBotListArray.clear();
		checkOutBottomList.setItems(FXCollections.observableArrayList(checkOutBotListArray));
		
		bottom.setLeft(checkOutBottomList);
		checkOutPane.setBottom(bottom);
	}
	
	public void getCell()
	{
		checkOutBottomList.setCellFactory(new Callback<ListView<InventoryItem>, ListCell<InventoryItem>>()
		{
			/*
			 * note that it is expected for this call function to be called 16 times. this is standard for
			 * ListCell call, due to there being multiple rows of the list on the screen (plus extra off-screen), not what is
			 * currently filled with data
			 * http://stackoverflow.com/questions/18159303/listview-in-javafx-adds-multiple-cells
			 */
			@Override
			public ListCell<InventoryItem> call(ListView<InventoryItem> param)
			{
				return new ViewCell();
			}			
		});
	}
}
