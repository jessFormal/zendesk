package au.zendesk.test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Logic for main features of application is defined here.
 * Manages the list of Users and Tickets, returns information to InteractiveWindow to display 
 * and creates and retrieves Models.
 */
public class ZendeskSystem {
	
	private List<User> users;
	private List<Ticket> tickets;
	
	// Used to distinguish Ticket Object to be made
	public static final String TYPE_TICKET = "2";
	
	// Used to distinguish User Object to be made
	public static final String TYPE_USER = "1";
	
	// Used to distinguish an empty value.
	// The Library CLI tool does not allow empty value as input so this
	// a workaround.
	public static final String EMPTY_STRING = "-1";
	
	public ZendeskSystem(String userFile, String ticketsFile) {
		users = new ArrayList<>();
		tickets = new ArrayList<>();
		parseFile(userFile, ZendeskSystem.TYPE_USER);
		parseFile(ticketsFile, ZendeskSystem.TYPE_TICKET);
	}

	// Constructor used for testing
	public ZendeskSystem() {
		users = new ArrayList<>();
		tickets = new ArrayList<>();
	}
	
	public List<String> getUserSearchableFields() {
		return User.getFields();
	}
	
	public List<String> getTicketSearchableFields() {
		return Ticket.getFields();
	}
	
	public void quitApplication() {
		System.exit(0);
	}
	
	public List<String> searchUsers(String field, String value) {
		List<String> results = new ArrayList<>();
		if(! value.isEmpty() ) {
			value = ZendeskSystemUtils.encodeUTF8(value);
		}
		if (isUserFieldValid(field)) {
			List<User> userResult = filterUsers(field, value);
			// append ticket subject information for each user
			for (User user : userResult) {
				List<String> ticketSubjects = getTicketsWithAssigneeId(
						user.getId());
				String subject = String.format("%-15s [%s] %n", 
						User.Field.TICKETS.get(), String.join(",", ticketSubjects));
				results.add(user + subject);
			}
		}
		return results;
	}
	
	public boolean isUserFieldValid(String field) {
		return User.getFields().contains(field);
	}
	
	public List<User> filterUsers(String field, String value) {
		return UserPredicate.filter(
				users, UserPredicate.search(field, value));
	}
	
	// Find all ticket subjects assigned to a user
	public List<String> getTicketsWithAssigneeId(String assigneeId) {
		int index =  ZendeskSystemUtils.binarySearchModified(tickets,assigneeId);
		List<String> ticketSubjects = new ArrayList<>();
		
		if (index != Integer.parseInt(Ticket.EMPTY_VALUE)) {
			for ( int i = index; i < tickets.size(); i++) {
				Ticket currTicket = tickets.get(i);
				if (currTicket.getAssigneeId().equals(assigneeId)) {
					// Add subject to list if it exists
					if (! currTicket.getSubject().isEmpty()) {
						ticketSubjects.add(currTicket.getSubject());
					}
				} else {
					break;
				}
			}
		}
		return ticketSubjects;
	}
	
	public List<String> searchTickets(String field, String value) {
		List<String> result = new ArrayList<>();
		if(! value.isEmpty() ) {
			value = ZendeskSystemUtils.encodeUTF8(value);
		}
		
		if (isTicketFieldValid(field)) {
			List<Ticket> ticketResult = filterTickets(field, value);
			
			// Append Assignee Name to Ticket Results
			for(Ticket ticket : ticketResult) {
				List<User> userResult = filterUsers(User.Field.ID.get(), 
						ticket.getAssigneeId());
				String name = "";
				if (! userResult.isEmpty()) {
					name = userResult.get(0).getName();
				}
				String nameVal = String.format(Ticket.FORMAT, 
						Ticket.Field.ASSIGNEE_NAME.get(), name);
				result.add(ticket + nameVal);
			}
		}
		return result;
	}
	
	public boolean isTicketFieldValid(String field) {
		return Ticket.getFields().contains(field);
	}
	
	public List<Ticket> filterTickets(String field, String value) {
		List<Ticket> ticketResult = null;
		
		if (field.equals(Ticket.Field.TAGS.get())) {
			ticketResult = TicketPredicate.filter(tickets, 
					TicketPredicate.searchTags(value));
		} else {
			ticketResult = TicketPredicate.filter(tickets, 
					TicketPredicate.search(field, value));
		}
		return ticketResult;
	}
	
	public void parseFile(String file, String type) {
		JSONParser jsonParser = new JSONParser();
		
		try (Reader reader = new FileReader(file)) {
			JSONArray array = (JSONArray) jsonParser.parse(reader);
			createUserOrTicket(array, type);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		sortList(type);
	}
	
	public void createUserOrTicket(JSONArray array, String type) {
		for (Object obj : array) {
			JSONObject object = (JSONObject) obj;
			if (type.equals(ZendeskSystem.TYPE_TICKET)) {
				tickets.add(TicketUtils.createTicket(object));
			} else if(type.equals(ZendeskSystem.TYPE_USER)) {
				users.add(UserUtils.createUser(object));
			} else {
				System.out.println("Error: Unknown Type: "+object);
			}
		}
	}
	
	public void sortList(String type) {
		if (type.equals(ZendeskSystem.TYPE_TICKET)) {
			Collections.sort(tickets, new SortByTicketAssigneeId());
		} else if (type.equals(ZendeskSystem.TYPE_USER)) {
			Collections.sort(users, new SortByUserId());
		} else {
			System.out.println("Error: Unknown Type Will Not Sort.");
		}
	}
	
	public void setUsers(List<User> newUsers) {
		users = newUsers;
	}
	
	public List<User> getUsers() {
		return users;
	}
	
	public void setTickets(List<Ticket> newTickets) {
		tickets = newTickets;
	}
	
	public List<Ticket> getTickets() {
		return tickets;
	}
}
