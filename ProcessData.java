import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class ProcessData 
{
	private JSONArray data;
	private FileReader file;
	private ArrayList<InventoryItem> masterList = new ArrayList<>();
	private ArrayList<Artist> artistList = new ArrayList<>();
	private ArrayList<Author> authorList = new ArrayList<>(); 
	private ArrayList<DVD> DVDList = new ArrayList<>();
	private ArrayList<Magazine> magList = new ArrayList<>();
	
	
	public ProcessData()
	{
		importFile();
		createLists();
	}
	
	public JSONArray getJSONArray()
	{
		return data;
	}
	
	private void importFile()
	{
		JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;
		try {
			file = new FileReader("newJSON.txt");
			jsonObject = (JSONObject)parser.parse(file);
		} catch (FileNotFoundException e) {
			System.out.println("No such file\n" + e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		} catch (ParseException e) {
			System.out.println(e.toString());
		}

        data = (JSONArray) jsonObject.get("library_items");
	}
	
	public void createLists()
	{
		InventoryItem header = new InventoryItem("ID", "Name", "Type");
		masterList.add(header);
		// getting each object of JSONArray in a list
		for (int i = 0; i < data.size(); i++)
		{
			InventoryItem x = createItem((JSONObject) data.get(i));
			
			if (x instanceof CD)
			{
				// check if CD is made by already found artist
				int j;
				String artistName = ((CD)x).getArtistName();
				for (j = 0; j < artistList.size() && !( artistList.get(j).getArtist().equals(artistName) ); j++)
				{ // purposely empty
				}
				
				if (j != artistList.size())  // if artist already exists
				{
					artistList.get(j).addToList((CD)x);
				}
				else
				{
					artistList.add(new Artist(artistName));
					artistList.get(artistList.size()-1).addToList((CD)x);
				}
			}
			else if (x instanceof Book)
			{
				// check if Book is made by already found author
				int j;
				String authorName = ((Book)x).getAuthorName();
				for (j = 0; j < authorList.size() && !( authorList.get(j).getAuthor().equals(authorName) ); j++)
				{ // purposely empty
				}
				
				if (j != authorList.size())  // if author already exists
				{
					authorList.get(j).addToList((Book)x);
				}
				else
				{
					authorList.add(new Author(authorName));
					authorList.get(authorList.size()-1).addToList((Book)x);
				}
			}
			else if (x instanceof DVD)
			{
				DVDList.add((DVD)x);
			}
			else if (x instanceof Magazine)
			{
				magList.add((Magazine)x);
			}
		}
		
		Collections.sort(artistList, Artist.artistComparator);
		Collections.sort(authorList, Author.authorComparator);
		for (int i = 0; i < artistList.size(); i++)
		{
			Collections.sort(artistList.get(i).getCDList(), CD.Comparator);
			masterList.addAll(artistList.get(i).getCDList());
		}
		for (int i = 0; i < authorList.size(); i++)
		{
			Collections.sort(authorList.get(i).getBookList(), Book.Comparator);
			masterList.addAll(authorList.get(i).getBookList());
		}
		Collections.sort(DVDList, DVD.Comparator);
		masterList.addAll(DVDList);
		Collections.sort(magList, Magazine.Comparator);
		masterList.addAll(magList);
	}
	
	public ArrayList<InventoryItem> getMaster()
	{
		return masterList;
	}
	
	public ArrayList<Artist> getArtistList()
	{
		return artistList;
	}
	
	public ArrayList<Author> getAuthorList()
	{
		return authorList;
	}
	
	public ArrayList<DVD> getDVDList()
	{
		return DVDList;
	}
	
	public ArrayList<Magazine> getMagList()
	{
		return magList;
	}
	
	private InventoryItem createItem(JSONObject x)
	{
		InventoryItem item = new InventoryItem((String)x.get("item_id"), (String)x.get("item_name"), (String)x.get("item_type"));

		switch((String)x.get("item_type"))
		{
		case "Book" 	:	return new Book(item, (String)x.get("item_author"));
		case "CD" 		: 	return new CD(item, (String)x.get("item_artist"));
		case "Magazine" :	return new Magazine(item);
		case "DVD" 		:	return new DVD(item);
		default 		: 	return null;
		}
	}
}
