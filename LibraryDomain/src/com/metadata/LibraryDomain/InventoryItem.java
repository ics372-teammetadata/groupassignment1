package com.metadata.LibraryDomain;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;

public class InventoryItem {

    /**
     *      Variables
     */

    protected String id, name, type;
    protected LocalDate checkoutDate = null;
    protected LocalDate dueDate = null;
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
     *      Constructor for InventoryItem created from previously loaded file
     *      @param itemID
     *      @param itemName
     *      @param itemType
     *      @param due
     *      @param checkOutDt
     *      @param checkedOutTo
     */

    public InventoryItem(String itemID, String itemName, String itemType, String due, String checkOutDt, String checkedOutTo){
        id = itemID;
        name = itemName;
        type = itemType;
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
        checkoutDate = LocalDate.now();
        dueDate = checkoutDate.plusDays(7);
        checkedOutToUserCardNumber = loggedOnUserCardNumber;
    }
    public void checkIn(){
        checkoutDate = null;
        dueDate = null;
        checkedOutToUserCardNumber = null;
    }
    public boolean isCheckedOut(){
        return (dueDate != null);
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
