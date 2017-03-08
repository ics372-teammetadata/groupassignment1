package unitTests;

import javafx.application.Application;
import javafx.stage.Stage;
import junit.framework.TestCase;

/**
 * Created by Christopher on 3/7/2017.
 */
public class UITest extends TestCase {
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