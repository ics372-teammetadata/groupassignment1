import junit.framework.TestCase;

/**
 * Created by Christopher on 3/7/2017.
 */
public class MagazineTest extends TestCase {
    Magazine mag = new Magazine("1234", "The Times", "Magazine", false, null, null, null);
    Magazine m = new Magazine("id123", "The Times", "Magazine");
    Magazine magWithVolume = new Magazine("1234", "The Times", "Magazine", "5",false, null, null, null);
    Magazine mWithVolume = new Magazine("id123", "The Times", "Magazine", "6");

    //test toString method to ensure that is returns a non-null value
    public void testToString() throws Exception {
        //test that  toString method returns a non-null value
        assertNotNull(mag.toString());
        assertNotNull(m.toString());
        assertNotNull(magWithVolume.toString());
        assertNotNull(mWithVolume.toString());
    }
    //test Magazine getVolume() method
    public void testGetVolume() throws Exception {
        assertEquals("5",magWithVolume.getVolume());
        assertEquals("6",mWithVolume.getVolume());
    }
}