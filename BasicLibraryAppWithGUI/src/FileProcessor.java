/**
 * Created by chris on 1/20/2017.
 **/


import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.time.format.DateTimeParseException;

import org.json.simple.*;
import org.json.simple.parser.*;


public class FileProcessor {

    /**
     *      Variables
     */

    private Library library = new Library();
    private InventoryItem libItem;
    private File jsonFile;
    JSONObject jsonObject = null;

    /**
     *      Constructor
     *      Called by the UIController class
     */

    FileProcessor(File f){
        jsonFile = f;
    }
    
    /**
     *      Method name : processJSONData
     *      Retrieves JSON importedJSONData from a file (which is received through the constructor)
     *      This is called by the FileProcesspor (this) processJSONData method
     *      Processes JSON file importedJSONData and generates library items from JSON object info and returns a Library list
     *      throws a ParseException that can be caught within the UIController's loadFile method
     **/

    Library processJSONData() throws ParseException, DateTimeParseException {
        //collection info from JSON file
        if (jsonFile.exists()) {
            try {
                JSONParser parser = new JSONParser();
                FileReader file = new FileReader(jsonFile.getPath());
                jsonObject = (JSONObject) parser.parse(file);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            } catch (IOException e) {
                System.out.println("IO exception");
            }

            if (jsonObject.get("library_items") != null) {
                //loop through JSON array, creating 'Library Item" objects, adding them to an inventory list to be returned
                for (Object jArrayItem : (JSONArray) jsonObject.get("library_items")) {

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
                            libItem = new CD(itemID, itemName, itemType, (String) arrayItem.get("item_artist"), isCheckedOut, itemDueDate, itemCheckOutDate);
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
        }

        return library;
    }

    /**
     *      Method name : writeJSONData
     *      @param lib
     * 
     *      Saves Inventory Items to the previously loaded JSON file
     *      Loops through Library list, adds each item to a JSON array
     *      Uses FileWriter to write the InventoryItem data to the previously loaded JSON file
     */

    public void writeJSONData(Library lib) {
        JSONObject parentOutputJObject = new JSONObject();
        JSONArray outputJArray = new JSONArray();

        for (InventoryItem i : lib) {
            JSONObject outputChildObject = new JSONObject();
            if (i.getType().equals("CD")) {
                CD cd = (CD) i;
                outputChildObject.put("item_artist", cd.getArtist());
            } else if (i.getType().equals("Book")) {
                Book b = (Book) i;
                outputChildObject.put("item_author", b.getAuthor());
            }
            outputChildObject.put("item_name", i.getName());
            outputChildObject.put("item_type", i.getType());
            outputChildObject.put("item_id", i.getID());
            outputChildObject.put("item_isCheckedOut", i.isCheckedOut());
            outputChildObject.put("item_dueDate", i.getDueDate());
            outputChildObject.put("item_checkoutDate", i.getCheckoutDate());
            outputJArray.add(outputChildObject);
        }

        parentOutputJObject.put("library_items", outputJArray);

        try (FileWriter file = new FileWriter(jsonFile.getPath())) {
            file.write(parentOutputJObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("An IOexception was thrown in writeJSONData method");;
        }
    }

}
