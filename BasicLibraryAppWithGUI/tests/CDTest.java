import com.metadata.LibraryDomain.CD;
import junit.framework.TestCase;


/**
 * Created by Christopher on 3/7/2017.
 */
public class CDTest extends TestCase {

    CD c = new CD("id123", "OK Computer", "com.metadata.LibraryDomain.CD", "Radiohead");
    CD cd = new CD("id123", "OK Computer", "com.metadata.LibraryDomain.CD", "Radiohead", null, null, null, "N/A");

    //test com.metadata.LibraryDomain.CD getArtist() method
    public void testGetArtist() throws Exception {
        assertEquals("Radiohead",c.getArtist());
        assertEquals("Radiohead",cd.getArtist());
    }

    //test com.metadata.LibraryDomain.CD toString() method
    public void testToString() throws Exception {
        //test that  toString method returns a non-null value
        assertNotNull(c.toString());
        assertNotNull(cd.toString());
    }

}