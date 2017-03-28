import com.metadata.LibraryDomain.Book;
import junit.framework.TestCase;

import java.time.LocalDate;

/**
 * Created by Christopher on 3/7/2017.
 */
public class BookTest extends TestCase {

    Book b = new Book("1234","1984", "com.metadata.LibraryDomain.Book", "George Orwell" );
    Book book = new Book("1234","1984", "com.metadata.LibraryDomain.Book", "George Orwell", null, null, null );

    public void testGetAuthor() throws Exception {
        assertEquals("George Orwell",b.getAuthor());
        assertEquals("George Orwell",book.getAuthor());
    }

    public void testCheckOut() throws Exception {
        b.checkOut("1234");
        book.checkOut("1234");
        assertEquals(LocalDate.now().plusDays(21).toString(), b.getDueDate());
        assertEquals(LocalDate.now().plusDays(21).toString(), book.getDueDate());
    }

    public void testToString() throws Exception {
        //test that  toString method returns a non-null value
        assertNotNull(b.toString());
        assertNotNull(book.toString());
    }

}