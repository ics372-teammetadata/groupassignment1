package unitTests;

import junit.framework.TestCase;

/**
 * Created by Christopher on 3/7/2017.
 */
public class LibraryTest extends TestCase {
    public void testGetItemByID() throws Exception {
        appSrc.Library l = new appSrc.Library();
        assertNotNull(l);
    }
}