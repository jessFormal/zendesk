package au.zendesk.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Model for Zendesk Ticket 
 */
public class Ticket {
	// Stores the data of Ticket
	private Map<String, String> values;
	// Set of tags associated to Ticket
	private Set<String> tags;
	
	// Assumption: values cannot be "-1" so "-1" has been designated as empty value
	public static final String EMPTY_VALUE = "-1";
	public static final String FORMAT = "%-15s %s %n";
	
	/*
	 * To hold a list of fields associated to Ticket.
	 */
	public enum Field {
		ID("_id", true),
		TYPE("type", true),
		CREATED_AT("created_at", true),
		ASSIGNEE_ID("assignee_id", true),
		SUBJECT("subject", true),
		TAGS("tags", true),
		ASSIGNEE_NAME("assignee_name", false);
		
		// Key to be used to search Ticket Map
		private final String key;
		// Can this field be searched
		private final boolean isSearchable;
		
		private Field(String key, boolean searchable) {
			this.key = key;
			this.isSearchable = searchable;
		}
		
		public String get() {
			return key;
		}
		
		public boolean isSearchable() {
			return isSearchable;
		}
	}
	
	public Ticket(String id, String type, String createdAt, 
			String assigneeId, String subject, Set<String> tags) {
		values = new HashMap<>();
		values.put(Field.ID.get(), id);
		values.put(Field.TYPE.get(), type);
		values.put(Field.CREATED_AT.get(), createdAt);
		values.put(Field.ASSIGNEE_ID.get(), assigneeId);
		values.put(Field.SUBJECT.get(), subject);
		this.tags = tags;
	}
	
	public String getId() {
		return getValue(Ticket.Field.ID.get());
	}
	
	public String getType() {
		return getValue(Ticket.Field.TYPE.get());
	}
	
	public String getCreatedAt() {
		return getValue(Ticket.Field.CREATED_AT.get());
	}
	
	public String getAssigneeId() {
		String res = getValue(Ticket.Field.ASSIGNEE_ID.get());
		return res.isEmpty() ? Ticket.EMPTY_VALUE : res;
	}
	
	public String getSubject() {
		return getValue(Ticket.Field.SUBJECT.get());
	}

	public String getValue(String key) {
		return values.get(key);
	}
	
	public boolean containTag(String value) {
		if (value.isEmpty()) {
			return tags.isEmpty();
		} else {
			return tags.contains(value);
		}
	}
	
	// Human readable tag output
	public String getPrintableTag() {
		return String.join(",", tags);
	}
	
	public Set<String> getTags() {
		return tags;
	}
	
	// Get the list of searchable fields for Tickets
	public static List<String> getFields() {
		List<String> result = new ArrayList<>();
		Field[] fields = Field.values();
		
		for(int i=0; i < fields.length; i++) {
			Field f = fields[i];
			if (f.isSearchable()) {
				result.add(fields[i].get());
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		return String.format(FORMAT
				+ FORMAT
				+ FORMAT
				+ FORMAT
				+ FORMAT
				+ "%-15s [%s] %n",
				Field.ID.get(),
				values.get(Field.ID.get()),
				Field.CREATED_AT.get(),
				values.get(Field.CREATED_AT.get()),
				Field.TYPE.get(),
				values.get(Field.TYPE.get()),
				Field.SUBJECT.get(),
				values.get(Field.SUBJECT.get()),
				Field.ASSIGNEE_ID.get(),
				values.get(Field.ASSIGNEE_ID.get()),
				Field.TAGS.get(),
				getPrintableTag());
	}
}
