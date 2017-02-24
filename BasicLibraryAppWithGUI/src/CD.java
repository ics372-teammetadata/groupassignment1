/**
 * Created by chris on 1/20/2017.
 */

import java.time.LocalDate;
public class CD extends InventoryItem {
    String artist = "";

    //constructors
    public CD (String idNumber, String itemName, String itemType, String artistName){
        super(idNumber, itemName, itemType);
        artist = artistName;
    }
    public CD (String idNumber, String itemName, String itemType, String artistName, boolean isCheckedOut, String due, String ckOut){
        super(idNumber, itemName, itemType, isCheckedOut, due, ckOut);
        artist = artistName;
    }

    //methods
    public String getArtist(){
        return artist;
    }

    //toString method - appends string to parent toString method
    public String toString(){
        return super.toString() +
        "Artist: " + this.getArtist() + "\n" +
        checkOutString + dueDateString;
    }

}
