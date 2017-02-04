
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javafx.util.Callback;
import javafx.scene.control.ListCell;

public class GUI extends Application
{
	ListView<ListOfItems> list;
	ListView<InventoryItem> secList = new ListView<>();
	ListView<InventoryItem> bottomList = new ListView<InventoryItem>();;
	ArrayList<InventoryItem> bl = new ArrayList<InventoryItem>();
	ArrayList<Integer> checkedOut = new ArrayList<>();
	ArrayList<InventoryItem> master = new ArrayList<InventoryItem>();
	ListView<InventoryItem> checkingIn = new ListView<>();
	ObservableList<InventoryItem> checkingInObs;
	
	int index;
	
	item abc;
	
	// check-in
	ArrayList<InventoryItem> notIn = new ArrayList<>();
	ObservableList<InventoryItem> notInView;
	ListView<InventoryItem> lv = new ListView<>();
	
	ListView<InventoryItem> checkInRightList = new ListView<>();
	
	
	//==================================================
	class ViewCell extends ListCell<InventoryItem>
	{
		HBox hbox = new HBox();
		Label label = new Label();
		Pane pane = new Pane();
		InventoryItem lastItem;
		Label due = new Label();
		
		public ViewCell()
		{
			super();
			hbox.getChildren().addAll(label, pane);
			HBox.setHgrow(pane,  Priority.ALWAYS);

		}
		
		public void updateItem(InventoryItem it, boolean empty)
		{
			super.updateItem(it, empty);
			int value = 0;
			if (it != null)
				if (it.getDueDate() != null)
				{
					due.setText(it.getDueDate().toString());
					hbox.getChildren().addAll(due);
				}
				lastItem = it;
				if (lastItem instanceof item)
					label.setText( ((item)it).getName() );
				else if (lastItem instanceof otherItem)
					label.setText( ((otherItem)it).getName() );
				setGraphic(hbox);
		}
	}

	//===========================================================================
	
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		lv.onEditStartProperty();
		BorderPane mainPane = new BorderPane();
		
		BorderPane checkOutPane = new BorderPane();
		
		BorderPane top = new BorderPane();
		
		GridPane grid = new GridPane();
		
		ComboBox<String> file = new ComboBox<>();
		String[] options = {"Check-out", "Check-in", "View List"};
		file.getItems().addAll(options);
		file.setValue("Check-out");
		mainPane.setTop(file);
		mainPane.setCenter(checkOutPane);
		
		// CHECKOUT
		
		Button loiButton = new Button("tools");
		Button loi2Button = new Button("food");
		grid.add(loiButton, 0, 1);
		grid.add(loi2Button, 1, 1);
		
		GridPane grid2 = new GridPane();
		TextField tf = new TextField("enter id");
		Button go = new Button("GO");
		grid2.add(tf, 0, 1);
		grid2.add(go, 1, 1);
		
		top.setLeft(grid);
		top.setRight(grid2);
		checkOutPane.setTop(top);
		
		
		ListOfItems loi = new ListOfItems("tools");
		item h = new item(1, "hammer");
		item n = new item(2, "nail");
		loi.addToList(h);
		loi.addToList(n);

		ListOfItems loi2 = new ListOfItems("Tools2");
		item sc = new item(5, "scissors");
		item p = new item(6, "paper");
		loi2.addToList(sc);
		loi2.addToList(p);

		
		ArrayList<ListOfItems> arrList = new ArrayList<ListOfItems>();
		arrList.add(loi);
		arrList.add(loi2);
		
		
		
		
		// otherItem
		ListOfItems otherItemList = new ListOfItems("OtherItems");
		otherItem car = new otherItem(10, "car");
		otherItem truck = new otherItem(11, "truck");
		otherItemList.addToList(car);
		otherItemList.addToList(truck);

		arrList.add(otherItemList);
		
		for (int i = 0; i < arrList.size(); i++)
		{
			master.addAll(arrList.get(i).getListOfItems());
		}
		
//		for (int i = 0; i < arrList.size(); i++)
//		{
//			for (int pb = 0; pb < arrList.get(i).getListOfItems().size(); pb++)
//				System.out.println(arrList.get(i).getListOfItems().get(pb));
//		}
//		for (int i = 0; i < master.size(); i++)
//		{
//			System.out.println(master.get(i));
//		}
		
		list = new ListView<ListOfItems>(FXCollections.observableArrayList(arrList));
		
		
		ListOfItems foodList = new ListOfItems("food");
		item s = new item(3, "strawberry");
		item b = new item(4, "banana");
		foodList.addToList(s);
		foodList.addToList(b);
		
		ArrayList<ListOfItems> arrList2 = new ArrayList<ListOfItems>();
		arrList2.add(foodList);
		
		for (int i = 0; i < arrList2.size(); i++)
		{
			master.addAll(arrList2.get(i).getListOfItems());
		}
		
		checkOutPane.setLeft(list);
		
		loiButton.setOnAction(e ->{
			checkOutPane.setRight(null);
			//list = new ListView<ListOfItems>(FXCollections.observableArrayList(arrList)); // IF YOU MAKE A NEW LIST THE PROGRAM LOSES SIGHT OF
																							// THE LISTENER FOR SOME REASON
			list.setItems(FXCollections.observableArrayList(arrList));
			checkOutPane.setLeft(list);
		});
		
		loi2Button.setOnAction(e->{
			checkOutPane.setRight(null);
			//list = new ListView<ListOfItems>(FXCollections.observableArrayList(arrList2));  	// IF YOU MAKE A NEW LIST THE PROGRAM LOSES SIGHT OF
																								// THE LISTENER FOR SOME REASON
			list.setItems(FXCollections.observableArrayList(arrList2));
			checkOutPane.setLeft(list);
		});
		
		
		
		list.getSelectionModel().selectedItemProperty().addListener(e ->		// THIS IS THE LISTENER FOR LIST of left side of pane
		{
			
			//pane.setLeft(list);
			ListOfItems x = list.getSelectionModel().getSelectedItem();
			if (x != null)    // x becomes null when going from list to pressing button
			{
				ObservableList<InventoryItem> xx = FXCollections.observableArrayList(x.getListOfItems());
				secList.setItems(xx);
				checkOutPane.setRight(secList);
			}
		});

		BorderPane bottom = new BorderPane();
		checkOutPane.setBottom(bottom);
		
		
		
		bottomList.setPrefSize(200, 100);
		bottom.setLeft(bottomList);
		
		secList.getSelectionModel().selectedItemProperty().addListener(e->{		// listener for list on right side of pane
			
			InventoryItem x = secList.getSelectionModel().getSelectedItem();
			if (x != null)
			{
				boolean inCart = false;
				
				if (x.getDueDate() != null)
					inCart = true;
				
				if (!inCart)
				{
					if (x instanceof item)
					{
						((item)x).CheckOut();
					}
					else if (x instanceof otherItem)
					{
						((otherItem)x).CheckOut();
					}
					
					bl.add(x);
					bottomList.setItems(FXCollections.observableArrayList(bl));
					bottom.setLeft(bottomList);
					
					notIn.add(x);  // adding the item to the check-in array
					System.out.println(x.getID() + " " + x);
				}
				else 
					System.out.println("Already in cart");
			}
			Platform.runLater(()->{
				secList.getSelectionModel().clearSelection();
			});
			
		});
		
		
		// search by id
		go.setOnAction(e->{
			String x = tf.getText();
			int num;
			try
			{
				num = Integer.parseInt(x);
				for (int i = 0; i < master.size(); i++)
				{
					InventoryItem obj = master.get(i);
					if ( num == obj.getID() && obj.getDueDate() == null)
					{
						notIn.add(master.get(i));  // adding the item to the check-in array
						if (obj instanceof item)
						{
							((item)obj).CheckOut();
						}
						else if (obj instanceof otherItem)
						{
							((otherItem)obj).CheckOut();
						}

						bl.add(master.get(i));
						bottomList.setItems(FXCollections.observableArrayList(bl));
						bottom.setLeft(bottomList);
						break;
					}
				}
			}
			catch(Exception ex)
			{
				System.out.println("must be a number");
			}	
		});
		
		
		// VIEW LIST
		
		ObservableList<InventoryItem> view = FXCollections.observableArrayList(master);
		ListView<InventoryItem> v = new ListView<>(view);
		v.setItems(view);
		v.setEditable(true);
		
		
//		v.setCellFactory(new Callback<ListView<InventoryItem>, ListCell<InventoryItem>>()
//		{
//			@Override
//			public ListCell<InventoryItem> call(ListView<InventoryItem> param)
//			{
//				return new XCell();
//			}			
//		});
		
		BorderPane viewListPane = new BorderPane();
		viewListPane.setCenter(v);
		Button save = new Button("Save");
		File f = new File("saved.txt");
		PrintWriter output = new PrintWriter(f);
		save.setOnAction(e->{
			
			try
			{
				
				for (int i = 0; i < master.size(); i++)
				{
					InventoryItem obj = master.get(i);
					String name = null;
					Date due = null;
					int id;
					if (obj instanceof item)
					{
						name = ((item)obj).getName();
						due = ((item)obj).getDueDate();
					}
					else if (obj instanceof otherItem)
					{
						name = ((otherItem)obj).getName();
						due = ((otherItem)obj).getDueDate();
					}
					id = obj.getID();
					output.println("name = " + name + ", id = " + id + ", due = " + due);
					
				}
			} catch (Exception e1)
			{
				e1.printStackTrace();
			}
			output.close();
		});
		
		viewListPane.setTop(save);
		
		
		//=======================================
		// CHECK IN
		
		BorderPane checkedInPane = new BorderPane();
		ArrayList<InventoryItem> ch = new ArrayList<>();
		
		Button confirm = new Button("confirm");
		BorderPane confirmPane = new BorderPane();
		confirmPane.setRight(confirm);
		checkedInPane.setBottom(confirmPane);
		ArrayList<InventoryItem> pending = new ArrayList<>();

		
		class CheckInCell extends ListCell<InventoryItem>
		{
			HBox hboxCheckIn = new HBox();
			Label label = new Label();
			Pane pane = new Pane();
			Button button = new Button("X");
			InventoryItem lastItem;
			Label due = new Label();
			
			public CheckInCell()
			{
				super();
				hboxCheckIn.getChildren().addAll(label, pane);
				HBox.setHgrow(pane,  Priority.ALWAYS);

			}
			
			public void updateItem(InventoryItem it, boolean empty)
			{
				super.updateItem(it, empty);
				if (it != null)
				{
						due.setText(it.getDueDate().toString());
						hboxCheckIn.getChildren().addAll(due, button);
				}
				lastItem = it;
				if (lastItem instanceof item)
				{
					pending.add(lastItem);
					label.setText( ((item)it).getName());
				}
				else if (lastItem instanceof otherItem)
				{
					pending.add(lastItem);
					label.setText( ((otherItem)it).getName() );
				}
				setGraphic(hboxCheckIn);
				
				button.setOnAction(new EventHandler<ActionEvent>()
				{
					@Override public void handle(ActionEvent e)
					{
						notIn.add(lastItem);
						notInView = FXCollections.observableArrayList(notIn);
						lv.setItems(notInView);
						
						ch.remove(lastItem);
						pending.remove(lastItem);
						
						checkInRightList.setItems(FXCollections.observableArrayList(ch));
						
						checkInRightList.setCellFactory(new Callback<ListView<InventoryItem>, ListCell<InventoryItem>>()
						{
							@Override
							public ListCell<InventoryItem> call(ListView<InventoryItem> arg0)
							{
								return new CheckInCell();
							}
					
						});
						
						checkedInPane.setCenter(checkInRightList);
//						Platform.runLater(()->{
//							ch.remove(lastItem);
//							ListView<InventoryItem> myList = new ListView<>();
//							myList.setItems(FXCollections.observableArrayList(ch));
//							checkedInPane.setRight(myList);
//						});
					}
					
				});
			}
		}
		
		
		// confirm button
		confirm.setOnAction(e->
		{
			for (int i = 0; i < master.size(); i++)
			{
				for (int j = 0; j < pending.size(); j++)
				{
					if (pending.get(j).equals(master.get(i)))
					{
						master.get(i).setDueDate(null);
					}
				}
			}
			ch.clear();
			
			checkInRightList.setItems(FXCollections.observableArrayList(ch));
			checkInRightList.setCellFactory(new Callback<ListView<InventoryItem>, ListCell<InventoryItem>>()
			{
				@Override
				public ListCell<InventoryItem> call(ListView<InventoryItem> arg0)
				{
					return new CheckInCell();
				}
		
			});
			checkedInPane.setCenter(checkInRightList);
		});
		

		
		lv.setEditable(true);
		lv.getSelectionModel().selectedItemProperty().addListener(e->
		{
			
			InventoryItem x = lv.getSelectionModel().getSelectedItem();
			// I think you can't remove from the list while the selectionModel is
			// changing the selected items (during clicking on the list), but if you 
			// use Platform.runLater the program will remove things at a later time
			// http://stackoverflow.com/questions/27769583/calling-clear-on-observablelist-causes-indexoutofboundsexception
			Platform.runLater(()->
			{
				
				notIn.remove(x);
				notInView = (FXCollections.observableArrayList(notIn));
				lv.setItems(notInView);
			});
			
			if (x != null)
			{
				ch.add(x);
				//x.CheckIn();
				ListView<InventoryItem> myList = new ListView<>();
				myList.setItems(FXCollections.observableArrayList(ch));
				//checkingInObs = FXCollections.observableArrayList(ch);
				
				//myList.setItems(checkingInObs);
				
				myList.setCellFactory(new Callback<ListView<InventoryItem>, ListCell<InventoryItem>>()
				{
					@Override
					public ListCell<InventoryItem> call(ListView<InventoryItem> arg0)
					{
						return new CheckInCell();
					}
			
				});
				checkedInPane.setCenter(myList);
			}
			
			

		});		
		
		
		// defining file combobox
		file.setOnAction(e->{
			int i = file.getSelectionModel().getSelectedIndex();
			if (i == 0)
			{
				bl.clear();
				bottomList.setItems(FXCollections.observableArrayList(bl));   // for some reason I don't have to create an observable. the ListView 
				mainPane.setCenter(checkOutPane);								// works just fine if you use setItems
			}
			else if (i == 1)
			{
				notInView = FXCollections.observableArrayList(notIn);   // fill check-in array appropriately
				lv.setItems(notInView);
				checkedInPane.setLeft(lv);
				
				// clears list on right when entering checking-in window
				ch.clear();
				checkingInObs = FXCollections.observableArrayList(ch);
				checkingIn.setItems(checkingInObs);
				
				mainPane.setCenter(checkedInPane);
				
			}
			else if (i == 2)
			{
				Platform.runLater(()->{
					v.setItems(FXCollections.observableArrayList(master));
					v.setCellFactory(new Callback<ListView<InventoryItem>, ListCell<InventoryItem>>()
					{
						@Override
						public ListCell<InventoryItem> call(ListView<InventoryItem> param)
						{
							return new ViewCell();
						}			
					});
					mainPane.setCenter(viewListPane);
				});

			}
		});
		
		
		
		
		
		
		Scene scene = new Scene(mainPane, 700, 400);
		primaryStage.setTitle("simple listView"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		
	}

}
