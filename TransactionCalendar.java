package metro.ics372;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TransactionCalendar
{
	private Date date;

	public TransactionCalendar()
	{
		date= new Date();
	}
	
	public String currentTime()
	{
		Format formatDate = new SimpleDateFormat("MM/dd/YYYY");
		String currentTime = formatDate.format(date);
		
		return currentTime;
	}
	
	public String dueDateForBook()
	{
		int days = 21;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date dueDate = calendar.getTime();
		Format dueDateFormat = new SimpleDateFormat("MM/dd/YYYY");
		String dueDateString = dueDateFormat.format(dueDate);
		
		return dueDateString;
	}
	
	public String dueDateForOtherItems()
	{
		int days = 7;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		Date dueDate = calendar.getTime();
		Format dueDateFormat = new SimpleDateFormat("MM/dd/YYYY");
		String dueDateString = dueDateFormat.format(dueDate);
		
		return dueDateString;
	}
	
	public static void main(String[] args)
	{
		TransactionCalendar dateAndTime = new TransactionCalendar();
		System.out.println(dateAndTime.currentTime());
		dateAndTime.dueDateForBook();
		dateAndTime.dueDateForOtherItems();
	}
}
