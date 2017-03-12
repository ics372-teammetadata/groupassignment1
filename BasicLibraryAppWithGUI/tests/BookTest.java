import junit.framework.TestCase;

import java.time.LocalDate;

/**
 * Created by Christopher on 3/7/2017.
 */
public class BookTest extends TestCase {

    Book b = new Book("1234","1984", "Book", "George Orwell" );
    Book book = new Book("1234","1984", "Book", "George Orwell", false, null, null );

    public void testGetAuthor() throws Exception {
        assertEquals("George Orwell",b.getAuthor());
        assertEquals("George Orwell",book.getAuthor());
    }

    public void testCheckOut() throws Exception {
        b.checkOut();
        book.checkOut();
        assertEquals(LocalDate.now().plusDays(21).toString(), b.getDueDate());
        assertEquals(LocalDate.now().plusDays(21).toString(), book.getDueDate());
    }

    public void testToString() throws Exception {
        assertNotNull(b.toString());
        assertNotNull(book.toString());
    }

}