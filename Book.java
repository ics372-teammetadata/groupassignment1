import java.util.Calendar;

class Book extends InventoryItem
{
	private String author;

	public Book(InventoryItem x, String author)
	{
		super(x.getID(), x.getName(), x.getType());
		this.author = author;
	}
	
	public String getAuthorName()
	{
		return author;
	}

	public void checkOut() {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.WEEK_OF_MONTH, +3);	
		setDueDate(today.getTime());
	}
}
