import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;

/**
 * Created by chris on 1/23/2017.
 */
public class Library extends ArrayList<InventoryItem>{

    Library(Library l){

    }
    Library(){

    }

    public InventoryItem getItemByID(String s){
        InventoryItem y = null;

        try {
            for (InventoryItem x : this) {
                if (x.getID().equals(s)) {
                    y = new InventoryItem(x);
                }
            }
            if (y != null) {
                return y;
            }
        }catch (Exception NullPointerException){
            System.exit(0);
        }
        return y;
    }

    public void viewList(){
        for(int x = 0; x < this.size(); x++) {
            System.out.println(this.get(x).getType() + " : " + this.get(x).getID() + " :  " + this.get(x).getName());
            if(this.get(x).isCheckedOut()){
                System.out.println("Item is checked out");
            }else{
                System.out.println("Item is checked in");
            }
            System.out.println();
        }
    }
}
