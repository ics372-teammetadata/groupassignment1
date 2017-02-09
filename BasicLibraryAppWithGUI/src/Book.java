/**
 * Created by chris on 1/20/2017.
 */

import java.time.LocalDate;
public class Book extends InventoryItem {
    String author = "";

    public Book(){
        super();
        author = "";
    }

    public Book(String idNumber, String itemName, String itemType, String authorName){
        super(idNumber, itemName, itemType);
        author = authorName;
    }
    public Book(String idNumber, String itemName, String itemType, String authorName, boolean isCheckedOut, String due, String ckOut){
        super(idNumber, itemName, itemType, isCheckedOut, due, ckOut);
        author = authorName;
    }
    public Book(Book b){
        super(b);
        if(b == null){
            System.out.println("Fatal error");
            System.exit(0);
        }
        author = b.author;
    }
    public String getAuthor(){
        return author;
    }
}