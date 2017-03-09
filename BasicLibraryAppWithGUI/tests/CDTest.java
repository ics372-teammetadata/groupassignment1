import junit.framework.TestCase;


/**
 * Created by Christopher on 3/7/2017.
 */
public class CDTest extends TestCase {

    CD c = new CD("id123", "OK Computer", "CD", "Radiohead");
    CD cd = new CD("id123", "OK Computer", "CD", "Radiohead", false, "2017-02-01", "2017-01-01");

    //test CD getArtist() method
    public void testGetArtist() throws Exception {
        assertEquals("Radiohead",c.getArtist());
        assertEquals("Radiohead",cd.getArtist());
    }

    //test CD toString() method
    public void testToString() throws Exception {
        assertNotNull(c.toString());
        assertNotNull(cd.toString());
    }

}