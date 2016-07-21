package summer.camp.judge.request;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;

import com.sap.security.auth.login.LoginContextFactory;

/**
 * Class for management user related stuffs
 */
public class UserManager {

	private static final ThreadLocal<String> username = new ThreadLocal<String>();
	private static final ThreadLocal<List<UserRole>> userRoles = new ThreadLocal<List<UserRole>>();

	/**
	 * The enumerations in the Roles enum, should map the roles that are defined in the web.xml
	 */
	public static enum UserRole {
		/**
		 * The Anonymous role
		 */
		ANONYMOUS("Anonymous"),

		/**
		 * The Everyone role
		 */
		EVERYONE("Everyone"),

		/**
		 * The Support role
		 */
		SUPPORT("Support"),

		/**
		 * The Admin role
		 */
		ADMIN("Admin");

		private final String name;

		private UserRole(String name) {
			this.name = name;
		}

		/**
		 * Returns the String representation of the name of the role
		 *
		 * @return the String representation of the name of the role
		 */
		public String getName() {
			return name;
		}
	}

	/**
	 * Stores the roles of the user in a thread local context
	 *
	 * @param request
	 */
	static void setUp(HttpServletRequest request) {
		setUsername(request);
		setUserRoles(request);
	}

	/**
	 * Clean up the stored user information from the thread local context
	 */
	static void cleanUp() {
		username.remove();
		if (userRoles.get() != null) {
			userRoles.get().clear();
		}
		userRoles.remove();
	}

	private static void setUsername(HttpServletRequest request) {
		String name = request.getUserPrincipal() != null ? request.getUserPrincipal().getName() : null;
		UserManager.username.set(name);
	}

	private static void setUserRoles(HttpServletRequest request) {
		if (userRoles.get() == null) {
			userRoles.set(new ArrayList<UserRole>());
		} else {
			userRoles.get().clear();
		}

		for (UserRole next : UserRole.values()) {
			if (request.isUserInRole(next.getName())) {
				userRoles.get().add(next);
			}
		}
	}

	/**
	 * Returns the user name of the the current logged in user
	 *
	 * @return the user name of the the current logged in user
	 */
	public static String getUsername() {
		return username.get();
	}

	/**
	 * Returns a list of all roles that the user have
	 *
	 * @return list of all roles that the user have
	 */
	public static List<UserRole> getUserRoles() {
		return userRoles.get();
	}

	public static void logout() throws LoginException {
		if (username.get() != null) {
			LoginContext loginContext = LoginContextFactory.createLoginContext();
			loginContext.logout();
		}
	}
}
