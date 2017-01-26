/**
 * Created by chris on 1/21/2017.
 */
public class Magazine extends InventoryItem {

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

}
