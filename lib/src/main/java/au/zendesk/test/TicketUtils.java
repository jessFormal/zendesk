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
		String type = getTicketType(ticketObject);
		String subject = getTicketSubject(ticketObject);
		String createdAt = getTicketCreatedAt(ticketObject);
		String id = getTicketId(ticketObject);
		Set<String> tags = getTicketTag(ticketObject);
		String assigneeId = getTicketAssigneeId(ticketObject);
		
		 return new Ticket(id, type, createdAt,assigneeId, 
				subject, tags);
	}
	
	public static String getTicketType(JSONObject ticketObject) {
		String typeKey = Ticket.Field.TYPE.get();
		
		if (ticketObject.containsKey(typeKey)) {
			return (String) ticketObject.get(typeKey);
		} else {
			return "";
		}
	}
	
	public static String getTicketSubject(JSONObject ticketObject) {
		String subjectKey = Ticket.Field.SUBJECT.get();
		
		if (ticketObject.containsKey(subjectKey)) {
			return String.valueOf(ticketObject.get(subjectKey));
		} else {
			return "";
		}
	}
	
	public static String getTicketCreatedAt(JSONObject ticketObject) {
		String createdAtKey = Ticket.Field.CREATED_AT.get();
		
		if (ticketObject.containsKey(createdAtKey)) {
			return (String) ticketObject.get(createdAtKey);
		} else {
			return "";
		}
	}
	
	public static String getTicketId(JSONObject ticketObject) {
		String idKey = Ticket.Field.ID.get();
		
		if (ticketObject.containsKey(idKey)) {
			return String.valueOf(ticketObject.get(idKey));
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
	
	public static String getTicketAssigneeId(JSONObject ticketObject) {
		String assigneeIdKey = Ticket.Field.ASSIGNEE_ID.get();
		
		if (ticketObject.containsKey(assigneeIdKey)) {
			return String.valueOf(ticketObject.get(assigneeIdKey));
		} else {
			return "";
		}
	}
}
