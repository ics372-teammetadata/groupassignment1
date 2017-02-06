import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.beans.property.SimpleListProperty;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by chris on 1/23/2017.
 */
public class Library extends ArrayList<InventoryItem>{

    public InventoryItem getItemByID(String s){
        InventoryItem x = null;

        try {
            /*
            for (InventoryItem x : this) {
                if (x.getID().equals(s)) {
                    y = new InventoryItem(x);
                }
            }
            if (y != null) {
                return y;
            }*/
            for (InventoryItem i : this) {
                if (i.getID().equals(s)) {
                    x = i;
                }
            }
            if (x != null) {
                return x;
            }
        }catch (Exception NullPointerException){
            System.exit(0);
        }
        return x;
    }

    public void viewList(){
        for(int x = 0; x < this.size(); x++) {
            System.out.println(this.get(x).getType() + " : " + this.get(x).getID() + " :  " + this.get(x).getName() + " : Is checked out? " + this.get(x).isCheckedOut() + " : Duedate == " + this.get(x).getDueDate()+ " : Checkout date == " + this.get(x).getCheckoutDate() + " : Days until due == " + this.get(x).getDaysUntilDue());
            System.out.println();
        }
    }
}
