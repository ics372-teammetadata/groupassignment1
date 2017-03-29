import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;

// viewCell is used to populate the listView viewList with
// a label, a pane for spacing and a due date
class ViewCell extends ListCell<InventoryItem>
{
	HBox hbox = new HBox();
	Label name = new Label();
	Label type = new Label();
	Label due = new Label();
	Label id = new Label();
	Pane pane = new Pane();
	
	Label artOrAuth = new Label();
	
	
	public ViewCell()
	{
		super();
		hbox.getChildren().addAll(id, type, name, artOrAuth, pane);
		HBox.setHgrow(pane,  Priority.ALWAYS);
	}
	
	public void updateItem(InventoryItem it, boolean empty)
	{
		super.updateItem(it, empty);

		if (it != null)
		{
			if (it.getID().equals("ID"))
			{
				id.setTextFill(Color.web("rgb(255,0,0)"));
				type.setTextFill(Color.web("rgb(255,0,0)"));
				name.setTextFill(Color.web("rgb(255,0,0)"));
				artOrAuth.setText("Artist/Author");
				artOrAuth.setMinWidth(100);
				artOrAuth.setTextFill(Color.web("rgb(255,0,0)"));
				Label dueDate = new Label("Due Date");
				dueDate.setTextFill(Color.web("rgb(255,0,0)"));
				hbox.getChildren().add(dueDate);
			}
			else if (it.getDueDate() != null)
			{
				hbox.getChildren().remove(due);
				due.setText(it.getDueDate().toString());
				hbox.getChildren().addAll(due);
			}
		}

		if (it != null)
		{
			id.setMinWidth(100);
			id.setText(it.getID());
			type.setMinWidth(100);
			type.setText(it.getType());
			name.setMinWidth(170);
//				idText = "ID: " + it.getID();
//				idText = setWidth(idText);
			
			if (it instanceof CD)
			{
				name.setText(((CD)it).getName() ) ;
				artOrAuth.setText(((CD)it).getArtistName());
			}
			else if (it instanceof DVD)
				name.setText(((DVD)it).getName() );
			else if (it instanceof Magazine)
				name.setText(((Magazine)it).getName() );
			else if (it instanceof Book)
			{
				name.setText(((Book)it).getName() );
				artOrAuth.setText(((Book)it).getAuthorName());
			}
			else if (it instanceof InventoryItem)
			{
				name.setText(it.getName());
			}
			setGraphic(hbox);
		}
			
			

	}
}