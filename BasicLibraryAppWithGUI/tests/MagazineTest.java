import junit.framework.TestCase;

/**
 * Created by Christopher on 3/7/2017.
 */
public class MagazineTest extends TestCase {
    public void testToString() throws Exception {
        //test that  toString method returns a non-null value
        
        Magazine m = new Magazine("id123", "The Times", "Magazine");
        assertNotNull(m.toString());
    }

}