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
	public void getUserId() {
		Long id = 1L;
		String expected = id.toString();
		
		JSONObject userObject = createUserObject(null, null, null , id);
		String actual = UserUtils.getUserId(userObject);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getUserId_MissingField() {
		Long id = null;
		
		JSONObject userObject = createUserObject(null, null, null , id);
		String actual = UserUtils.getUserId(userObject);
		
		assertTrue(actual.isEmpty());
	}
	
	@Test
	public void getUserCreatedAt() {
		String expected = "2016-04-15T05:19:46-10:00";
		
		JSONObject userObject = createUserObject(null, null, expected, null);
		String actual = UserUtils.getUserCreatedAt(userObject);
		
		assertEquals(expected, actual);
	}

	@Test
	public void getUserCreatedAt_MissingField() {
		String createdAt = null;
		
		JSONObject userObject = createUserObject(null, null, createdAt, null);
		String actual = UserUtils.getUserCreatedAt(userObject);
		
		assertTrue(actual.isEmpty());
	}
	
	@Test
	public void getUserVerifed() {
		Boolean verified = true;
		String expected = verified.toString();
		
		JSONObject userObject = createUserObject(null, verified, null, null);
		String actual = UserUtils.getUserVerified(userObject);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getUserVerifed_MissingField() {
		Boolean verified = null;
		
		JSONObject userObject = createUserObject(null, verified, null, null);
		String actual = UserUtils.getUserVerified(userObject);
		
		assertTrue(actual.isEmpty());
	}
	
	@Test
	public void getUserName() {
		String expected = "John Smith";
		
		JSONObject userObject = createUserObject(expected, null, null, null);
		String actual = UserUtils.getUserName(userObject);
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void getUserName_MissingField() {
		String name = null;
		
		JSONObject userObject = createUserObject(name, null, null, null);
		String actual = UserUtils.getUserName(userObject);
		
		assertTrue(actual.isEmpty());
	}
}
