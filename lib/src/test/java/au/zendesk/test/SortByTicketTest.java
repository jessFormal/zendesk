package au.zendesk.test;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class SortByTicketTest {
	public List<Ticket> tickets;
	public Ticket t1;
	public Ticket t2;
	public Ticket t3;
	public Ticket t4;
	
	@Before
	public void setUp() {
		t1 = new Ticket("436bf9b0-1147-4c0a-8439-6f79833bff5b",
				"incident", 
				"2016-04-28T11:19:34-10:00",
				"10", 
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
				"3", 
				"", 
				new HashSet<String>(Arrays.asList("New York", "Main")));
		t4 = new Ticket("436bf920-1147-430a-8439-6f89833asf33",
				"problem", 
				"2016-04-28T11:19:35-10:00",
				"", 
				"", 
				new HashSet<String>(Arrays.asList("New York", "Main")));
		tickets = Arrays.asList(t1,t2,t3,t4);
	}
	
	@Test
	public void sortByTicketAssigneeId() {
		Collections.sort(tickets, new SortByTicketAssigneeId());
		List<Ticket> expected = Arrays.asList(t4,t2,t3,t1);
		
		assertEquals(expected,tickets);
	}

}
