package metro.ics372;

public class LibraryItem 
{
	private String item_name;
	private String item_type;
	private String item_id;
	private String author_name;
	private String artist_name;
	private String display;
	
	//constructor with three common attributes
	public LibraryItem(String item_name, String item_type, String item_id, String author_name, String artist_name)
	{
		this.item_name = item_name;
		this.item_type = item_type;
		this.item_id = item_id;
		this.author_name = author_name;
		this.artist_name = artist_name;
		display = "";
	}
	
	//item set methods
	public void setItemName(String item_name)
	{
		this.item_name = item_name;
	}
	public void setItemType(String item_type)
	{
		this.item_type = item_type;
	}
	public void setItemId(String item_id)
	{
		this.item_id = item_id;
	}
	public void setAuthorName(String author_name)
	{
		this.author_name = author_name;
	}
	public void setArtistName(String artist_name)
	{
		this.artist_name = artist_name;
	}
	
	//item get methods
	public String getItemName()
	{
		return item_name;
	}
	public String getItemType()
	{
		return item_type;
	}
	public String getItemId()
	{
		return item_id;
	}
	public String getArtistName()
	{
		return artist_name;
	}	
	public String getAuthorName()
	{
		return author_name;
	}
	
	public String toString()
	{
		
		if(getAuthorName() != "")
		{
			display = "Item Name: " + getItemName() + "\n" + "Item type: " + getItemType() + "\n" + 
					  "Item ID: " + getItemId() + "\n" + "Author Name: " + getAuthorName() + "\n";
		}
		else if(getArtistName() != "")
		{
			display = "Item Name: " + getItemName() + "\n" + "Item type: " + getItemType() + "\n" + 
					  "Item ID: " + getItemId() + "\n" + "Artist Name: " + getArtistName() + "\n";
		}
		else
		{
			display = "Item Name: " + getItemName() + "\n" + "Item type: " + getItemType() + "\n" + 
					  "Item ID: " + getItemId() + "\n";
		}
		
		return display;
	}
	
	public static void main(String[] args)
	{
		LibraryItem it = new LibraryItem("Pao", "Person", "0202145", "", "");
		System.out.println(it.toString());
	}
}
