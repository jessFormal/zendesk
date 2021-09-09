package au.zendesk.test;

import java.util.Comparator;

/**
 * Comparator to sort tickets by assignee id in ascending order
 */
public class SortByTicketAssigneeId implements Comparator<Ticket>{

	@Override
	public int compare(Ticket t1, Ticket t2) {;
		String t1AssigneeId = t1.getAssigneeId();
		String t2AssigneeId = t2.getAssigneeId();
		
		if (t1AssigneeId.isEmpty()) {
			t1AssigneeId = Ticket.EMPTY_VALUE;
		}
		if (t2AssigneeId.isEmpty()) {
			t2AssigneeId = Ticket.EMPTY_VALUE;
		}
		
		return Long.compare(Long.parseLong(t1AssigneeId), 
				Long.parseLong(t2AssigneeId));
	}

}
