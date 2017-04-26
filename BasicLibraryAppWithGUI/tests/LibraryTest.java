import com.metadata.LibraryDomain.CD;
import com.metadata.LibraryDomain.Library;
import junit.framework.TestCase;

import java.util.Iterator;

/**
 * Created by Christopher on 3/7/2017.
 */
public class LibraryTest extends TestCase {
    Library l = new Library();

    public void testGetItemByID() throws Exception {

        //Test for id match
        CD cd = new CD("id123", "OK Computer", "com.metadata.LibraryDomain.CD", "Radiohead", null, null, null, "N/A");
        l.add(cd);
        assertEquals(cd, l.getItemByID("id123"));

        // test for scenario when id is not found
        Library lib = new Library();
        assertEquals(null, lib.getItemByID("id123"));

        //test iterator
        Iterator iterator = l.iterator();
        assertTrue(iterator.hasNext());

        //test cd getID method
        assertTrue(cd.getID().equals("id123"));

        //test for null value
        assertNull(l.getItemByID("id123eeeww"));
    }

    public void testSort() throws Exception{
        CD cd2 = new CD("id124", "Black Album", "com.metadata.LibraryDomain.CD", "Metallica", null, null, null, "N/A");
        l.add(cd2);
        l.sort();
        assertEquals("Black Album", l.get(0).getName());
    }
}