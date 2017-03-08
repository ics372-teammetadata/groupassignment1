package unitTests;

import appSrc.Book;
import junit.framework.TestCase;

import java.time.LocalDate;

/**
 * Created by Christopher on 3/7/2017.
 */
public class BookTest extends TestCase {

    Book b = new Book("1234","1984", "Book", "George Orwell" );
    public void testGetAuthor() throws Exception {
        assertNotNull(b.getAuthor());
    }

    public void testCheckOut() throws Exception {
        b.checkOut();
        assertEquals(LocalDate.now().plusDays(21).toString(), b.getDueDate());
    }

    public void testToString() throws Exception {
        assertNotNull(b.toString());
    }

}