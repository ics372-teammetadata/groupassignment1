package com.metadata.LibraryDomain;

import java.util.ArrayList;
import java.util.Iterator;

public class StaffList extends ArrayList<Staff>
{
	public Member getStaffByID(String s) {
		for (Iterator iterator = this.iterator(); iterator.hasNext(); ) {
			Member item = (Member) iterator.next();
			if (item.getID().equals(s)) {
				return item;
			}
		}
		return null;
	}

	public Member getStaffByUsername(String s) {
		for (Iterator iterator = this.iterator(); iterator.hasNext(); ) {
			Member item = (Member) iterator.next();
			if (item.getName().equals(s)) {
				return item;
			}
		}
		return null;
	}
}
