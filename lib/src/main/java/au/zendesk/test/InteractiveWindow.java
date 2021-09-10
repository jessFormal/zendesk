package au.zendesk.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

/*
 * Interactive CLI - Controls the flow of interactions and what is displayed 
 * to the user
 */
public class InteractiveWindow{
	// Used to created InputStreamers. All input streamers created
	// from this instance share the same TextTerminal.
	private TextIO textIO;
	
	// Used to print to console
	@SuppressWarnings("rawtypes")
	private TextTerminal textTerminal;
	
	// False if application should continue to run
	private boolean quit;
	
	// Handles the list of tickets and users. Controls the logic in searching
	// and what to display to InteractiveWindow.
	private ZendeskSystem zendeskSystem;
	
	private static final String SEPARATOR_FORMAT = "---------------------------%n";
	
	// The console option to choose to enter search for either user or ticket
	private static final String SEARCH_OPTION = "1";
	
	// The console option to choose to list searchable fields for ticket or user
	private static final String LIST_FIELD_OPTION = "2";

	public static void main(String[] args) {
		if (args[0] == null) {
			System.err.println("Error: Arg0 Missing Users.json");
			System.exit(0);
		}
		if (args[1] == null) {
			System.err.println("Error: Arg1 Missing Tickets.json");
			System.exit(0);
		}
		String userFile = args[0];
		String ticketsFile = args[1];
		
		ZendeskSystem zendeskSystem = new ZendeskSystem(userFile,ticketsFile);
		InteractiveWindow view = new InteractiveWindow(zendeskSystem);
		
		view.startZendesk();
	}
	
	public InteractiveWindow(ZendeskSystem zendeskSystem) {
		this.zendeskSystem = zendeskSystem;
		textIO = TextIoFactory.getTextIO();
		textTerminal = textIO.getTextTerminal();
		quit = false;
	}
	
	// Main logic flow
	public void startZendesk() {
		while(! quit) {
			showMenuOptions();
			String type = selectSearchType();
			List<String> res = searchTermAndValue(type);
			printResult(res);
		}
	}
	
	public String getWelcomeMessage() {
		return String.format("Welcome to Zendesk Search%n"
				+ "Type \'quit\' to exit at any time, Press \'Enter\' to continue%n%n%n"
				+ "\t Select search options:%n"
				+ "\t  * Press 1 to search Zendesk%n"
				+ "\t  * Press 2 to view a list of searchable fields%n"
				+ "\t  * Type \'quit\' to exit%n%n");
	}
	
	// Get input with required values and at any time user types quit 
	// then exit the application
	public String waitForInput(List<String> possibleValues, String prompt) {
		String input = textIO.newStringInputReader().
				withPossibleValues(possibleValues).read(prompt);
		if (input.equals("quit")) {
			quit();
		}
		return input;
	}
	
	// Get input with default value set and at any time user types quit 
	// then exit the application
	public String waitForInputDefaultVal(String prompt, String defaultValue) {
		String input = textIO.newStringInputReader().
				withDefaultValue(defaultValue).read(prompt);
		if (input.equals("quit")) {
			quit();
		}
		return input;
	}
	
	// Exit application
	public void quit() {
		quit = true;
		textTerminal.printf("Goodbye from Zendesk Search%n");
		zendeskSystem.quitApplication();
	}
	
	// Shows options to either search zendesk or list searchable fields
	// or quit the application. If invalid option or list field option 
	// is provided will continue to show welcome message.
	public void showMenuOptions() {
		boolean nextStep = false;
		
		while (! nextStep) {
			String input = waitForInput(
			    Arrays.asList(SEARCH_OPTION, LIST_FIELD_OPTION, "quit"),
					getWelcomeMessage());
			if (input.equals(SEARCH_OPTION)) {
				nextStep = true;
			} else if(input.equals(LIST_FIELD_OPTION)) {
				printSearchableFields();
			}
		}
	}
	
	// Shows a list of searchable fields for user and ticket
	public void printSearchableFields() {
		textTerminal.printf(SEPARATOR_FORMAT);
		textTerminal.printf("Search Users with%n"
				+ "%s",iterateOverFields(zendeskSystem.getUserSearchableFields()));
		textTerminal.printf(SEPARATOR_FORMAT);
		textTerminal.printf("Search Tickets with%n"
				+ "%s%n",iterateOverFields(zendeskSystem.getTicketSearchableFields()));
	}
	
	// Returns a string with each field in a new line
	public String iterateOverFields(List<String> list) {
		String result = "";
		for (String s : list) {
			result += s + '\n';
		}
		return result;
	}
	
	// Shows an option to search user or ticket and returns the option
	public String selectSearchType() {
		String type = "";
		while (! type.equals(ZendeskSystem.TYPE_TICKET) 
				&& ! type.equals(ZendeskSystem.TYPE_USER)) {
			type = waitForInput(
			    Arrays.asList(ZendeskSystem.TYPE_USER, ZendeskSystem.TYPE_TICKET, "quit"),
					String.format("Select 1) Users or 2) Tickets%n"));
		}
		return type;
	}
	
	// Ask for Search Term and Value and then activate search
	public List<String> searchTermAndValue(String type) {
		String field = waitForInput(null, String.format("Enter Search Term %n"));
		String value = waitForInputDefaultVal(
				String.format("Enter Search Value %n"),ZendeskSystem.EMPTY_STRING);
		return searchTicketOrUser(type, field, value);
	}
	
	// Search Ticket or User with given field and value
	// return filtered results
	public List<String> searchTicketOrUser(String type, String field, String value) {
		List<String> res = new ArrayList<>();
		
		// Library Text IO CLI does not allow empty input therefore this
		// is a workaround to pass empty string to the search
		if (value.equals(ZendeskSystem.EMPTY_STRING)) {
			value = "";
		}
		if (type.equals(ZendeskSystem.TYPE_USER)) {
			textTerminal.printf("Searching users for %s with a value of %s .%n", 
					field, value);
			res = zendeskSystem.searchUsers(field, value);
		} else if (type.equals(ZendeskSystem.TYPE_TICKET)) {
			textTerminal.printf("Searching tickets for %s with a value of %s .%n",
					field, value);
			res = zendeskSystem.searchTickets(field, value);
		}
		return res;
	}
	
	// Print list of results in human readable format
	public void printResult(List<String> res) {
		if (! res.isEmpty() ) {
			for (String line : res) {
				textTerminal.printf("%s %n", line);
				textTerminal.printf(SEPARATOR_FORMAT);
			}
		} else {
			textTerminal.printf("No results found.%n%n");
		}
	}
}
