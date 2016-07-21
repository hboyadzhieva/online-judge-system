package summer.camp.judge.resources;

import javax.security.auth.login.LoginException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.cxf.common.util.StringUtils;

import summer.camp.judge.commons.UnitOfWorkUtils;
import summer.camp.judge.dao.UserDao;
import summer.camp.judge.entities.User;
import summer.camp.judge.request.UserManager;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.sap.security.um.service.UserManagementAccessor;
import com.sap.security.um.user.UnsupportedUserAttributeException;
import com.sap.security.um.user.UserProvider;

@Singleton
@Path("/userAuth")
public class UserRegistrationResource {

	private UserDao userDao;
	private UnitOfWorkUtils unitOfWorkUtils;

	private static final String RESPONSE_LOGGING_OUT_WAS_UNSUCCESSFUL_MESSAGE = "Logging out was unsuccessful";
	private static final String RESPONSE_USER_ATTRIBUTE_NOT_FOUND_MESSAGE = "User attribute: firstname, lastname or email not found.";
	private static final String RESPONSE_SUCCESSFULLY_LOGGED_OUT_MESSAGE = "You have successfully logged out";

	/**
	 * Constructor
	 *
	 * @param userDao
	 */
	@Inject
	public UserRegistrationResource(UserDao userDao) {
		this.userDao = userDao;
		this.unitOfWorkUtils = new UnitOfWorkUtils();
	}

	/**
	 * @return HTTP 200 OK if there were no exceptions during the registration
	 */
	@POST
	@Path("/user-registration")
	public Response registerUser() {
		unitOfWorkUtils.begin();
		String userId = UserManager.getUsername();
		if (!StringUtils.isEmpty(userId)) {
			com.sap.security.um.user.User idmUser = null;
			try {
				UserProvider provider = UserManagementAccessor.getUserProvider();
				idmUser = provider.getUser(userId);
			} catch (Exception e) {
				// Ignore the exception
			}

			try {
				User user = userDao.findById(userId);
				if (idmUser != null) {
					String firstName = idmUser.getAttribute("firstname");
					String lastName = idmUser.getAttribute("lastname");
					String email = idmUser.getAttribute("email");

					// Read the currently logged in user from the user storage

					if (user == null) {
						user = new User();
						user.setId(userId);
						user.setFirstName(firstName);
						user.setLastName(lastName);
						user.setEmail(email);

						userDao.create(user);

					} else {
						boolean doUpdate = false;
						if (user.getFirstName() != firstName) {
							user.setFirstName(firstName);
							doUpdate = true;
						}
						if (user.getLastName() != lastName) {
							user.setLastName(lastName);
							doUpdate = true;
						}
						if (user.getEmail() != email) {
							user.setEmail(email);
							doUpdate = true;
						}
						if (doUpdate) {
							userDao.update(user);
						}
					}
				} else {
					if (user == null) {
						user = new User();
						user.setId(userId);
						userDao.create(user);
					}
				}
			} catch (UnsupportedUserAttributeException e) {
				e.printStackTrace();
			}
		}
		unitOfWorkUtils.end();
		return javax.ws.rs.core.Response.ok().build();
	}

	/**
	 * @return response
	 */
	@GET
	@Path("/delete")
	public Response deleteUser() {
		unitOfWorkUtils.begin();
		String userId = UserManager.getUsername();
		if (!StringUtils.isEmpty(userId)) {
			User user = userDao.findById(userId);
			if (user != null) {
				userDao.delete(user);
			}
		}
		unitOfWorkUtils.end();
		return Response.ok().build();
	}

	@GET
	@Path("/getUserInfo")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response getUserInfo() {

		String userName = UserManager.getUsername();
		User user = userDao.findById(userName);

		return Response.ok().entity(user).build();
	}

	@GET
	@Path("/logout")
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public Response logout() {

		Response response;
		try {

			UserManager.logout();
			response = Response.status(Status.OK).entity(RESPONSE_SUCCESSFULLY_LOGGED_OUT_MESSAGE).build();

		} catch (LoginException e) {

			response = Response.status(Status.UNAUTHORIZED).entity(RESPONSE_LOGGING_OUT_WAS_UNSUCCESSFUL_MESSAGE).build();
		}
		return response;
	}
}
