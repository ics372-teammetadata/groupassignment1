import java.io.File;

/**
 * Created by chris on 1/21/2017.
 */
public class DVD extends InventoryItem {

    public DVD (){
        super();
    }
    public DVD (String idNumber, String itemName, String itemType){
        super(idNumber, itemName, itemType);
    }
    public DVD(DVD d){
        super(d);
        if(d == null){
            System.out.println("Fatal error");
            System.exit(0);
        }
    }

}
