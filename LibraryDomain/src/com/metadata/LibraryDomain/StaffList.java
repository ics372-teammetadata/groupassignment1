package com.metadata.LibraryDomain;

import java.util.ArrayList;
import java.util.Iterator;

public class StaffList extends ArrayList<Staff>
{
	public Staff getStaffByName(String s) {
		for (Iterator iterator = this.iterator(); iterator.hasNext(); ) {
			Staff item = (Staff) iterator.next();
			if (item.getName().equals(s)) {
				return item;
			}
		}
		return null;
	}

	public Staff getStaffByUsername(String s) {
		for (Iterator iterator = this.iterator(); iterator.hasNext(); ) {
			Staff item = (Staff) iterator.next();
			if (item.getUserName().equals(s)) {
				return item;
			}
		}
		return null;
	}
}
