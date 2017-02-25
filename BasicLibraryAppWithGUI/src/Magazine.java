/**
 * Created by chris on 1/21/2017.
 */
public class Magazine extends InventoryItem {

    //////////////////
    //Constructors
    //////////////////

    Magazine(String idNumber, String itemName, String itemType){
        super(idNumber, itemName, itemType);
    }

    Magazine(String idNumber, String itemName, String itemType, boolean isCheckedOut, String due, String ckOut){
        super(idNumber, itemName, itemType, isCheckedOut, due, ckOut);
    }

    //////////////
    // Methods
    //////////////

    //toString method, used by UI textArea1 to display information about an individual item to the user
    //toString method - appends string to parent toString method
    public String toString(){
        return super.toString() +
                checkOutString + dueDateString;
    }

}
