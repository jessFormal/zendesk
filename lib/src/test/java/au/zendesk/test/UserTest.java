package au.zendesk.test;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {

	@Test
	public void User() {
		String expectedId = "1";
		String expectedName = "Joan Rivers";
		String expectedVerified = "true";
		String expectedCreatedAt = "2016-04-14T08:32:31-10:00";
		User u = new User(expectedId, expectedName, expectedVerified, expectedCreatedAt);
		
		assertEquals(expectedId, u.getId());
		assertEquals(expectedName, u.getName());
		assertEquals(expectedVerified, u.getVerified());
		assertEquals(expectedCreatedAt, u.getCreatedAt());
	}
}
