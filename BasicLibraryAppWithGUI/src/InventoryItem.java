import java.time.LocalDate;
import java.time.Period;

public class InventoryItem {
    protected String id, name, type;
    protected LocalDate checkoutDate = null;
    protected LocalDate dueDate = null;
    protected boolean checkedOut = false;
    protected int daysUntilDue = 0;
    protected String checkOutString;
    protected String dueDateString;

    //constructors
    InventoryItem(){
        id = "test";
        name = "itemName";
        type = "test";
        checkedOut = true;
        LocalDate dDate = LocalDate.now();
        dueDate = dDate;
        LocalDate cDate = LocalDate.now();
        checkoutDate = cDate;
    }
    //constructors
    InventoryItem(String id){
        id = this.id;
        name = "itemName";
        type = "test";
        checkedOut = true;
        LocalDate dDate = LocalDate.now();
        dueDate = dDate;
        LocalDate cDate = LocalDate.now();
        checkoutDate = cDate;
    }
    InventoryItem(String idNumber, String itemName, String itemType){
        id = idNumber;
        name = itemName;
        type = itemType;
    }
    InventoryItem(String idNumber, String itemName, String itemType, boolean isCheckedOut, String due, String checkOutDt){
        id = idNumber;
        name = itemName;
        type = itemType;
        checkedOut = isCheckedOut;
        LocalDate dDate = LocalDate.parse(due);
        dueDate = dDate;
        LocalDate cDate = LocalDate.parse(checkOutDt);
        checkoutDate = cDate;
    }
    InventoryItem(InventoryItem i){
        if(i == null){
            System.out.println("Fatal error");
            System.exit(0);
        }
        id = i.id;
        name = i.name;
        type = i.type;
    }

    //checkin/out methods
    public void checkOut(){
        checkedOut = true;
        checkoutDate = LocalDate.now();
        dueDate = checkoutDate.plusDays(7);
    }
    public void checkIn(){
        checkedOut = false;
        checkoutDate = null;
        dueDate = null;
    }
    public boolean isCheckedOut(){
        return checkedOut;
    };

    //getters
    public String getCheckoutDate(){
        if(checkoutDate == null){
            return null;
        }
        else{
            return  checkoutDate.toString();
        }
    }
    public String getDueDate(){
        if(dueDate == null){
            return null;
        }
        else{
            return dueDate.toString();
        }
    }
    public int getDaysUntilDue(){
        if(dueDate != null) {
            daysUntilDue = Period.between(LocalDate.now(), dueDate).getDays();
            return daysUntilDue;
        }
        else return 0;
    }
    public String getID(){
        return id;
    }
    public String getType(){
        return type;
    }
    public String getName(){
        return name;
    }

    public String toString(){
        if(this.isCheckedOut()){
            checkOutString = "Item is checked out\n";
        } else {
            checkOutString = "Item is checked in\n";
        }
        if(this.getDueDate() == null){
            dueDateString = "";
        } else {
            dueDateString = "Item is due on " + this.getDueDate() + "\n";
        }
        return
        ("ID : " + this.getID() + "\n" +
        "Item : " + this.getName() + "\n" +
        "Type : " + this.getType() + "\n" );
    }
}
