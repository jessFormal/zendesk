package au.zendesk.test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;

/**
 * Ticket Utility Class to help create Ticket Objects from JSONObjects
 */
public class TicketUtils {
	
	private TicketUtils() {
	    throw new IllegalStateException("Ticket Utility Helper Class");
	}
	
	public static Ticket createTicket(JSONObject ticketObject) {
		String type = getValue(ticketObject, Ticket.Field.TYPE.get());
		String subject = getValue(ticketObject, Ticket.Field.SUBJECT.get());
		String createdAt = getValue(ticketObject, Ticket.Field.CREATED_AT.get());
		String id = getValue(ticketObject, Ticket.Field.ID.get());
		Set<String> tags = getTicketTag(ticketObject);
		String assigneeId = getValue(ticketObject, Ticket.Field.ASSIGNEE_ID.get());
		
		 return new Ticket(id, type, createdAt,assigneeId, 
				subject, tags);
	}
	
	public static String getValue(JSONObject ticketObject, String key) {
		if (ticketObject.containsKey(key)) {
			String val = String.valueOf(ticketObject.get(key));
			return ZendeskSystemUtils.encodeUTF8(val);
		} else {
			return "";
		}
	}
	
	public static Set<String> getTicketTag(JSONObject ticketObject) {
		Set<String> tags = new HashSet<>();
		String tagsKey = Ticket.Field.TAGS.get();
		
		if (ticketObject.containsKey(tagsKey)) {
			@SuppressWarnings("unchecked")
			List<String> tempTags = (List<String>) ticketObject.get(tagsKey);
			tags = new HashSet<>(tempTags);
		}
		return tags;
	}
}
