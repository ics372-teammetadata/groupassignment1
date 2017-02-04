import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public abstract class InventoryItem
{
	private int id = 10;
	private Date checkedOut = null;
	private Date dueDate = null;
	private String abc = "abc";
	
	public String getabc()
	{
		return abc;
	}
	
	public InventoryItem(int id)
	{
		this.id = id;
	}
	
	public int getID()
	{
		return id;
	}
	
	public void setID(int id)
	{
		this.id = id;
	}
	
	public Date getCheckedOut()
	{
		return checkedOut;
	}
	
	public void setCheckedOut(Date date)
	{
		checkedOut = date;
	}
	
	public void CheckIn()
	{
		checkedOut = null;
		dueDate = null;
	}
	
	public Date getDueDate()
	{
		return dueDate;
	}
	
	public void setDueDate(Date date)
	{
		dueDate = date;
	}
}
