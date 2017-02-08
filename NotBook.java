import java.util.Calendar;

public class NotBook extends InventoryItem
{

	public NotBook(String id, String name, String type) 
	{
		super(id, name, type);
	}

	public void checkOut() 
	{
		Calendar today = Calendar.getInstance();
		today.add(Calendar.WEEK_OF_MONTH, +1);	
		setDueDate(today.getTime());
	}

}
