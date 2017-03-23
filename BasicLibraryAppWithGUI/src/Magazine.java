/**
 * Created by chris on 1/21/2017.
 */
public class Magazine extends InventoryItem {

    /**
     * Variables
     */
    private String volume = "";

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
     *  Constructor
     *
     * @param idNumber
     * @param itemName
     * @param itemType
     * @param vol
     */
    public Magazine(String idNumber, String itemName, String itemType, String vol){
        super(idNumber, itemName, itemType);
        volume = vol;
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

    public Magazine(String idNumber, String itemName, String itemType, boolean isCheckedOut, String due, String ckOut, String checkedOutTo){
        super(idNumber, itemName, itemType, isCheckedOut, due, ckOut, checkedOutTo);
    }

    /**
     * Constructor
     *
     * @param idNumber
     * @param itemName
     * @param itemType
     * @param vol
     * @param isCheckedOut
     * @param due
     * @param ckOut
     * @param checkedOutTo
     */
    public Magazine(String idNumber, String itemName, String itemType, String vol, boolean isCheckedOut, String due, String ckOut, String checkedOutTo){
        super(idNumber, itemName, itemType, isCheckedOut, due, ckOut, checkedOutTo);
        volume = vol;
    }

    /**
     * Method name: getVolume
     *
     * @return volume
     */

    public String getVolume(){
        return volume;
    }


    /**
     * toString method
     *
     * Used by UI textArea1 to display information about an individual item to the user
     * Appends string to parent toString method
     *
     * @return String
     */
    public String toString(){
        return super.toString() +
                "Volume: " + this.getVolume() + "\n" +
                checkOutString + dueDateString;
    }

}
