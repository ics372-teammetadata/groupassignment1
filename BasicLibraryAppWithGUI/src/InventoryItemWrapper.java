import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * InventoryItemWrapper wraps the inventory item class to enable it to be used in the TableView
 *
 * Created by Andrew on 4/25/2017.
 */
public class InventoryItemWrapper {
    private String itemId;
    private String itemLocation;
    private final SimpleStringProperty itemName;
    private final SimpleStringProperty itemArtist;
    private final SimpleStringProperty itemType;
    private final SimpleStringProperty itemStatus;

    public InventoryItemWrapper(String id, String location, String name, String artist, String type, String status){
        itemId = id;
        itemLocation = location;
        itemName = new SimpleStringProperty(name);
        itemArtist = new SimpleStringProperty(artist);
        itemType = new SimpleStringProperty(type);
        itemStatus = new SimpleStringProperty(status);
    }

    public String getItemId(){ return itemId; }
    public String getItemLocation(){ return itemLocation; }

    public String getItemName(){ return itemName.get(); }
    public void setItemName(String name){itemName.set(name);}
    public StringProperty itemNameProperty(){return itemName; }

    public String getItemArtist(){ return itemArtist.get(); }
    public void setItemArtist(String artist){ itemArtist.set(artist);}
    public StringProperty itemArtistProperty(){ return itemArtist; }

    public String getItemType(){ return itemType.get(); }
    public void setItemType(String type){ itemType.set(type);}
    public StringProperty itemTypeProperty(){ return itemType; }

    public String getItemStatus(){ return itemStatus.get(); }
    public void setItemStatus(String status){ itemStatus.set(status);}
    public StringProperty itemStatusProperty(){ return itemStatus; }
}
