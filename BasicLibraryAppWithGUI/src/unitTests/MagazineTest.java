package unitTests;
import appSrc.Magazine;

import junit.framework.TestCase;

/**
 * Created by Christopher on 3/7/2017.
 */
public class MagazineTest extends TestCase {
    public void testToString() throws Exception {
        Magazine m = new Magazine("id123", "The Times", "Magazine");
        assertNotNull(m.toString());
    }

}