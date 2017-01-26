/**
 * Created by chris on 1/20/2017.
 */

import java.io.*;
import java.util.ArrayList;
import org.json.simple.*;
import org.json.simple.parser.*;


public class FileProcessor {
    private Library library  = new Library();
    private InventoryItem libItem;
    private JSONArray data;
    private File jsonFile = new File("c:/temp/test.txt");;
    /*
    public void writeData(Library l){
        JSONObject obj = new JSONObject();
        JSONObject obj2 = new JSONObject();
        obj2.put("item_name " , l.getItemByID("48934j").getName());
        JSONArray newJArray = new JSONArray();
        obj.put("library_items", obj2);
        obj.put("library_items", newJArray);

        // try-with-resources statement based on post comment below :)
        try (FileWriter file = new FileWriter("c:/temp/test2.txt")) {
            file.write(obj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + obj);
        }catch(IOException e){}
    }
    */
    public Library processData(){

        //collection info from JSON file
        if(jsonFile.exists()) {
            data = this.importFile(jsonFile);
            //System.out.println(jsonFile.getPath());
        }else{
            System.exit(0);
        }

        //loop through JSON array, creating 'Library Item" objects, adding them to an inventory list to be returned
        for(Object jArrayItem : data){
            JSONObject b = (JSONObject)jArrayItem;
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
    private InventoryItem createInvItem(JSONObject jsonObject){
        if(jsonObject.get("item_type").equals("CD")){
            libItem = new CD((String)jsonObject.get("item_id"),(String)jsonObject.get("item_name"),(String)jsonObject.get("item_type"), (String)jsonObject.get("item_artist"));
        }
        else if(jsonObject.get("item_type").equals("DVD")){
            libItem = new DVD((String)jsonObject.get("item_id"),(String)jsonObject.get("item_name"),(String)jsonObject.get("item_type"));
        }
        else if(jsonObject.get("item_type").equals("Magazine")){
            libItem = new Magazine((String)jsonObject.get("item_id"),(String)jsonObject.get("item_name"),(String)jsonObject.get("item_type"));
        }
        else if(jsonObject.get("item_type").equals("Book")){
            libItem = new Book((String)jsonObject.get("item_id"),(String)jsonObject.get("item_name"),(String)jsonObject.get("item_type"), (String)jsonObject.get("item_author"));
        }
        return libItem;
    }
}
