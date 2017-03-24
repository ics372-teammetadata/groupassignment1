package com.metadata.LibraryDomain;

import java.time.LocalDate;
import java.time.Period;

public class InventoryItem {

    /**
     *      Variables
     */


    protected String id, name, type;
    protected LocalDate checkoutDate = null;
    protected LocalDate dueDate = null;
    protected boolean checkedOut = false;
    protected int daysUntilDue = 0;
    protected String checkOutString;
    protected String dueDateString;
    protected String checkedOutToUserCardNumber;


    /**
     *      Basic Constructor
     *
     *      @param itemID
     *      @param itemName
     *      @param itemType
     */

    public InventoryItem(String itemID, String itemName, String itemType){
        id = itemID;
        name = itemName;
        type = itemType;
    }

    /**
     *      Constructor for com.metadata.LibraryDomain.InventoryItem created from previously loaded file
     *       @param itemID
     *      @param itemName
     * @param itemType
     * @param isCheckedOut
     * @param due
     * @param checkOutDt
     * @param checkedOutTo
     */

    public InventoryItem(String itemID, String itemName, String itemType, boolean isCheckedOut, String due, String checkOutDt, String checkedOutTo){
        id = itemID;
        name = itemName;
        type = itemType;
        checkedOut = isCheckedOut;
        checkedOutToUserCardNumber = checkedOutTo;

        if(due != null) {
            dueDate = LocalDate.parse(due);
        }
        if(checkOutDt != null){
            checkoutDate = LocalDate.parse(checkOutDt);
        }

    }

    /**
     *      Check in/out related methods
     */

    public void checkOut(String loggedOnUserCardNumber){
        checkedOut = true;
        checkoutDate = LocalDate.now();
        dueDate = checkoutDate.plusDays(7);
        checkedOutToUserCardNumber = loggedOnUserCardNumber;
    }
    public void checkIn(){
        checkedOut = false;
        checkoutDate = null;
        dueDate = null;
        checkedOutToUserCardNumber = null;
    }
    public boolean isCheckedOut(){
        return checkedOut;
    }

    /**
     *      Getters
     */

    public String getCheckoutDate(){
        if(checkoutDate == null){
            //return null if Date dueDate is NULL
            return null;
        }
        else{
            return  checkoutDate.toString();
        }
    }
    public String getDueDate(){
        if(dueDate == null){
            //return null if Date dueDate is NULL
            return null;
        }
        else{
            return dueDate.toString();
        }
    }
    public int getDaysUntilDue(){
        if(dueDate != null) {
            daysUntilDue = Period.between(LocalDate.now(), dueDate).getDays();
            return daysUntilDue;
        }
        else return 0;
    }
    public String getID(){
        return id;
    }
    public String getType(){
        return type;
    }
    public String getName(){
        return name;
    }
    public String getCheckedOutToUserCardNumber(){return checkedOutToUserCardNumber;}


    /**
     *      toString method, used by UI textArea1 to display information about an individual item to the user
     */
    public String toString(){
        if(this.isCheckedOut()){
            checkOutString = "Item is checked out\n";
        } else {
            checkOutString = "Item is checked in\n";
        }
        if(this.getDueDate() == null ){
            dueDateString = "";
        } else {
            dueDateString = "Item is due on " + this.getDueDate() + "\n";
        }
        return
        ("ID : " + this.getID() + "\n" +
        "Item : " + this.getName() + "\n" +
        "Type : " + this.getType() + "\n" );
    }
}
