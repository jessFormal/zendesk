package au.zendesk.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import au.zendesk.test.User.Field;

public class InteractiveWindowTest {
	private ZendeskSystem zendeskSystem;
	private List<User> users;
	private List<Ticket> tickets;
	private User u1;
	private Ticket t1;
	private InteractiveWindow view;
	
	@Before
	public void setUp() {
		users = new ArrayList<User>();
		u1 = new User(
				"2",
				"John Smith",
				"true",
				"2016-06-23T10:31:39-10:00");
		
		users = Arrays.asList(u1);
		
		t1 = new Ticket("436bf9b0-1147-4c0a-8439-6f79833bff5b",
				"incident", 
				"2016-04-28T11:19:34-10:00",
				"2", 
				"A Catastrophe in Korea (North)", 
				new HashSet<String>(Arrays.asList("Ohio", "Mass")));
		
		tickets = Arrays.asList(t1);
		
		zendeskSystem = new ZendeskSystem();
		zendeskSystem.setUsers(users);
		zendeskSystem.setTickets(tickets);
		zendeskSystem.sortList(ZendeskSystem.TYPE_USER);
		zendeskSystem.sortList(ZendeskSystem.TYPE_TICKET);
		view = new InteractiveWindow(zendeskSystem);
	}
	@Test
	public void searchTicketOrUser() {
		List<String> actual = view.searchTicketOrUser(
				ZendeskSystem.TYPE_USER, User.Field.ID.get(), "2");
		List<String> expected = Arrays.asList(String.format(
				User.FORMAT
				+ User.FORMAT
				+ User.FORMAT
				+ User.FORMAT
				+ "%-15s [%s] %n",
				Field.ID.get(),
				u1.getId(),
				Field.NAME.get(),
				u1.getName(),
				Field.VERIFIED.get(),
				u1.getVerified(),
				Field.CREATED_AT.get(),
				u1.getCreatedAt(),
				Field.TICKETS.get(),
				t1.getSubject()));
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void searchTicketOrUser_Empty() {
		List<String> actual = view.searchTicketOrUser(
				ZendeskSystem.TYPE_USER, User.Field.ID.get(), "4");
		
		assertTrue(actual.isEmpty());
	}
	
	@Test
	public void iterativeOverFields() {
		List<String> list = Arrays.asList("tag1", "tag2");
		String actual = view.iterateOverFields(list);
		String expected = "tag1\ntag2\n";
		
		assertEquals(expected, actual);
	}
}
