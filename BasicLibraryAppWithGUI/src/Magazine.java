/**
 * Created by chris on 1/21/2017.
 */
public class Magazine extends InventoryItem {

    //constructors
    public Magazine(){
        super();
    }
    public Magazine(String idNumber, String itemName, String itemType){
        super(idNumber, itemName, itemType);
    }

    public Magazine(Magazine m){
        super(m);
        if(m == null){
            System.out.println("Fatal error");
            System.exit(0);
        }
    }
    public Magazine(String idNumber, String itemName, String itemType, boolean isCheckedOut, String due, String ckOut){
        super(idNumber, itemName, itemType, isCheckedOut, due, ckOut);
    }

    //toString method - appends string to parent toString method
    public String toString(){
        return super.toString() +
                checkOutString + dueDateString;
    }

}
