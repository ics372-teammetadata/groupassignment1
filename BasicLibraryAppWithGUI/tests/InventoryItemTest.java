import com.metadata.LibraryDomain.InventoryItem;
import junit.framework.TestCase;

import java.time.LocalDate;

/**
 * Created by chris on 3/21/2017.
 */
public class InventoryItemTest extends TestCase {
    InventoryItem inv = new InventoryItem("1234","1984", "com.metadata.LibraryDomain.Book" );
    InventoryItem i = new InventoryItem("1234","1984", "com.metadata.LibraryDomain.Book", null, null, null );
    InventoryItem inventory = new InventoryItem("1234","1984", "com.metadata.LibraryDomain.Book","2017-03-01", "2017-02-01", "89844" );

    public void testCheckOut() throws Exception {
        //verify checkOut functionality

        inv.checkOut("454545");
        i.checkOut("454545");
        assertEquals(LocalDate.now().plusDays(7).toString(), inv.getDueDate());
        assertEquals(LocalDate.now().plusDays(7).toString(), i.getDueDate());
    }

    public void testCheckIn() throws Exception {
        //verify checkIn functionality

        i.checkIn();
        inv.checkIn();
        assertFalse(i.isCheckedOut());
        assertFalse(inv.isCheckedOut());
    }

    public void testIsCheckedOut() throws Exception {
        //very functionality of isCheckedOut method, whcih returns a boolean

        inv.checkOut("444555444");
        i.checkOut("444555444");
        assertTrue(i.isCheckedOut());
        assertTrue(inv.isCheckedOut());

        i.checkIn();
        inv.checkIn();
        assertFalse(i.isCheckedOut());
        assertFalse(inv.isCheckedOut());
    }

    public void testGetCheckoutDate() throws Exception {
        //verify that the getCheckOutDate method returns the proper date

        inv.checkOut("444555444");
        i.checkOut("444555444");
        assertEquals(LocalDate.now().toString(), inv.getCheckoutDate());
        assertEquals(LocalDate.now().toString(), i.getCheckoutDate());

        i.checkIn();
        inv.checkIn();
        assertNull(inv.getCheckoutDate());
        assertNull( i.getCheckoutDate());
    }

    public void testGetDueDate() throws Exception {
        //verify that the getDateDate method returns the proper date

        inv.checkOut("444555444");
        i.checkOut("444555444");
        assertEquals(LocalDate.now().plusDays(7).toString(), inv.getDueDate());
        assertEquals(LocalDate.now().plusDays(7).toString(), i.getDueDate());

        i.checkIn();
        inv.checkIn();
        assertNull(inv.getDueDate());
        assertNull( i.getDueDate());
    }

    public void testGetDaysUntilDue() throws Exception {

        //verify that the getDaysUntilDue method returns the proper interger value
        inv.checkOut("444555444");
        i.checkOut("444555444");
        assertEquals(7, inv.getDaysUntilDue());
        assertEquals(7, i.getDaysUntilDue());

        i.checkIn();
        inv.checkIn();
        assertEquals(0, inv.getDaysUntilDue());
        assertEquals(0, i.getDaysUntilDue());
    }

    public void testGetID() throws Exception {
        //very that the getType method returns the expected type

        assertEquals("com.metadata.LibraryDomain.Book", i.getType());
        assertEquals("com.metadata.LibraryDomain.Book", i.getType());
    }

    public void testGetType() throws Exception {
        //very that the getType method returns the expected type

        assertEquals("com.metadata.LibraryDomain.Book", i.getType());
        assertEquals("com.metadata.LibraryDomain.Book", i.getType());
    }

    public void testGetName() throws Exception {
        //very that the getName method returns the expected name

        assertEquals("1984", i.getName());
        assertEquals("1984", i.getName());
    }

    public void testGetCheckedOutToUserCardNumber() throws Exception {
        //very that the proper card number for the userName who checked out a particular item

        inv.checkOut("9999");
        i.checkOut("9999");

        assertEquals("9999", i.getCheckedOutToUserCardNumber());
        assertEquals("9999", inv.getCheckedOutToUserCardNumber());
    }

    public void testToString() throws Exception {
        //test that  toString method returns a non-null value

        assertNotNull(i.toString());
        assertNotNull(inv.toString());
    }

}