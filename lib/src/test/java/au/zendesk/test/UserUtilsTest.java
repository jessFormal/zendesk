package au.zendesk.test;

import static org.junit.Assert.*;

import org.json.simple.JSONObject;
import org.junit.Test;

public class UserUtilsTest {
	
	@SuppressWarnings("unchecked")
	public JSONObject createUserObject(String name, Boolean verified, 
			String createdAt, Long id) {
		JSONObject details = new JSONObject();
		
		if (id != null) {
			details.put(User.Field.ID.get(), id);
		}
		if (name != null) {
			details.put(User.Field.NAME.get(), name);
		}
		if (verified != null) {
			details.put(User.Field.VERIFIED.get(), verified);
		}
		if (createdAt != null) {
			details.put(User.Field.CREATED_AT.get(), 
				createdAt);
		}
		return details;
	}
	
	@Test
	public void createUser() {
		Long expectedId = 1L;
		boolean expectedVerified = true;
		String expectedCreatedAt = "2016-04-14T08:32:31-10:00";
		String expectedName = "Harry Potter";
		
		JSONObject obj = createJSONObject(expectedId, expectedVerified, 
				expectedCreatedAt, expectedName);
		User u = UserUtils.createUser(obj);
		
		assertEquals(String.valueOf(expectedId), u.getId());
		assertEquals(expectedName, u.getName());
		assertEquals(String.valueOf(expectedVerified), u.getVerified());
		assertEquals(expectedCreatedAt, u.getCreatedAt());
	}
	
	@SuppressWarnings("unchecked")
	public static JSONObject createJSONObject(Long id, boolean verified, 
			String createdAt, String name) {
		JSONObject obj = new JSONObject();
		obj.put(User.Field.ID.get(), id);
		obj.put(User.Field.NAME.get(), name);
		obj.put(User.Field.CREATED_AT.get(), createdAt );
		obj.put(User.Field.VERIFIED.get(), verified);
		
		return obj;
	}

	@Test
	public void getValue() {
		Long id = 1L;
		String expected = id.toString();
		
		JSONObject userObject = createUserObject(null, null, null , id);
		String actual = UserUtils.getValue(userObject, User.Field.ID.get());
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getValue_MissingField() {
		Long id = null;
		
		JSONObject userObject = createUserObject(null, null, null , id);
		String actual = UserUtils.getValue(userObject, User.Field.ID.get());
		
		assertTrue(actual.isEmpty());
	}
	
	@Test
	public void getValue_NonUTF8() {
		String expected = "Vergnügen";
		
		JSONObject obj = createUserObject(expected, null, null, null);
		String actual = TicketUtils.getValue(obj, User.Field.NAME.get());
		
		assertEquals(expected, actual);
	}
}
