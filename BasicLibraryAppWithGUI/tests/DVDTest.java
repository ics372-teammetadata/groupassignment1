

import junit.framework.TestCase;

/**
 * Created by Christopher on 3/7/2017.
 */
public class DVDTest extends TestCase {
    public void testToString() throws Exception {
        DVD d = new DVD("1234", "Die Hard", "DVD");
        assertNotNull(d.toString());
    }
}