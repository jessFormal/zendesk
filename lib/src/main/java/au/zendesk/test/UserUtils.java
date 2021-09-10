package au.zendesk.test;

import org.json.simple.JSONObject;

/**
 * User Utility Class to help create User Objects from JSONObjects
 */
public class UserUtils {
	
	private UserUtils() {
	    throw new IllegalStateException("User Utility Helper Class");
	}
	
	public static User createUser(JSONObject userObject) {
		String name = getValue(userObject, User.Field.NAME.get());
		String verified = getValue(userObject, User.Field.VERIFIED.get());
		String createdAt = getValue(userObject, User.Field.CREATED_AT.get());
		String id = getValue(userObject, User.Field.ID.get());
		
		return new User(id, name, verified, createdAt);
	}
	
	public static String getValue(JSONObject userObject, String key) {
		if ( userObject.containsKey(key)) {
			return ZendeskSystemUtils.encodeUTF8(
					String.valueOf(userObject.get(key)));
		} else {
			return "";
		}
	}
}
