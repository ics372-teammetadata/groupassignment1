import junit.framework.TestCase;


/**
 * Created by Christopher on 3/7/2017.
 */
public class CDTest extends TestCase {

    CD c = new CD("id123", "OK Computer", "CD", "Radiohead");
    CD cd = new CD("id123", "OK Computer", "CD", "Radiohead", false, null, null, null);

    //test CD getArtist() method
    public void testGetArtist() throws Exception {
        assertEquals("Radiohead",c.getArtist());
        assertEquals("Radiohead",cd.getArtist());
    }

    //test CD toString() method
    public void testToString() throws Exception {
        //test that  toString method returns a non-null value
        assertNotNull(c.toString());
        assertNotNull(cd.toString());
    }

}