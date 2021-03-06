package com.metadata.LibraryDomain;

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

	public Member getMemberByUsername(String s) {
		for (Iterator iterator = this.iterator(); iterator.hasNext(); ) {
			Member item = (Member) iterator.next();
			if (item.getName().equals(s)) {
				return item;
			}
		}
		return null;
	}
}
