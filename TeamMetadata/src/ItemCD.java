import java.time.LocalDate;

/**
 * Created by Andrew on 1/23/2017.
 */
public class ItemCD extends InventoryItem {
    private String item_artist;

    public ItemCD(String id, String name, String artist){
        super(id, name);
        item_artist = artist;
    }

    public String getItem_artist() {
        return item_artist;
    }

    public void setItem_artist(String item_artist) {
        this.item_artist = item_artist;
    }

    @Override
    public String toString(){
        return super.toString() + item_artist;
    }
}
