package au.zendesk.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class TicketUtilsTest {
	public ZendeskSystem zendeskSystem;
	
	@Before
	public void setup() {
		zendeskSystem = new ZendeskSystem();
		User u = new User("1", "John Smith", 
				"true", "2016-07-28T05:29:25-10:00");
		User u2 = new User("2", "Elon Moo", 
				"false", "2016-06-23T10:31:39-10:00");
		List<User> newUsers = Arrays.asList(u,u2);
		zendeskSystem.setUsers(newUsers);
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject createTicketObject(Long id, String subject, String createdAt, 
			String type, Long assigneeId, String[] tags) {
		JSONArray tagsArr = new JSONArray();
		JSONObject details = new JSONObject();
		
		if (tags != null) {
			for(String tag : tags) {
				tagsArr.add(tag);
				details.put(Ticket.Field.TAGS.get(), tagsArr);
			}
		}
		if (id != null) {
			details.put(Ticket.Field.ID.get(), id);
		}
		if (subject != null) {
			details.put(Ticket.Field.SUBJECT.get(), subject);
		}
		if (createdAt != null) {
			details.put(Ticket.Field.CREATED_AT.get(), 
				createdAt);
		}
		if (type != null) {
			details.put(Ticket.Field.TYPE.get(), type);
		}
		if (assigneeId != null) {
			details.put(Ticket.Field.ASSIGNEE_ID.get(), assigneeId);
		}
		return details;
	}
	
	@Test
	public void createTicket() {
		Long expectedId = 100L;
		String expectedSubject = "Congrats to Woop Woop";
		String expectedCreatedAt = "2016-04-28T11:19:34-10:00";
		String expectedType = "incident";
		Long expectedAssigneeId = 5L;
		String[] tags = new String[] {"Ohio", "Down Under"};
		Set<String> expectedTags = new HashSet<>();
		expectedTags.add("Ohio");
		expectedTags.add("Down Under");
		
		JSONObject obj = createTicketObject(expectedId, expectedSubject, expectedCreatedAt,
				expectedType, expectedAssigneeId, tags);
		Ticket ticket = TicketUtils.createTicket(obj);
		
		assertEquals(String.valueOf(expectedId), ticket.getId());
		assertEquals(expectedSubject, ticket.getSubject());
		assertEquals(expectedCreatedAt, ticket.getCreatedAt());
		assertEquals(expectedType, ticket.getType());
		assertEquals(String.valueOf(expectedAssigneeId), ticket.getAssigneeId());
		assertEquals(expectedTags, ticket.getTags());
	}
	
	@Test
	public void getValue() {
		String expected = "incident";
		
		JSONObject ticketObject = createTicketObject(null, null, null, 
				expected, null, null);
		String actual = TicketUtils.getValue(ticketObject, Ticket.Field.TYPE.get());
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getValue_MissingField() {
		String type = null;
		
		JSONObject ticketObject = createTicketObject(null, null, null, 
				type, null, null);
		String actual = TicketUtils.getValue(ticketObject, Ticket.Field.TYPE.get());
		
		assertTrue(actual.isEmpty());
	}
	
	@Test
	public void getValue_NonUTF8() {
		String expected = "Vergnügen";
		
		JSONObject ticketObject = createTicketObject(null, expected, null, 
				null, null, null);
		String actual = TicketUtils.getValue(ticketObject, Ticket.Field.SUBJECT.get());
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getTicketTag() {
		String[] tags = new String[] {"Ohio", "Pennsylvania", "American Samoa"};
		Set<String> expected = new HashSet<String>(Arrays.asList(tags));
		
		JSONObject ticketObject = createTicketObject(null, null, null, 
				null, null, tags);
		Set<String> actual = TicketUtils.getTicketTag(ticketObject);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getTicketTag_MissingField() {
		String[] tags = null;
		
		JSONObject ticketObject = createTicketObject(null, null, null, 
				null, null, tags);
		Set<String> actual = TicketUtils.getTicketTag(ticketObject);
		
		assertTrue(actual.isEmpty());
	}
}
