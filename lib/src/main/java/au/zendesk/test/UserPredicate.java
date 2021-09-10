package au.zendesk.test;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class to search and filter a list of Users with given field and values
 */
public class UserPredicate {
	
	private UserPredicate() {
	    throw new IllegalStateException("User Predicate Helper Class");
	}
	
	public static List<User> filter( List<User> users, 
			Predicate<User> predicate) {
		return users.parallelStream()
                .filter( predicate )
                .collect(Collectors.<User>toList());
	}
	
	public static Predicate<User> search(String key, String value) {
		return user -> user.getValue(key).equalsIgnoreCase(value);
	}
}
