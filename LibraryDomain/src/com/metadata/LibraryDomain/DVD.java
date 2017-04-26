package com.metadata.LibraryDomain;

/**
 * Created by chris on 1/21/2017.
 */
public class DVD extends InventoryItem {

    /**
     * Constructor
     *
     * @param idNumber
     * @param itemName
     * @param itemType
     */
    public DVD (String idNumber, String itemName, String itemType){
        super(idNumber, itemName, itemType);
    }

    /**
     * Constructor
     *
     * @param idNumber
     * @param itemName
     * @param itemType
     * @param due
     * @param ckOut
     * @param checkedOutTo
     */
    public DVD(String idNumber, String itemName, String itemType, String due, String ckOut, String checkedOutTo, String status){
        super(idNumber, itemName, itemType, "", due, ckOut, checkedOutTo, status);
    }

    /**
     *
     * toString method, used by UI textArea1 to display information about an individual item to the user
     * toString method - appends string to parent toString method
     *
     * @return String
     */
    public String toString(){
        return super.toString() +
                checkOutString + dueDateString;
    }

}
