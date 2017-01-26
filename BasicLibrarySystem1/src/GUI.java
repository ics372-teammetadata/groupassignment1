
import java.util.ArrayList;

/**
 * Created by chris on 1/20/2017.
 */
public class GUI {

    public static void main(String[] args){
        Library library  = new Library();
        FileProcessor j = new FileProcessor();
        library = j.processData();
        j.writeData(library);
    }
}
