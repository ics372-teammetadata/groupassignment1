/**
 * Created by chris on 1/20/2017.
 */

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.time.LocalDate;


public class FileProcessor {

    //variables
    private Library library = new Library();
    private InventoryItem libItem;
    private JSONArray data;
    private File jsonFile;

    //Constructor
    //Called by the UIController class
    FileProcessor(File f){
        jsonFile = f;
    }

    //Retrieves JSON data from a file (which is received through the constructor)
    //Returns a JSON array
    //This is called by the FileProcesspor (this) processJSONData method
    //throws a ParseException that can be caught within the UIController's loadFile method
    private JSONArray importFile(File f) throws ParseException{
        try {
            JSONParser parser = new JSONParser();
            FileReader file = new FileReader(f.getPath());
            JSONObject jsonObject = (JSONObject) parser.parse(file);
            this.data = (JSONArray) jsonObject.get("library_items");
            return data;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO exception");
        }
        return data;
    }

    //Processes JSON file data and generates library items from JSON object info and returns a Library list
    //Calls importFile which throws a ParseException that can be caught within the UIController's loadFile method
    public Library processJSONData() throws ParseException {
        //collection info from JSON file
        if (jsonFile.exists()) {
            data = this.importFile(jsonFile);
        } else {
            data = null;
        }

        //loop through JSON array, creating 'Library Item" objects, adding them to an inventory list to be returned
        for (Object jArrayItem : data) {
            JSONObject b = (JSONObject) jArrayItem;
            //generate library items from JSON object info and return an InventoryItem
            if  (b.containsKey("item_isCheckedOut")){
                if (b.get("item_type").equals("CD")) {
                    System.out.println("CD Try");
                    libItem = new CD((String) b.get("item_id"), (String) b.get("item_name"), (String) b.get("item_type"), (String) b.get("item_artist"), (boolean) b.get("item_isCheckedOut"), (String)b.get("item_dueDate"),(String)b.get("item_checkoutDate"));
                } else if (b.get("item_type").equals("Book")) {
                    System.out.println("Book Try");
                    libItem = new Book((String) b.get("item_id"), (String) b.get("item_name"), (String) b.get("item_type"), (String) b.get("item_author"),(boolean) b.get("item_isCheckedOut"),(String)b.get("item_dueDate"), (String)b.get("item_checkoutDate"));
                } else if (b.get("item_type").equals("DVD")) {
                    System.out.println("DVD Try");
                    libItem = new DVD((String) b.get("item_id"), (String) b.get("item_name"), (String) b.get("item_type"),(boolean) b.get("item_isCheckedOut"),(String)b.get("item_dueDate"), (String)b.get("item_checkoutDate"));
                } else if (b.get("item_type").equals("Magazine")) {
                    System.out.println("Magazine Try");
                    libItem = new Magazine((String) b.get("item_id"), (String) b.get("item_name"), (String) b.get("item_type"), (boolean) b.get("item_isCheckedOut"),(String)b.get("item_dueDate"), (String)b.get("item_checkoutDate"));
                }
            }
            else{
                if (b.get("item_type").equals("CD")) {
                    System.out.println("Fialed CD");
                    libItem = new CD((String) b.get("item_id"), (String) b.get("item_name"), (String) b.get("item_type"), (String) b.get("item_artist"));
                } else if (b.get("item_type").equals("Book")) {
                    System.out.println("Fialed book");
                    libItem = new Book((String) b.get("item_id"), (String) b.get("item_name"), (String) b.get("item_type"), (String) b.get("item_author"));
                } else if (b.get("item_type").equals("DVD")) {
                    System.out.println("Fialed dvd");
                    libItem = new DVD((String) b.get("item_id"), (String) b.get("item_name"), (String) b.get("item_type"));
                } else if (b.get("item_type").equals("Magazine")) {
                    System.out.println("Fialed mag");
                    libItem = new Magazine((String) b.get("item_id"), (String) b.get("item_name"), (String) b.get("item_type"));
                }
            }

            //Add inventory item to the Library list
            library.add(libItem);
        }
        return library;
    }

    //Write data to JSON file
    //Loops through Library list, adds each item to a JSON array
    //Uses FileWriter to write the data to the previously loaded JSON file
    public void writeData(Library l) {
        JSONObject parentOutputJObject = new JSONObject();
        JSONArray outputJArray = new JSONArray();

        for (InventoryItem i : l) {
            if (i.getType().equals("CD")) {
                CD c = (CD) i;
                JSONObject outputChildObject = new JSONObject();
                outputChildObject.put("item_name", c.getName());
                outputChildObject.put("item_type", c.getType());
                outputChildObject.put("item_id", c.getID());
                outputChildObject.put("item_artist", c.getArtist());
                outputChildObject.put("item_isCheckedOut", c.isCheckedOut());
                outputChildObject.put("item_dueDate", c.getDueDate());
                outputChildObject.put("item_checkoutDate", c.getCheckoutDate());

                outputJArray.add(outputChildObject);
            } else if (i.getType().equals("Book")) {
                Book b = (Book) i;
                JSONObject outputChildObject = new JSONObject();
                outputChildObject.put("item_name", b.getName());
                outputChildObject.put("item_type", b.getType());
                outputChildObject.put("item_id", b.getID());
                outputChildObject.put("item_author", b.getAuthor());
                outputChildObject.put("item_isCheckedOut", b.isCheckedOut());
                outputChildObject.put("item_dueDate", b.getDueDate());
                outputChildObject.put("item_checkoutDate", b.getCheckoutDate());

                outputJArray.add(outputChildObject);
            } else {
                JSONObject outputChildObject = new JSONObject();
                outputChildObject.put("item_name", i.getName());
                outputChildObject.put("item_type", i.getType());
                outputChildObject.put("item_id", i.getID());
                outputChildObject.put("item_isCheckedOut", i.isCheckedOut());
                outputChildObject.put("item_dueDate", i.getDueDate());
                outputChildObject.put("item_checkoutDate", i.getCheckoutDate());

                outputJArray.add(outputChildObject);
            }
        }

        parentOutputJObject.put("library_items", outputJArray);

        try (FileWriter file = new FileWriter(jsonFile.getPath())) {
            file.write(parentOutputJObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
