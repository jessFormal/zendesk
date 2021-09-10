package au.zendesk.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import au.zendesk.test.User.Field;

public class ZendeskSystemTest {
	public List<User> users;
	public User u1;
	public User u2;
	public User u3;
	public User u4;
	
	public List<Ticket> tickets;
	public Ticket t1;
	public Ticket t2;
	public Ticket t3;
	
	public ZendeskSystem zendeskSystem;
	
	@Before
	public void setUp() {
		users = new ArrayList<User>();
		u1 = new User(
				"",
				"John Smith",
				"true",
				"2016-06-23T10:31:39-10:00");
		u2 = new User(
				"1",
				"",
				"false",
				"2016-02-09T07:52:10-11:00");
		u3 = new User(
				"2",
				"Apple Bee",
				"",
				"2016-04-04T01:30:49-10:00");
		u4 = new User(
				"3",
				"Keanu Reeves",
				"true",
				"");
		
		users = Arrays.asList(u1, u2, u3, u4);
		
		t1 = new Ticket("436bf9b0-1147-4c0a-8439-6f79833bff5b",
				"incident", 
				"2016-04-28T11:19:34-10:00",
				"2", 
				"A Catastrophe in Korea (North)", 
				new HashSet<String>(Arrays.asList("Ohio", "Mass")));
		t2 = new Ticket("436bf9b0-1147-4c0a-8439-6f79833cff5b",
				"incident", 
				"",
				"1", 
				"A Catastrophe in Australia", 
				new HashSet<String>(Arrays.asList("Ohio", "Mass")));
		t3 = new Ticket("436bf9b0-1147-430a-8439-6f89833asf33",
				"problem", 
				"2016-04-28T11:19:35-10:00",
				"1", 
				"", 
				new HashSet<String>(Arrays.asList("New York", "Main")));
		
		tickets = Arrays.asList(t1, t2, t3);
		
		zendeskSystem = new ZendeskSystem();
		zendeskSystem.setUsers(users);
		zendeskSystem.setTickets(tickets);
		Collections.sort(users, new SortByUserId());
		Collections.sort(tickets, new SortByTicketAssigneeId());
	}

	@Test
	public void searchUsers() {
		List<String> actual = zendeskSystem.searchUsers(User.Field.ID.get(), "2");
		List<String> expected = Arrays.asList(String.format(
				User.FORMAT
				+ User.FORMAT
				+ User.FORMAT
				+ User.FORMAT
				+ "%-15s [%s] %n",
				Field.ID.get(),
				u3.getId(),
				Field.NAME.get(),
				u3.getName(),
				Field.VERIFIED.get(),
				u3.getVerified(),
				Field.CREATED_AT.get(),
				u3.getCreatedAt(),
				Field.TICKETS.get(),
				t1.getSubject()));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void searchUsers_EmptyResult() {
		List<String> actual = zendeskSystem.searchUsers(User.Field.ID.get(), "1432");
		
		assertTrue(actual.isEmpty());
	}
	
	@Test
	public void getTicketsWithAssigneeId() {
		List<String> actual = zendeskSystem.getTicketsWithAssigneeId("2");
		List<String> expected = Arrays.asList("A Catastrophe in Korea (North)");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getTicketsWithAssigneeId_EmptyAssigneeId() {
		List<String> actual = zendeskSystem.getTicketsWithAssigneeId("-1");
		
		assertTrue(actual.isEmpty());
	}
	
	@Test
	public void getTicketsWithAssigneeId_EmptySubject() {
		List<String> actual = zendeskSystem.getTicketsWithAssigneeId("1");
		List<String> expected = Arrays.asList("A Catastrophe in Australia");
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void searchTickets() {
		List<String> actual = zendeskSystem.searchTickets(
				Ticket.Field.ID.get(), "436bf9b0-1147-4c0a-8439-6f79833cff5b");
		List<String> expected = Arrays.asList(String.format(
				Ticket.FORMAT
				+ Ticket.FORMAT
				+ Ticket.FORMAT
				+ Ticket.FORMAT
				+ Ticket.FORMAT
				+ "%-15s [%s] %n"
				+ Ticket.FORMAT,
				Ticket.Field.ID.get(),
				t2.getId(),
				Ticket.Field.CREATED_AT.get(),
				t2.getCreatedAt(),
				Ticket.Field.TYPE.get(),
				t2.getType(),
				Ticket.Field.SUBJECT.get(),
				t2.getSubject(),
				Ticket.Field.ASSIGNEE_ID.get(),
				t2.getAssigneeId(),
				Ticket.Field.TAGS.get(),
				t2.getPrintableTag(),
				Ticket.Field.ASSIGNEE_NAME.get(),
				u2.getName()));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void searchTickets_Tag() {
		List<String> actual = zendeskSystem.searchTickets(
				Ticket.Field.TAGS.get(), "Main");
		List<String> expected = Arrays.asList(String.format(
				Ticket.FORMAT
				+ Ticket.FORMAT
				+ Ticket.FORMAT
				+ Ticket.FORMAT
				+ Ticket.FORMAT
				+ "%-15s [%s] %n"
				+ Ticket.FORMAT,
				Ticket.Field.ID.get(),
				t3.getId(),
				Ticket.Field.CREATED_AT.get(),
				t3.getCreatedAt(),
				Ticket.Field.TYPE.get(),
				t3.getType(),
				Ticket.Field.SUBJECT.get(),
				t3.getSubject(),
				Ticket.Field.ASSIGNEE_ID.get(),
				t3.getAssigneeId(),
				Ticket.Field.TAGS.get(),
				t3.getPrintableTag(),
				Ticket.Field.ASSIGNEE_NAME.get(),
				u2.getName()));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void searchTickets_EmptyResult() {
		List<String> actual = zendeskSystem.searchTickets(
				Ticket.Field.TAGS.get(), "Nothing");
				
		assertTrue(actual.isEmpty());
	}
	
	@Test
	public void getUserSearchableFields() {
		List<String> actual = zendeskSystem.getUserSearchableFields();
		List<String> expected = Arrays.asList(
				User.Field.ID.get(),
				User.Field.NAME.get(),
				User.Field.VERIFIED.get(),
				User.Field.CREATED_AT.get());

		assertEquals(expected,actual);
	}

	@Test
	public void getTicketSearchableFields() {
		List<String> actual = zendeskSystem.getTicketSearchableFields();
		List<String> expected = Arrays.asList(
				Ticket.Field.ID.get(),
				Ticket.Field.TYPE.get(),
				Ticket.Field.CREATED_AT.get(),
				Ticket.Field.ASSIGNEE_ID.get(),
				Ticket.Field.SUBJECT.get(),
				Ticket.Field.TAGS.get());

		assertEquals(expected,actual);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void createUserOrTicket_CreateUser() {
		zendeskSystem = new ZendeskSystem();
		JSONArray array = new JSONArray();
		JSONObject userObj = UserUtilsTest.createJSONObject(
				1L, 
				true, 
				"2016-04-14T08:32:31-10:00", 
				"John Smith");
		array.add(userObj);
		
		zendeskSystem.createUserOrTicket(array, ZendeskSystem.TYPE_USER);
		List<User> actual = zendeskSystem.getUsers();
		
		List<User> expected = new ArrayList<>();
		expected.add(new User("1", "John Smith", "true", 
				"2016-04-14T08:32:31-10:00"));
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void createUserOrTicket_CreateTicket() {
		zendeskSystem = new ZendeskSystem();
		JSONArray array = new JSONArray();
		JSONObject tickObj = TicketUtilsTest.createTicketObject(
				100L, 
				"Party in Africa", 
				"2016-04-14T08:32:31-10:00",
				"incident", 
				5L, 
				null);
		array.add(tickObj);
		
		zendeskSystem.createUserOrTicket(array, ZendeskSystem.TYPE_TICKET);
		List<Ticket> actual = zendeskSystem.getTickets();
		
		List<Ticket> expected = new ArrayList<>();
		expected.add(new Ticket("100", "incident", "2016-04-14T08:32:31-10:00", 
				"5", "Party in Africa",new HashSet<>()));
		
		assertEquals(expected.toString(), actual.toString());
	}
}
