import java.util.ArrayList;
import java.util.Collections;

public class ListOfItems
{
	private String listName;
	ArrayList<InventoryItem> itemList = new ArrayList<>();
	
	public String toString()
	{
		return listName;
	}
	
	public ListOfItems(String listName)
	{
		this.listName = listName;
	}
	
	public void addToList(InventoryItem i)
	{
		itemList.add(i);
	}
	
	public void addWholeList(ListOfItems list)
	{
		ArrayList<InventoryItem> newList = list.getListOfItems();
		itemList.addAll(newList);
		//Collections.sort(itemList);
	}
	
	public ArrayList<InventoryItem> getListOfItems()
	{
		return itemList;
	}
}
