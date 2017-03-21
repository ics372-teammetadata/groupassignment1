import javafx.application.Application;
import javafx.stage.Stage;
import junit.framework.TestCase;

/**
 * Created by chris on 3/21/2017.
 */
public class UI2Test extends TestCase {
    public void testStart() throws Exception {
        try {
            Stage s = new Stage();
        }catch(ExceptionInInitializerError e){
            System.out.println("Caught ExceptionInInitializerError");}
    }

    public void testMain() throws Exception {
        try{
            Application.launch("testArgs");
        }catch(RuntimeException e){
            System.out.println("Caught Runtime Exception");}
    }

}