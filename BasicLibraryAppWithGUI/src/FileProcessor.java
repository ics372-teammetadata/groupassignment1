/**
 * Created by chris on 1/20/2017.
 **/


import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.*;


public class FileProcessor {

    //variables
    private Library library = new Library();
    private InventoryItem libItem;
    private JSONArray importedJSONData;
    private File jsonFile;

    //Constructor
    //Called by the UIController class
    FileProcessor(File f){
        jsonFile = f;
    }

    //Retrieves JSON importedJSONData from a file (which is received through the constructor)
    //Returns a JSON array
    //This is called by the FileProcesspor (this) processJSONData method
    //throws a ParseException that can be caught within the UIController's loadFile method
    private JSONArray importFile(File f) throws ParseException{
        try {
            JSONParser parser = new JSONParser();
            FileReader file = new FileReader(f.getPath());
            JSONObject jsonObject = (JSONObject) parser.parse(file);
            this.importedJSONData = (JSONArray) jsonObject.get("library_items");
            return importedJSONData;
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("IO exception");
        }
        return importedJSONData;
    }

    /**
    *       Processes JSON file importedJSONData and generates library items from JSON object info and returns a Library list
    *       Calls importFile which throws a ParseException that can be caught within the UIController's loadFile method
     **/
    Library processJSONData() throws ParseException {
        //collection info from JSON file
        if (jsonFile.exists()) {
            importedJSONData = this.importFile(jsonFile);
        } else {
            importedJSONData = null;
        }

        if(importedJSONData != null) {
            //loop through JSON array, creating 'Library Item" objects, adding them to an inventory list to be returned
            for (Object jArrayItem : importedJSONData) {

                JSONObject arrayItem = (JSONObject) jArrayItem;

                //variables to be used when instantiating InventoryItem objects
                String itemDueDate = "";
                String itemCheckOutDate = "";
                boolean isCheckedOut = false;
                String itemName = (String) arrayItem.get("item_name");
                String itemID = (String) arrayItem.get("item_id");
                String itemType = (String) arrayItem.get("item_type");


                //generate library items from JSON object info and return an InventoryItem

                if (arrayItem.containsKey("item_isCheckedOut")) {
                    // IF arrayItem.containsKey("item_isCheckedOut"), then we are using a file than has been processed at least once by the application
                    // therefore difference Object constructors will be needed when instantiating InventoryItem object from the JSON data

                    //Set variables unique to files that have been processed at least once by the application
                    itemDueDate = (String) arrayItem.get("item_dueDate");
                    itemCheckOutDate = (String) arrayItem.get("item_checkoutDate");
                    isCheckedOut = (boolean) arrayItem.get("item_isCheckedOut");

                    if (arrayItem.get("item_type").equals("CD")) {
                        libItem = new CD(itemID, itemName, itemType, (String) arrayItem.get("item_artist"),isCheckedOut, itemDueDate, itemCheckOutDate);
                    } else if (arrayItem.get("item_type").equals("Book")) {
                        libItem = new Book(itemID, itemName, itemType, (String) arrayItem.get("item_author"), isCheckedOut, itemDueDate, itemCheckOutDate);
                    } else if (arrayItem.get("item_type").equals("DVD")) {
                        libItem = new DVD(itemID, itemName, itemType, isCheckedOut, itemDueDate, itemCheckOutDate);
                    } else if (arrayItem.get("item_type").equals("Magazine")) {
                        libItem = new Magazine(itemID, itemName, itemType, isCheckedOut, itemDueDate, itemCheckOutDate);
                    }
                } else {
                    // IF arrayItem.containsKey("item_isCheckedOut") IS FALSE, then we are using a file than has been NOT BEEN processed at least once by the application
                    // therefore difference Object constructors will be needed when instantiating InventoryItem object from the JSON data

                    if (arrayItem.get("item_type").equals("CD")) {
                        libItem = new CD(itemID, itemName, itemType, (String) arrayItem.get("item_artist"));
                    } else if (arrayItem.get("item_type").equals("Book")) {
                        libItem = new Book(itemID, itemName, itemType, (String) arrayItem.get("item_author"));
                    } else if (arrayItem.get("item_type").equals("DVD")) {
                        libItem = new DVD(itemID, itemName, itemType);
                    } else if (arrayItem.get("item_type").equals("Magazine")) {
                        libItem = new Magazine(itemID, itemName, itemType);
                    }
                }
                //Add inventory item to the Library list
                library.add(libItem);
            }
        }
        return library;
    }

    //Write importedJSONData to JSON file
    //Loops through Library list, adds each item to a JSON array
    //Uses FileWriter to write the importedJSONData to the previously loaded JSON file
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
