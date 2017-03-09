/**
 * Created by chris on 1/20/2017.
 **/

import java.time.LocalDate;
public class Book extends InventoryItem {
    //////////////
    // Variables
    //////////////

    private String author = "";

    //////////////////
    //Constructors
    //////////////////

    public Book(String idNumber, String itemName, String itemType, String authorName){
        super(idNumber, itemName, itemType);
        author = authorName;
    }

    public Book(String idNumber, String itemName, String itemType, String authorName, boolean isCheckedOut, String due, String ckOut){
        super(idNumber, itemName, itemType, isCheckedOut, due, ckOut);
        author = authorName;
    }

    //////////////
    // Methods
    //////////////

    public String getAuthor(){
        return author;
    }

    //Overrides parent method
    public void checkOut(){
        checkedOut = true;
        checkoutDate = LocalDate.now();
        dueDate = checkoutDate.plusDays(21);
    }
    //toString method, used by UI textArea1 to display information about an individual item to the user
    //toString method - appends string to parent toString method
    public String toString(){
        return super.toString() +
        "Author: " + this.getAuthor() + "\n" +
        checkOutString + dueDateString ;
    }

}
