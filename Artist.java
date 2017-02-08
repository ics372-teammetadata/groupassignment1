import java.util.ArrayList;
import java.util.Comparator;

public class Artist extends ListViewObject
{
	private String artist;
	private ArrayList<CD> cdList = new ArrayList<>();
	
	public Artist(String artist)
	{
		this.artist = artist;
	}
	
	public String toString()
	{
		return artist;
	}
	
	public void addToList(CD cd)
	{
		cdList.add(cd);
	}
	
	public void addWholeList(Artist list)
	{
		ArrayList<CD> newList = list.getCDList();
		cdList.addAll(newList);
	}
	
	public ArrayList<CD> getCDList()
	{
		return cdList;
	}
	
	public String getArtist()
	{
		return artist;
	}
	
	public void setArtist(String x)
	{
		artist = x;
	}
	
	// http://stackoverflow.com/questions/12575833/java-compare-cannot-be-resolved-to-a-type-error
	public static Comparator<Artist> artistComparator = new Comparator<Artist>()
	{	
		public int compare(Artist a1, Artist a2) 
		{
			String artist1 = a1.getArtist().toUpperCase();
			String artist2 = a2.getArtist().toUpperCase();
			
			return artist1.compareTo(artist2);
		}
	};




}
