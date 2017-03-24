import com.metadata.LibraryDomain.Member;
import com.metadata.LibraryDomain.MemberList;
import junit.framework.TestCase;
/**
 * Created by chris on 3/21/2017.
 */
public class MemberListTest extends TestCase {
    //Construct new member to use for testing
    Member member = new Member("u999", "Geralt of Rivia", "123456789");
    //Construct new list
    MemberList memberList = new MemberList();
    public void testGetMemberByID() throws Exception {
        //verify that the getMemberByID method returns the proper ID number
        memberList.add(member);
        assertEquals("u999", memberList.getMemberByID("u999").getID());

        memberList.remove(member);
        assertNull(memberList.getMemberByID("u999"));
    }

    public void testGetMemberByCardNumber() throws Exception {
        //verify that the getMemberCardNumber method returns the proper card number
        memberList.add(member);
        assertEquals("123456789", memberList.getMemberByCardNumber("123456789").getCardNumber());

        memberList.remove(member);
        assertNull(memberList.getMemberByCardNumber("123456789"));
    }

}