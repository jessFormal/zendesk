package au.zendesk.test;

import java.util.List;

/*
 * Utility Class for ZendeskSystem
 */
public class ZendeskSystemUtils {
	
	private ZendeskSystemUtils() {
		throw new IllegalStateException("Zendesk System Utility Helper Class.");
	}
	
	/**
	 * The method will find the first occurrence of value in the list and return the index
	 * @param tickets The ticket list to be operated on 
	 * @param assigneeId The assigneeId value
	 * @return index The first occurrence of value in Ticket list
	 */
	public static int binarySearchModified(List<Ticket> tickets, String assigneeId) {
		int left = 0;
		int right = tickets.size() - 1;
		
		if(assigneeId.isEmpty()) {
			assigneeId = Ticket.EMPTY_VALUE;
		}
		long valueLong = Long.parseLong(assigneeId);
		
		while (left <= right) {
			int mid = (left + right)/2;
			long currValue = Long.parseLong(tickets.get(mid).getAssigneeId());
			
			// if current value matches assigneeId and is either
			// the first index or the left index does not match the assigneeId
			// return the current index
			if ( currValue == valueLong && 
					( mid == 0 || Long.parseLong(
							tickets.get(mid - 1).getAssigneeId()) != valueLong )) {
				return mid;
			// Current value is bigger than the value so inspect the left-side of array	
			} else if ( valueLong <= currValue) {
				right = mid -1;
			// Current value is lower than the value so inspect the right-side of array
			} else {
				left = mid + 1;
			}
		}
		return -1;
	}
}
