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
		String name = getUserName(userObject);
		String verified = getUserVerified(userObject);
		String createdAt = getUserCreatedAt(userObject);
		String id = getUserId(userObject);
		
		return new User(id,name,verified,createdAt);
	}

	public static String getUserId(JSONObject userObject) {
		String idKey = User.Field.ID.get();
		
		if ( userObject.containsKey(idKey)) {
			return String.valueOf(userObject.get(idKey));
		} else {
			return "";
		}
	}

	public static String getUserCreatedAt(JSONObject userObject) {
		String createdAtKey = User.Field.CREATED_AT.get();
		
		if ( userObject.containsKey(createdAtKey)) {
			return (String) userObject.get(createdAtKey);
		} else {
			return "";
		}
	}

	public static String getUserVerified(JSONObject userObject) {
		String verifiedKey = User.Field.VERIFIED.get();
		
		if ( userObject.containsKey(verifiedKey)) {
			return String.valueOf(userObject.get(verifiedKey));
		} else {
			return "";
		}
	}

	public static String getUserName(JSONObject userObject) {
		String nameKey = User.Field.NAME.get();
		
		if ( userObject.containsKey(nameKey)) {
			 return (String) userObject.get(nameKey);
		} else {
			return "";
		}
	}
}
