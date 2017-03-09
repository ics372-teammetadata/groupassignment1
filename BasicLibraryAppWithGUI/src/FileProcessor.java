/**
 * Created by chris on 1/20/2017.
 **/


import java.io.*;
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
    private JSONObject jsonObject = null;
    private static final String ITEM_ARTIST = "item_artist";
    private static final String ITEM_AUTHOR = "item_author";
    private static final String LIBRARY_ITEMS = "library_items";
    private static final String ITEM_NAME = "item_name";
    private static final String ITEM_ID = "item_id";
    private static final String ITEM_TYPE = "item_type";
    private static final String ITEM_ISCHECKEDOUT = "item_isCheckedOut";
    private static final String ITEM_DUEDATE = "item_dueDate";
    private static final String ITEM_CHECKOUTDATE = "item_checkoutDate";
    private static final String CD = "CD";
    private static final String DVD = "DVD";
    private static final String BOOK = "Book";
    private static final String MAGAZINE = "Magazine";
    /**
     *      Constructor
     *      Called by the UIController class
     */

    public FileProcessor(File f){
        jsonFile = f;
    }
    
    /**
     *      Method name : processJSONData
     *      Retrieves JSON importedJSONData from cardRepo file (which is received through the constructor)
     *      This is called by the FileProcesspor (this) processJSONData method
     *      Processes JSON file importedJSONData and generates library items from JSON object info and returns cardRepo Library list
     *      throws cardRepo ParseException and DateTimeParseException that can be caught within the UIController's loadFile method,
     *       which displays cardRepo meaningful error message to the user
     **/

    public Library processJSONData() throws ParseException, DateTimeParseException {
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

            if (jsonObject.get(LIBRARY_ITEMS) != null) {
                //loop through JSON array, creating 'Library Item" objects, adding them to an inventory list to be returned
                for (Object jArrayItem : (JSONArray) jsonObject.get(LIBRARY_ITEMS)) {

                    JSONObject arrayItem = (JSONObject) jArrayItem;

                    //variables to be used when instantiating InventoryItem objects
                    String itemName = (String) arrayItem.get(ITEM_NAME);
                    String itemID = (String) arrayItem.get(ITEM_ID);
                    String itemType = (String) arrayItem.get(ITEM_TYPE);


                    //generate library items from JSON object info and return an InventoryItem
                    if (arrayItem.containsKey(ITEM_ISCHECKEDOUT)) {
                        // IF arrayItem.containsKey(ITEM_ISCHECKEDOUT), then we are using cardRepo file than has been processed at least once by the application
                        // therefore difference Object constructors will be needed when instantiating InventoryItem object from the JSON data

                        //Set variables unique to files that have been processed at least once by the application
                        String itemDueDate = (String) arrayItem.get(ITEM_DUEDATE);
                        String itemCheckOutDate = (String) arrayItem.get(ITEM_CHECKOUTDATE);
                        boolean isCheckedOut = (boolean) arrayItem.get(ITEM_ISCHECKEDOUT);

                        if (arrayItem.get(ITEM_TYPE).equals(CD)) {
                            libItem = new CD(itemID, itemName, itemType, (String) arrayItem.get(ITEM_ARTIST), isCheckedOut, itemDueDate, itemCheckOutDate);
                        } else if (arrayItem.get(ITEM_TYPE).equals(BOOK)) {
                            libItem = new Book(itemID, itemName, itemType, (String) arrayItem.get(ITEM_AUTHOR), isCheckedOut, itemDueDate, itemCheckOutDate);
                        } else if (arrayItem.get(ITEM_TYPE).equals(DVD)) {
                            libItem = new DVD(itemID, itemName, itemType, isCheckedOut, itemDueDate, itemCheckOutDate);
                        } else if (arrayItem.get(ITEM_TYPE).equals(MAGAZINE)) {
                            libItem = new Magazine(itemID, itemName, itemType, isCheckedOut, itemDueDate, itemCheckOutDate);
                        }
                    } else {
                        // IF arrayItem.containsKey(ITEM_ISCHECKEDOUT) IS FALSE, then we are using cardRepo file than has been NOT BEEN processed at least once by the application
                        // therefore difference Object constructors will be needed when instantiating InventoryItem object from the JSON data

                        if (arrayItem.get(ITEM_TYPE).equals(CD)) {
                            libItem = new CD(itemID, itemName, itemType, (String) arrayItem.get(ITEM_ARTIST));
                        } else if (arrayItem.get(ITEM_TYPE).equals(BOOK)) {
                            libItem = new Book(itemID, itemName, itemType, (String) arrayItem.get(ITEM_AUTHOR));
                        } else if (arrayItem.get(ITEM_TYPE).equals(DVD)) {
                            libItem = new DVD(itemID, itemName, itemType);
                        } else if (arrayItem.get(ITEM_TYPE).equals(MAGAZINE)) {
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
     *      Loops through Library list, adds each item to cardRepo JSON array
     *      Uses FileWriter to write the InventoryItem data to the previously loaded JSON file
     */

    public void writeJSONData(Library lib) {
        JSONObject parentOutputJObject = new JSONObject();
        JSONArray outputJArray = new JSONArray();

        for (InventoryItem i : lib) {
            JSONObject outputChildObject = new JSONObject();
            if (i.getType().equals(CD)) {
                CD cd = (CD) i;
                outputChildObject.put(ITEM_ARTIST, cd.getArtist());
            } else if (i.getType().equals(BOOK)) {
                Book b = (Book) i;
                outputChildObject.put(ITEM_AUTHOR, b.getAuthor());
            }
            outputChildObject.put(ITEM_NAME, i.getName());
            outputChildObject.put(ITEM_TYPE, i.getType());
            outputChildObject.put(ITEM_ID, i.getID());
            outputChildObject.put(ITEM_ISCHECKEDOUT, i.isCheckedOut());
            outputChildObject.put(ITEM_DUEDATE, i.getDueDate());
            outputChildObject.put(ITEM_CHECKOUTDATE, i.getCheckoutDate());
            outputJArray.add(outputChildObject);
        }

        parentOutputJObject.put(LIBRARY_ITEMS, outputJArray);

        try (FileWriter file = new FileWriter(jsonFile.getPath())) {
            file.write(parentOutputJObject.toJSONString());
            file.flush();
        } catch (IOException e) {
            System.out.println("An IOexception was thrown in writeJSONData method ");;
        }
    }

    /**
     *   processXMLData method
     *
     *   Reads XML data from a file
     *
     */

    public void processXMLData(){

    }
}
