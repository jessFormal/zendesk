package au.zendesk.test;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import au.zendesk.test.Ticket.Field;

public class TicketTest {
	
	private Ticket ticket;
	
	@Before
	public void setUp() {
		String id = "1";
		String type = "incident";
		String createdAt = "2016-04-14T08:32:31-10:00";
		String assigneeId = "10";
		String subject = "A Problem in Morocco";
		Set<String> tags = new HashSet<>();
		tags.add("Massachusetts");
		tags.add("New York");
		tags.add("Minnesota");
		ticket = new Ticket(id, type, createdAt, 
				assigneeId, subject, tags);
	}
	
	@Test
	public void toStringFormat() {
		String actual = ticket.toString();
		String expected = String.format(
				Ticket.FORMAT
				+ Ticket.FORMAT
				+ Ticket.FORMAT
				+ Ticket.FORMAT
				+ Ticket.FORMAT
				+ "%-15s [%s] %n",
				Ticket.Field.ID.get(),
				ticket.getValue(Field.ID.get()),
				Ticket.Field.CREATED_AT.get(),
				ticket.getValue(Field.CREATED_AT.get()),
				Ticket.Field.TYPE.get(),
				ticket.getValue(Field.TYPE.get()),
				Ticket.Field.SUBJECT.get(),
				ticket.getValue(Field.SUBJECT.get()),
				Ticket.Field.ASSIGNEE_ID.get(),
				ticket.getValue(Field.ASSIGNEE_ID.get()),
				Ticket.Field.TAGS.get(),
				ticket.getPrintableTag());
		
		assertEquals(expected, actual);
	}
	
}
