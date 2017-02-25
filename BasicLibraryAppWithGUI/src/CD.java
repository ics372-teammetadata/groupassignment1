/**
 * Created by chris on 1/20/2017.
 */

import java.time.LocalDate;
public class CD extends InventoryItem {
    private String artist = "";

    //////////////////
    //Constructors
    //////////////////

    CD (String idNumber, String itemName, String itemType, String artistName){
        super(idNumber, itemName, itemType);
        artist = artistName;
    }
    CD (String idNumber, String itemName, String itemType, String artistName, boolean isCheckedOut, String due, String ckOut){
        super(idNumber, itemName, itemType, isCheckedOut, due, ckOut);
        artist = artistName;
    }

    //////////////
    // Methods
    //////////////

    String getArtist(){
        return artist;
    }

    //toString method, used by UI textArea1 to display information about an individual item to the user
    //toString method - appends string to parent toString method
    public String toString(){
        return super.toString() +
        "Artist: " + this.getArtist() + "\n" +
        checkOutString + dueDateString;
    }

}
