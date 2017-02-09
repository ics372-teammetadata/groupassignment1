/**
 * Created by chris on 1/20/2017.
 */

import java.time.LocalDate;
public class CD extends InventoryItem {
    String artist = "";

    public CD (){
        super();
        artist = "";
    }

    public CD (String idNumber, String itemName, String itemType, String artistName){
        super(idNumber, itemName, itemType);
        artist = artistName;
    }
    public CD (String idNumber, String itemName, String itemType, String artistName, boolean isCheckedOut, String due, String ckOut){
        super(idNumber, itemName, itemType, isCheckedOut, due, ckOut);
        artist = artistName;
    }

    public CD(CD c){
        super(c);
        if(c == null){
            System.out.println("Fatal error");
            System.exit(0);
        }
        artist = c.artist;
    }
    public String getArtist(){
        return artist;
    }
}