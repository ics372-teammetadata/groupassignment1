package com.metadata.LibraryDomain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class InventoryItem {

    /**
     *      Variables
     */

    protected String id, name, type;
    protected Date checkoutDate = null;
    protected Date dueDate = null;
    protected String checkOutString;
    protected String dueDateString;
    protected String checkedOutToUserCardNumber;

    private static final String dateFormatString = "MM/dd/yyyy";


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

        SimpleDateFormat formatter = new SimpleDateFormat(dateFormatString);
        if(due != null) {
            try{
                dueDate = formatter.parse(due); //LocalDate.parse(due);
            }
            catch (ParseException e){

            }

        }
        if(checkOutDt != null){
            try {
                checkoutDate = formatter.parse(checkOutDt);
            }
            catch (ParseException e){

            }
        }

    }

    /**
     *      Check in/out related methods
     */

    public void checkOut(String loggedOnUserCardNumber){
        checkoutDate = new Date();
        setCheckoutDate(7);
        checkedOutToUserCardNumber = loggedOnUserCardNumber;
    }

    protected void setCheckoutDate(int days){
        Calendar cal = Calendar.getInstance();
        cal.setTime(checkoutDate);
        cal.add(Calendar.DATE, days);
        dueDate = cal.getTime();
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
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormatString);
            return  formatter.format(checkoutDate);
        }
    }
    public String getDueDate(){
        if(dueDate == null){
            //return null if Date dueDate is NULL
            return null;
        }
        else{
            SimpleDateFormat formatter = new SimpleDateFormat(dateFormatString);
            return formatter.format(dueDate);
        }
    }

    // I would really like to get rid of this method
    public int getDaysUntilDue(){
        if(dueDate != null) {
            Calendar nowCal = Calendar.getInstance();
            Calendar dueCal = Calendar.getInstance();
            nowCal.setTime(new Date());
            dueCal.setTime(dueDate);
            return dueCal.get(Calendar.DATE) - nowCal.get(Calendar.DATE);
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
