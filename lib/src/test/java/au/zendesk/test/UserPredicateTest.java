package au.zendesk.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UserPredicateTest {
	
	public List<User> users;
	public User u1;
	public User u2;
	public User u3;
	public User u4;
	public User u5;
	
	@Before
	public void setUp() {
		users = new ArrayList<User>();
		u1 = new User(
				"",
				"John Smith",
				"true",
				"2016-06-23T10:31:39-10:00");
		u2 = new User(
				"1",
				"",
				"false",
				"2016-02-09T07:52:10-11:00");
		u3 = new User(
				"2",
				"Apple Bee",
				"",
				"2016-04-04T01:30:49-10:00");
		u4 = new User(
				"3",
				"Keanu Reeves",
				"true",
				"");
		u5 = new User(
				"4",
				"Brôôks Burke",
				"true",
				"");
		users = Arrays.asList(u1, u2, u3, u4);
	}
	
	@Test
	public void filterUsers_EmptyList() {
		String key = User.Field.ID.get();
		String value = "7";
		
		List<User> actual = UserPredicate.filter(users, 
				UserPredicate.search(key, value));
		
		assertTrue(actual.isEmpty());
	}
	
	@Test
	public void filterUsers_Id() {
		String key = User.Field.ID.get();
		String value = "2";
		
		List<User> actual = UserPredicate.filter(users, 
				UserPredicate.search(key, value));
		
		List<User> expected = Arrays.asList(u3);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterUsers_Id_Empty() {
		String key = User.Field.ID.get();
		String value = "";
		
		List<User> actual = UserPredicate.filter(users, 
				UserPredicate.search(key, value));
		
		List<User> expected = Arrays.asList(u1);
		
		assertEquals(expected.toString(), actual.toString());
	}

	@Test
	public void filterUsers_Name() {
		String key = User.Field.NAME.get();
		String value = "Keanu Reeves";
		
		List<User> actual = UserPredicate.filter(users, 
				UserPredicate.search(key, value));
		
		List<User> expected = Arrays.asList(u4);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterUsers_Name_Empty() {
		String key = User.Field.NAME.get();
		String value = "";
		
		List<User> actual = UserPredicate.filter(users, 
				UserPredicate.search(key, value));
		
		List<User> expected = Arrays.asList(u2);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterUsers_NameNonUTF_8() {
		String key = User.Field.NAME.get();
		String value = "Brôôks Burke";
		
		List<User> actual = UserPredicate.filter(users, 
				UserPredicate.search(key, value));
		
		List<User> expected = Arrays.asList(u5);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterUsers_Verified() {
		String key = User.Field.VERIFIED.get();
		String value = "true";
		
		List<User> actual = UserPredicate.filter(users, 
				UserPredicate.search(key, value));
		
		List<User> expected = Arrays.asList(u1,u4);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterUsers_Verified_Empty() {
		String key = User.Field.VERIFIED.get();
		String value = "";
		
		List<User> actual = UserPredicate.filter(users, 
				UserPredicate.search(key, value));
		
		List<User> expected = Arrays.asList(u3);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterUsers_CreatedAt() {
		String key = User.Field.CREATED_AT.get();
		String value = "2016-02-09T07:52:10-11:00";
		
		List<User> actual = UserPredicate.filter(users, 
				UserPredicate.search(key, value));
		
		List<User> expected = Arrays.asList(u2);
		
		assertEquals(expected.toString(), actual.toString());
	}
	
	@Test
	public void filterUsers_CreatedAt_Empty() {
		String key = User.Field.CREATED_AT.get();
		String value = "";
		
		List<User> actual = UserPredicate.filter(users, 
				UserPredicate.search(key, value));
		
		List<User> expected = Arrays.asList(u4);
		
		assertEquals(expected.toString(), actual.toString());
	}
}
