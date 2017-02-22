
import java.util.ArrayList;

/**
 * Created by chris on 1/20/2017.
 */
public class Test {

    public static void main(String[] args){
        Library library  = new Library();
        FileProcessor j = new FileProcessor();

        //collect data form file and populate library
        library = j.processData();

        library.viewList();
        InventoryItem i1 =  library.getItemByID("48934j");
        library.get(2).checkOut();

        //library.getItemByID("1adf4").checkOut();

        i1.checkOut();
        //System.out.println(library.get(0).isCheckedOut());
        //System.out.println(library.get(0).getDaysUntilDue());
        //library.viewList();

        library.getItemByID("1adf4").checkIn();
       //System.out.println(library.getItemByID("1adf4").isCheckedOut());

        //write to file
        j.writeData(library);
    }
}
