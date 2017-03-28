

import com.metadata.LibraryDomain.DVD;
import junit.framework.TestCase;

/**
 * Created by Christopher on 3/7/2017.
 */
public class DVDTest extends TestCase {
    public void testToString() throws Exception {
        //test that  toString method returns a non-null value

        DVD d = new DVD("1234", "Die Hard", "com.metadata.LibraryDomain.DVD");
        DVD dvd = new DVD("1234", "Die Hard", "com.metadata.LibraryDomain.DVD", null, null, null);
        assertNotNull(d.toString());
        assertNotNull(dvd.toString());
    }
}