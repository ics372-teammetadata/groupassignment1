/**
 * Created by chris on 1/20/2017.
 */

public class CD extends InventoryItem {

    /**
     * Variables
     */

    private String artist = "";

    /**
     * Constructor
     *
     * @param idNumber
     * @param itemName
     * @param itemType
     * @param artistName
     */

    public CD (String idNumber, String itemName, String itemType, String artistName){
        super(idNumber, itemName, itemType);
        artist = artistName;
    }

    /**
     * Constructor
     *
     * @param idNumber
     * @param itemName
     * @param itemType
     * @param artistName
     * @param isCheckedOut
     * @param due
     * @param ckOut
     * @param checkedOutTo
     */
    public CD (String idNumber, String itemName, String itemType, String artistName, boolean isCheckedOut, String due, String ckOut, String checkedOutTo){
        super(idNumber, itemName, itemType, isCheckedOut, due, ckOut, checkedOutTo);
        artist = artistName;
    }

    /**
     * Method name : get Artist
     *
     * @return artist
     */

    public String getArtist(){
        return artist;
    }


    /**
     * Moethod name: toString()
     *
     * @return String
     *
     * used by UI textArea1 to display information about an individual item to the user
     * used by UI textArea1 to display information about an individual item to the user
     */
    public String toString(){
        return super.toString() +
        "Artist: " + this.getArtist() + "\n" +
        checkOutString + dueDateString;
    }

}
