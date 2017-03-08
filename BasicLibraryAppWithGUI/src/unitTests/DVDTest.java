package unitTests;

import junit.framework.TestCase;

/**
 * Created by Christopher on 3/7/2017.
 */
public class DVDTest extends TestCase {
    public void testToString() throws Exception {
        appSrc.DVD d = new appSrc.DVD("1234", "Die Hard", "DVD");
        assertNotNull(d.toString());
    }
}