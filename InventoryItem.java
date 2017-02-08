import java.util.Comparator;
import java.util.Date;

public class InventoryItem extends ListViewObject
{
	private String id;
	private String name;
	private String type;
	private Date dueDate = null;
	
	public InventoryItem(String id, String name, String type)
	{
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public String toString()
	{
		return getName();
	}
	
	public void checkIn()
	{
		dueDate = null;
	}
	
	//public abstract void checkOut();
	
	public String getID()
	{
		return id;
	}
	
	public void setID(String id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public Date getDueDate()
	{
		return dueDate;
	}
	
	public void setDueDate(Date date)
	{
		dueDate = date;
	}
	
	// http://stackoverflow.com/questions/12575833/java-compare-cannot-be-resolved-to-a-type-error
	public static Comparator<InventoryItem> Comparator = new Comparator<InventoryItem>()
	{	
		public int compare(InventoryItem a1, InventoryItem a2) 
		{
			if (a1.getID().equals("ID") || a2.getID().equals("ID"))
				return Integer.MAX_VALUE;
			String InventoryItem1 = a1.getName().toUpperCase();
			String InventoryItem2 = a2.getName().toUpperCase();
						
			return InventoryItem1.compareTo(InventoryItem2);
		}
	};
	
	
}
