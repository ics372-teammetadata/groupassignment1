import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by chris on 1/23/2017.
 */

//Inventory Item List Container
public class Library extends ArrayList<InventoryItem>{
    //lookup Inventory Item by ID and return an InventoryItem if cardRepo match is found, or else null is returned
    public InventoryItem getItemByID(String s){
        for (Iterator iterator = this.iterator();iterator.hasNext();){
            InventoryItem item = (InventoryItem) iterator.next();
            if (item.getID().equals(s)) {
                return item;
            }
        }
        return null;
    }
}
