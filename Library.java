import java.util.ArrayList;

public class Library 
{
	private ArrayList<InventoryItem> InventoryList = new ArrayList<>();
	
	ArrayList<Artist> artists = new ArrayList<>();
	ArrayList<Author> authors = new ArrayList<>();
	ArrayList<DVD> DVDs = new ArrayList<>();
	ArrayList<Magazine> mags = new ArrayList<>();
	ArrayList<InventoryItem> master = new ArrayList<>();
	
	public Library()
	{
		ProcessData data = new ProcessData();
		artists = data.getArtistList();
		authors = data.getAuthorList();
		DVDs = data.getDVDList();
		mags = data.getMagList();
		master = data.getMaster();
	}
	
	public ArrayList<InventoryItem> getMaster()
	{
		return master;
	}
	
	public ArrayList<Artist> getArtistList()
	{
		return artists;
	}
	
	public ArrayList<Author> getAuthorList()
	{
		return authors;
	}
	
	public ArrayList<DVD> getDVDList()
	{
		return DVDs;
	}
	
	public ArrayList<Magazine> getMagList()
	{
		return mags;
	}
	
	public ArrayList<InventoryItem> getList()
	{
		return InventoryList;
	}	

	

}
