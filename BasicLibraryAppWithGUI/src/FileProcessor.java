/**
 * Created by chris on 1/20/2017.
 */

import java.io.*;
import java.util.ArrayList;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.time.LocalDate;


public class FileProcessor {
    private Library library = new Library();
    private InventoryItem libItem;
    private JSONArray data;
    private File jsonFile; // = new File("c:/temp/test.txt");

    FileProcessor(File f){
        jsonFile = f;
    }

    FileProcessor(){
        jsonFile = new File("c:/temp/test.txt");
    }
    public void writeData(Library l) {
        JSONObject parentOutputJObject = new JSONObject();
        JSONArray outputJArray = new JSONArray();

        for (InventoryItem i : l) {
            if (i.getType().equals("CD")) {
                CD c = (CD) i;
                //System.out.println(i.getClass());
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
                //System.out.println(i.getClass());
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
                //System.out.println(i.getClass());
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
        //System.out.print(parentOutputJObject);
    }

    public Library processData() {

        //collection info from JSON file
        if (jsonFile.exists()) {
            data = this.importFile(jsonFile);
            //System.out.println(jsonFile.getPath());
        } else {
            System.out.println("File does not exist");
            System.exit(0);
        }

        //loop through JSON array, creating 'Library Item" objects, adding them to an inventory list to be returned
        for (Object jArrayItem : data) {
            JSONObject b = (JSONObject) jArrayItem;
            libItem = this.createInvItem(b);
            library.add(libItem);
        }
        return library;
    }

    //retrieve JSON data from a file and return a JSON array
    private JSONArray importFile(File f) {
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
        } catch (ParseException e) {
            System.out.println("Parse exception");
        }
        return data;
    }

    //generate library items from JSON object info and return an InventoryItem
    private InventoryItem createInvItem(JSONObject jsonObject) {
        try{
            if (jsonObject.get("item_type").equals("CD")) {
                libItem = new CD((String) jsonObject.get("item_id"), (String) jsonObject.get("item_name"), (String) jsonObject.get("item_type"), (String) jsonObject.get("item_artist"), (boolean) jsonObject.get("item_isCheckedOut"), (String)jsonObject.get("item_dueDate"),(String)jsonObject.get("item_checkoutDate"));
            } else if (jsonObject.get("item_type").equals("Book")) {
                libItem = new Book((String) jsonObject.get("item_id"), (String) jsonObject.get("item_name"), (String) jsonObject.get("item_type"), (String) jsonObject.get("item_author"),(boolean) jsonObject.get("item_isCheckedOut"),(String)jsonObject.get("item_dueDate"), (String)jsonObject.get("item_checkoutDate"));
            } else if (jsonObject.get("item_type").equals("DVD")) {
                libItem = new DVD((String) jsonObject.get("item_id"), (String) jsonObject.get("item_name"), (String) jsonObject.get("item_type"),(boolean) jsonObject.get("item_isCheckedOut"),(String)jsonObject.get("item_dueDate"), (String)jsonObject.get("item_checkoutDate"));
            } else if (jsonObject.get("item_type").equals("Magazine")) {
                libItem = new Magazine((String) jsonObject.get("item_id"), (String) jsonObject.get("item_name"), (String) jsonObject.get("item_type"), (boolean) jsonObject.get("item_isCheckedOut"),(String)jsonObject.get("item_dueDate"), (String)jsonObject.get("item_checkoutDate"));
            }
        }
        catch (NullPointerException e){

            if (jsonObject.get("item_type").equals("CD")) {
                libItem = new CD((String) jsonObject.get("item_id"), (String) jsonObject.get("item_name"), (String) jsonObject.get("item_type"), (String) jsonObject.get("item_artist"));
            } else if (jsonObject.get("item_type").equals("Book")) {
                libItem = new Book((String) jsonObject.get("item_id"), (String) jsonObject.get("item_name"), (String) jsonObject.get("item_type"), (String) jsonObject.get("item_author"));
            } else if (jsonObject.get("item_type").equals("DVD")) {
                libItem = new DVD((String) jsonObject.get("item_id"), (String) jsonObject.get("item_name"), (String) jsonObject.get("item_type"));
            } else if (jsonObject.get("item_type").equals("Magazine")) {
                libItem = new Magazine((String) jsonObject.get("item_id"), (String) jsonObject.get("item_name"), (String) jsonObject.get("item_type"));
            }
        }
        return libItem;
    }
}
