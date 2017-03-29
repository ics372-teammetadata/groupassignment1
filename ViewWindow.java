import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

class ViewWindow
	{
		// VIEW LIST
		BorderPane viewListPane = new BorderPane();
		ListView<InventoryItem> viewList = new ListView<>();
		//ArrayList<InventoryItem> masterList = new ArrayList<>();
		
		public ViewWindow(ArrayList<InventoryItem> masterList)
		{
			// create viewList and fill with master list
			//Collections.sort(masterList, InventoryItem.Comparator);
			viewList.setItems(FXCollections.observableArrayList(masterList));
			getCell();
			viewListPane.setCenter(viewList);
		}
		
		public BorderPane getViewListPane()
		{
			return viewListPane;
		}
		
		public void getCell()
		{
			viewList.setCellFactory(new Callback<ListView<InventoryItem>, ListCell<InventoryItem>>()
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