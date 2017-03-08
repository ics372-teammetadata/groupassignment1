package unitTests;

import junit.framework.TestCase;
import appSrc.CD;

/**
 * Created by Christopher on 3/7/2017.
 */
public class CDTest extends TestCase {

    CD c = new CD("id123", "OK Computer", "CD", "Radiohead");

    //test CD getArtist() method
    public void testGetArtist() throws Exception {
        assertNotNull(c.getArtist());
    }

    //test CD toString() method
    public void testToString() throws Exception {
        assertNotNull(c.toString());
    }

}