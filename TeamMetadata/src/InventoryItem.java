import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import javafx.scene.control.*;
import sun.util.resources.cldr.ka.LocaleNames_ka;

/**
 * Abstract class contains information for all types of inventory items
 * Created by Andrew on 1/23/2017.
 */
public abstract class InventoryItem {
    private String item_id;
    private String item_name;
    private LocalDate checkedOutDate;
    private LocalDate dueDate;

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public LocalDate getCheckedOutDate() {
        return checkedOutDate;
    }

    public void setCheckedOutDate(LocalDate checkedOutDate) {
        this.checkedOutDate = checkedOutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public InventoryItem(String id, String name){
        item_id = id;
        item_name = name;
        checkedOutDate = null;
        dueDate = null;
    }

    public boolean isCheckedOut(){
        return checkedOutDate != null;
    }

    public boolean checkOut(LocalDate date){
        if (isCheckedOut())return false;
        setCheckedOutDate(date);
        setDueDate(date.plusWeeks(1));
        return true;
    }
    public void checkIn(){
        checkedOutDate = null;
        dueDate = null;
    }

    @Override
    public String toString(){
        return item_name;
    }
}
