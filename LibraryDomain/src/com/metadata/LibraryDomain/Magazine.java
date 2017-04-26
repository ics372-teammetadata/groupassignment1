package com.metadata.LibraryDomain;

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
     * @param due
     * @param ckOut
     */

    public Magazine(String idNumber, String itemName, String itemType, String due, String ckOut, String checkedOutTo, String status){
        super(idNumber, itemName, itemType, "", due, ckOut, checkedOutTo,status);
    }

    /**
     * Constructor
     *
     * @param idNumber
     * @param itemName
     * @param itemType
     * @param vol
     * @param due
     * @param ckOut
     * @param checkedOutTo
     */
    public Magazine(String idNumber, String itemName, String itemType, String vol, String due, String ckOut, String checkedOutTo, String status){
        super(idNumber, itemName, itemType, "", due, ckOut, checkedOutTo, status);
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
