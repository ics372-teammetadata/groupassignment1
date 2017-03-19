import junit.framework.TestCase;

import java.util.Iterator;

/**
 * Created by Christopher on 3/7/2017.
 */
public class LibraryTest extends TestCase {
    public void testGetItemByID() throws Exception {

        //Test for id match
        CD cd = new CD("id123", "OK Computer", "CD", "Radiohead", false, null, null, null);
        Library l = new Library();
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
    }
}