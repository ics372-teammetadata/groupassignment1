import java.util.ArrayList;
import java.util.Iterator;

public class MemberList extends ArrayList<Member>
{
	public Member getMemberByID(String s) {
		for (Iterator iterator = this.iterator(); iterator.hasNext(); ) {
			Member item = (Member) iterator.next();
			if (item.getID().equals(s)) {
				return item;
			}
		}
		return null;
	}

	public Member getMemberByCardNumber(String s) {
		for (Iterator iterator = this.iterator(); iterator.hasNext(); ) {
			Member item = (Member) iterator.next();
			if (item.getCardNumber().equals(s)) {
				return item;
			}
		}
		return null;
	}
}
