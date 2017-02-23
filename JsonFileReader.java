package metro.ics372;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class JsonFileReader
{
   private LibraryItem[] itemCollection;
   private int counter;
   private FileReader read;
   private JSONObject jsonObject;
   private JSONArray itemList;

   public JsonFileReader(String path)
   {
       itemCollection = new LibraryItem[100];
       counter = 0;
              	   
       try 
       {
    	   read = new FileReader(path);
    	   jsonObject = (JSONObject) new JSONParser().parse(read);
    	   itemList = (JSONArray) jsonObject.get("library_items");   
       } 
       catch (FileNotFoundException e) 
       {
    	   System.out.println("File Not Found!!! Try Different File Path");
       } 
       catch (IOException e) 
       {
			System.out.println("File Object is Unavailable");
       }
       catch (ParseException e) 
       {
			System.out.println("File cannot be Parsed");	
       }   	
	}
       

   public LibraryItem[] showItemList()
   { 
		  		    
	   for(int i = 0; i < itemList.size(); i++)
	   {
		   JSONObject itemObject = (JSONObject) itemList.get(i);
		   String holdString = itemObject.keySet().toString().replace("[", "").replace("]", "").replace(",", "");
		   String[] stringArray = holdString.split("\\s"); 
		   
		   for(int j = 0; j < stringArray.length; j++)
		   {
			   if(stringArray.length == 4 && stringArray[j].equalsIgnoreCase("item_author"))
			   {
				   itemCollection[counter] = new LibraryItem(itemObject.get("item_name").toString(),
						   									 itemObject.get("item_type").toString(),
						   									 itemObject.get("item_id").toString(),
						   									 itemObject.get("item_author").toString(), "");
				   counter++;
				   break;
			   }
			   else if(stringArray.length == 4 && stringArray[j].equalsIgnoreCase("item_artist"))
			   {
				   itemCollection[counter] = new LibraryItem(itemObject.get("item_name").toString(),
								 itemObject.get("item_type").toString(),
								 itemObject.get("item_id").toString(), "",
								 itemObject.get("item_artist").toString());
				   counter++;
				   break;
			   }			   
			   else if(stringArray.length == 3)
			   {
				   itemCollection[counter] = new LibraryItem(itemObject.get("item_name").toString(),
								 itemObject.get("item_type").toString(),
								 itemObject.get("item_id").toString(), "", "");
				   counter++;
				   break;
			   }
		   }
	   }
	   
	   return itemCollection;
   }
   
   public static void main(String[] args)
   {
	   JsonFileReader newReader = new JsonFileReader("A:\\workspace\\ICS372\\src\\com\\metro\\ics372\\LibraryItem.json");
	   LibraryItem[] inventory = newReader.showItemList();

	   for(int i = 0; i < inventory.length; i++)
	   {
		   System.out.println(inventory[i].toString());
	   }

   }
}
