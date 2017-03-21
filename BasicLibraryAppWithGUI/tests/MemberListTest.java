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
        memberList.add(member);
        assertEquals("Geralt of Rivia", memberList.getMemberByID("u999").getName());
    }

    public void testGetMemberByCardNumber() throws Exception {
        memberList.add(member);
        assertEquals("Geralt of Rivia", memberList.getMemberByCardNumber("123456789").getName());
    }

}