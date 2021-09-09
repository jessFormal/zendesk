package au.zendesk.test;

import java.util.Comparator;

/**
 * Comparator to sort users by user id in ascending order
 */
public class SortByUserId implements Comparator<User>{

	@Override
	public int compare(User u1, User u2) {
		String userIdKey = User.Field.ID.get();
		return u1.getValue(userIdKey).compareTo(u2.getValue(userIdKey));
	}

}
