package au.zendesk.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model of Zendesk User
 */
public class User {
	
	// Stores data of a User
	private Map<String, String> values;
	
	// Assumption: values cannot be "-1" so "-1" has been designated as empty value
	public static final String EMPTY_VALUE = "-1";
	public static final String FORMAT = "%-15s %s %n";
	
	public enum Field {
		ID("_id", true),
		NAME("name", true),
		VERIFIED("verified", true),
		CREATED_AT("created_at", true),
		TICKETS("tickets", false);
		
		// Key to be used to for Map key
		private final String key;
		// is field searchable in the application
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
	
	public User(String id, String name, String verified, 
			String createdAt) {
		values = new HashMap<>();
		values.put(Field.ID.get(), id);
		values.put(Field.NAME.get(), name);
		values.put(Field.VERIFIED.get(), verified);
		values.put(Field.CREATED_AT.get(), createdAt);
	}
	
	public String getId() {
		return getValue(User.Field.ID.get());
	}
	
	public String getName() {
		return getValue(User.Field.NAME.get());
	}
	
	public String getVerified() {
		return getValue(User.Field.VERIFIED.get());
	}
	
	public String getCreatedAt() {
		return getValue(User.Field.CREATED_AT.get());
	}
	
	public String getValue(String key) {
		return values.get(key);
	}
	
	// Get list of searchable fields
	public static List<String> getFields() {
		List<String> result = new ArrayList<>();
		Field[] fields = Field.values();
		
		for (int i=0; i < fields.length; i++) {
			Field field = fields[i];
			if (field.isSearchable()) {
				result.add(field.get());
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		return String.format(
				FORMAT
				+ FORMAT
				+ FORMAT
				+ FORMAT, 
				Field.ID.get(),
				values.get(Field.ID.get()),
				Field.NAME.get(),
				values.get(Field.NAME.get()),
				Field.VERIFIED.get(),
				values.get(Field.VERIFIED.get()),
				Field.CREATED_AT.get(),
				values.get(Field.CREATED_AT.get()));
	}
}