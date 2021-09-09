package au.zendesk.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class TicketPredicateTest {
	public List<Ticket> tickets;
	public Ticket t1;
	public Ticket t2;
	public Ticket t3;
	public Ticket t4;
	public Ticket t5;
	public Ticket t6;
	
	@Before
	public void setUp() {
		t1 = new Ticket("436bf9b0-1147-4c0a-8439-6f79833bff5b",
				"incident", 
				"2016-04-28T11:19:34-10:00",
				"", 
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
		t4 = new Ticket("436bf9b0-1147-4c0a-8439-asdf79833asf33",
				"problem", 
				"2016-04-28T11:19:35-10:00",
				"1", 
				"A Catastrophe in Brisbane", 
				new HashSet<String>());
		t5 = new Ticket("436bf9b0-1147-4c0a-8439-6f7fd833asf33",
				"", 
				"2016-04-28T11:19:35-10:00",
				"1", 
				"A Catastrophe in Brisbane", 
				new HashSet<String>(Arrays.asList("Victoria","Smallsville","Amsterdam")));
		t6 = new Ticket("",
				"problem", 
				"2016-04-28T11:19:35-10:00",
				"1", 
				"A Catastrophe in Brisbane", 
				new HashSet<String>(Arrays.asList("Victoria","Smallsville","Amsterdam")));
		
		tickets = Arrays.asList(t1, t2, t3, t4,	t5, t6);
	}
	
	@Test
	public void filterTickets_EmptyList() {
		String key = User.Field.ID.get();
		String value = "7";
		
		List<Ticket> actual = TicketPredicate.filter(tickets, 
				TicketPredicate.search(key, value));
		
		assertTrue(actual.isEmpty());
	}

	@Test
	public void filterTickets_Id() {
		String key = Ticket.Field.ID.get();
		String value = "436bf9b0-1147-4c0a-8439-asdf79833asf33";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.search(key, value));
		
		List<Ticket> expected = Arrays.asList(t4);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterTickets_Id_Empty() {
		String key = Ticket.Field.ID.get();
		String value = "";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.search(key, value));
		
		List<Ticket> expected = Arrays.asList(t6);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterTickets_CreatedAt() {
		String key = Ticket.Field.CREATED_AT.get();
		String value = "2016-04-28T11:19:34-10:00";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.search(key, value));
		
		List<Ticket> expected = Arrays.asList(t1);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterTickets_CreatedAt_Empty() {
		String key = Ticket.Field.CREATED_AT.get();
		String value = "";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.search(key, value));
		
		List<Ticket> expected = Arrays.asList(t2);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterTickets_Type() {
		String key = Ticket.Field.TYPE.get();
		String value = "incident";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.search(key, value));
		
		List<Ticket> expected = Arrays.asList(t1, t2);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterTickets_Type_Empty() {
		String key = Ticket.Field.TYPE.get();
		String value = "";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.search(key, value));
		
		List<Ticket> expected = Arrays.asList(t5);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterTickets_Tags() {
		String value = "Ohio";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.searchTags(value));
		
		List<Ticket> expected = Arrays.asList(t1, t2);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterTickets_Tags_Empty() {
		String value = "";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.searchTags(value));
		
		List<Ticket> expected = Arrays.asList(t4);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterTickets_AssigneeId() {
		String key = Ticket.Field.ASSIGNEE_ID.get();
		String value = "1";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.search(key, value));
		
		List<Ticket> expected = Arrays.asList(t2, t3, t4, t5, t6);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterTickets_AssigneeId_Empty() {
		String key = Ticket.Field.ASSIGNEE_ID.get();
		String value = "";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.search(key, value));
		
		List<Ticket> expected = Arrays.asList(t1);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterTickets_Subject() {
		String key = Ticket.Field.SUBJECT.get();
		String value = "A Catastrophe in Australia";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.search(key, value));
		
		List<Ticket> expected = Arrays.asList(t2);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterTickets_Subject_Empty() {
		String key = Ticket.Field.SUBJECT.get();
		String value = "";
		
		List<Ticket> actual = TicketPredicate.filter(
				tickets, TicketPredicate.search(key, value));
		
		List<Ticket> expected = Arrays.asList(t3);
		
		assertEquals(expected.toString(), actual.toString());
	}

}
