import com.metadata.LibraryDomain.Member;
import junit.framework.TestCase;

/**
 * Created by chris on 3/21/2017.
 */
public class MemberTest extends TestCase {

    //Construct new member to use for testing
    Member member = new Member("u999", "Geralt of Rivia", "123456789");

    public void testGetID() throws Exception {
        assertEquals("u999", member.getID());
    }

    public void testGetCardNumber() throws Exception {
        assertEquals("123456789", member.getCardNumber());
    }

    public void testGetName() throws Exception {
        assertEquals("Geralt of Rivia", member.getName());
    }

}