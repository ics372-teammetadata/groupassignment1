/**
 * Created by chris on 1/21/2017.
 */
public class Magazine extends InventoryItem {

    /**
     * Variables
     */
    private String author = "";

    /**
     *  Constructor
     *
     * @param idNumber
     * @param itemName
     * @param itemType
     */

    public Magazine(String idNumber, String itemName, String itemType){
        super(idNumber, itemName, itemType);
    }

    /**
     * Constructor
     *
     * @param idNumber
     * @param itemName
     * @param itemType
     * @param isCheckedOut
     * @param due
     * @param ckOut
     */

    public Magazine(String idNumber, String itemName, String itemType, boolean isCheckedOut, String due, String ckOut){
        super(idNumber, itemName, itemType, isCheckedOut, due, ckOut);
    }

    /**
     * toString method
     *
     * Used by UI textArea1 to display information about an individual item to the user
     * Appends string to parent toString method
     * @return
     */
    public String toString(){
        return super.toString() +
                checkOutString + dueDateString;
    }

}
