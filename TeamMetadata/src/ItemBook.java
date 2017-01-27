import java.time.LocalDate;

/**
 * Created by Andrew on 1/23/2017.
 */
public class ItemBook extends InventoryItem {
    private String item_author;

    @Override
    public boolean checkOut(LocalDate date) {
        if (super.checkOut(date)){
            setDueDate(date.plusWeeks(3));
            return true;
        }
        else return false;
    }

    public ItemBook(String id, String name, String author){
        super(id, name);
        item_author = author;
    }

    public String getItem_author() {
        return item_author;
    }

    public void setItem_author(String item_author) {
        this.item_author = item_author;
    }

    @Override
    public String toString(){
        return super.toString() + item_author;
    }
}
