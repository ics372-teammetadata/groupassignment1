package metro.ics372;

import java.util.Date;

public class Transaction
{
	private String status;
	private LibraryItem[] itemList;
	private String[] statusList;
	private String action;
	private int itemNum;
	private TransactionCalendar date;

	public Transaction(LibraryItem[] inventory)
	{
		date = new TransactionCalendar();
		itemList = inventory;
		statusList = new String[100];
		status = "Available";
		action = "Checked In";
		itemNum = 0;

		try
		{
			for(int i = 0; i < itemList.length; i++)
			{
				String holdString = itemList[i].toString() + "Status: " + getStatus() + "\n" +  "Action: " + getAction() + "\n" + "Checked In Date: " + date.currentTime() + "\n";
				statusList[itemNum] = holdString;
				itemNum++;
			}
		}
		catch(NullPointerException ex)
		{

		}
	}

	public String getStatus()
	{
		return status;
	}

	public String getAction()
	{
		return action;
	}

	public boolean isCheckedOut()
	{
		return getAction().equalsIgnoreCase("Checked Out");
	}

	public void checkedIn(String item_id)
	{
		if(isCheckedOut())
		{
		for(int i = 0; i < itemNum; i++)
		{
			status = "Available";
			action = "Checked In";

			if(itemList[i].getItemId().equalsIgnoreCase(item_id))
			{
				String holdString = itemList[i].toString() + "Status: " + getStatus() + "\n" +  "Action: " + getAction() + "\n" + "Check In Date: " + date.currentTime() + "\n";
				statusList[i] = holdString;
			}
		}
		}
		else
		{
			System.out.println("Already Checked In");
		}
	}

	public void checkedOut(String item_id)
	{
		for(int i = 0; i < itemNum; i++)
		{
			status = "Not Available";
			action = "Checked Out";

			if(itemList[i].getItemId().equalsIgnoreCase(item_id) && itemList[i].getItemType().equalsIgnoreCase("Book"))
			{
				String holdString = itemList[i].toString() + "Status: " + getStatus() + "\n" +  "Action: " + getAction() + "\n" + "Check Out Date: " + date.currentTime() + "\n" +
								    "Return By: " + date.dueDateForBook() + "\n";
				statusList[i] = holdString;
			}
			else if(itemList[i].getItemId().equalsIgnoreCase(item_id) && !itemList[i].getItemType().equalsIgnoreCase("Book"))
			{
				String holdString = itemList[i].toString() + "Status: " + getStatus() + "\n" +  "Action: " + getAction() + "\n" + "Check Out Date: " + date.currentTime() + "\n" +
					    "Return By: " + date.dueDateForOtherItems() + "\n";
	statusList[i] = holdString;
			}
		}
	}

	public void viewItemStatus()
	{
		try
		{
			for(int i = 0; i < statusList.length; i++)
			{
				System.out.println(statusList[i].toString());
			}
		}
		catch(NullPointerException ex)
		{

		}
	}

	public static void main(String[] args)
	{
		JsonFileReader newReader = new JsonFileReader("C:\\Users\\sadmin\\IdeaProjects\\ICS372-Project\\lib\\LibraryItem.json");
		LibraryItem[] inventory = newReader.showItemList();
		Transaction ts = new Transaction(inventory);
		ts.checkedIn("1adf4");
		ts.checkedOut("1a545");
		ts.viewItemStatus();
	}
}
