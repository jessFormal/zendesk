package au.zendesk.test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class to search and filter a list of Tickets with given field and values
 */
public class TicketPredicate {
	
	private TicketPredicate() {
	    throw new IllegalStateException("Tickets Predicate Helper Class");
	}
	
	public static List<Ticket> filter(List<Ticket> tickets, 
			Predicate<Ticket> predicate) {
		return tickets.parallelStream()
                .filter( predicate )
                .collect(Collectors.<Ticket>toList());
	}
	
	public static Predicate<Ticket> search(String key, String value) {
		return tickets -> tickets.getValue(key).equals(value);
	}
	
	public static Predicate<Ticket> searchTags(String value) {
		return tickets -> tickets.containTag(value);
	}
}
