import java.util.Date;

public class InventoryItem {
    private String id, name, type;
    private Date checkoutDate;
    private Date checkinDate;
    private boolean checkedOut = false;
    private boolean checkedIn = true;

    InventoryItem(){
        id = "";
        name = "";
        type = "";
        checkoutDate = null;
    }
    InventoryItem(String idNumber, String itemName, String itemType){
        id = idNumber;
        name = itemName;
        type = itemType;
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
    public void checkOut(){
        checkedOut = true;
        checkoutDate = new Date();
    }
    public void checkIn(){
        checkedOut = false;
        checkoutDate = new Date();
    }
    public void getCheckoutDate(){
        System.out.println(checkoutDate);
    }
    public boolean isCheckedOut(){
        return checkedOut;
    };
    public String getName(){
        return name;
    }
    public String getID(){
        return id;
    }
    public String getType(){
        return type;
    }
}
