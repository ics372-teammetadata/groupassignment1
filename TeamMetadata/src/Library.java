import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.json.simple.*;
import org.json.simple.parser.*;

/**
 * Created by Andrew on 1/23/2017.
 */

public class Library {
    private HashMap<String, InventoryItem> ItemLibrary;
    private enum itemType {CD, Book, Magazine, DVD};


    private JSONArray getData(){
        JSONArray data = null;

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose Library File");
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        JSONParser parser = new JSONParser();
        JSONObject obj;

        while (data == null) {
            try {
                obj = (JSONObject) parser.parse(new FileReader(chooser.showOpenDialog(new Stage())));
                data = (JSONArray) obj.get("library_items");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    public void init(){
        ItemLibrary = new HashMap<String, InventoryItem>();
        JSONArray data = getData();

        for (Object o: data.toArray()){
            JSONObject item = (JSONObject)o;
            InventoryItem m = null;
            switch (itemType.valueOf((String)item.get("item_type"))){
                case CD:
                    m = new ItemCD((String)item.get("item_id"), (String)item.get("item_name"), (String)item.get("item_artist"));
                    break;
                case Book:
                    m = new ItemBook((String)item.get("item_id"), (String)item.get("item_name"), (String)item.get("item_author"));
                    break;
                case Magazine:
                    m = new ItemMagazine((String)item.get("item_id"), (String)item.get("item_name"));
                    break;
                case DVD:
                    m = new ItemDVD((String)item.get("item_id"), (String)item.get("item_name"));
                    break;
                default:
            }
            ItemLibrary.put(m.getItem_id(), m);
        }
    }

    public void printLibrary(){
        InventoryItem i = null;
        for (String s: ItemLibrary.keySet()){
            i = ItemLibrary.get(s);
            System.out.println(s + ": " + i);
        }
    }

    public boolean checkOut(String id){
        InventoryItem i = ItemLibrary.get(id);
        boolean b = i.checkOut(LocalDate.now());
        ItemLibrary.put(id, i);
        return b;
    }

    public void checkIn(String id){
        InventoryItem i = ItemLibrary.get(id);
        i.checkIn();
        ItemLibrary.put(id, i);
    }
}
