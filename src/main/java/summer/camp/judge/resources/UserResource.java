package summer.camp.judge.resources;

import java.text.MessageFormat;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import summer.camp.judge.commons.CurrentUserContext;
import summer.camp.judge.commons.UnitOfWorkUtils;
import summer.camp.judge.dao.UserDao;
import summer.camp.judge.entities.User;
import summer.camp.judge.validation.UserValidator;

/**
 * Service for educations
 */
@Singleton
@Path("/users")
public class UserResource extends AbstractCRUDService<Long, User> {

	private static final String ERROR_THERE_IS_NO_TASK_WITH_TASK_ID_MESSAGE = "There is no task with [taskId={0}]";

	@Inject
	private CurrentUserContext userContext;

	@Inject
	private UserDao userDao;

	/**
	 * Constructor
	 *
	 * @param userDao
	 * @param userValidator
	 * @param unitOfWorkUtils
	 */
	@Inject
	public UserResource(UserDao userDao, UserValidator userValidator, UnitOfWorkUtils unitOfWorkUtils) {
		super(userDao, userValidator, unitOfWorkUtils);
	}

	/**
	 * Returns a list of all users
	 *
	 * @return a list of all users
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getUsers() {
		return getAll();
	}

	/**
	 * Returns a task specified by the id path parameter
	 *
	 * @param id
	 * @return a task specified by the id path parameter
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getEducation(@PathParam("id") final Long id) {
		return get(id);
	}

	/**
	 * Adds new task
	 *
	 * @param user
	 * @return HTTP 201 CREATED if the task was successfully added or
	 *         HTTP 400 BAD REQUEST if something went wrong
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEducation(User user) {
		return add(user);
	}

	/**
	 * Updates existing task
	 *
	 * @param id
	 * @param task
	 * @return HTTP 204 NO CONTENT if the update was successful or
	 *         HTTP 404 NOT FOUND if there was no such task
	 */
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUser(@PathParam("id") final Long id, User user) {
		return update(id, user);
	}

	/**
	 * Deletes existing task
	 *
	 * @param id
	 * @return HTTP 204 NO CONTENT if the deletion was successful or
	 *         HTTP 404 NOT FOUND if there was no such education
	 */
	@DELETE
	@Path("/{id}")
	public Response deleteEducation(@PathParam("id") final Long id) {
		return delete(id);
	}

	/**
	 * Returns the count of all tasks
	 *
	 * @return the count of all tasks
	 */
	@GET
	@Path("/count")
	public Long count() {
		return countAll();
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(User user) {
		User validatedUser = this.userDao.byEmailAndPassword(user.getEmail(), user.getPassword());
		this.userContext.setUser(validatedUser);

		if (validatedUser == null) {
			throw new InternalServerErrorException();
		}

		return Response.ok(validatedUser).build();
	}

	@GET
	@Path("/current")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCurrentUser() {
		User currentUser = this.userContext.getUser();

		return Response.ok(currentUser).build();
	}

	@Override
	protected String getNotFoundMessage(Long id) {
		return MessageFormat.format(ERROR_THERE_IS_NO_TASK_WITH_TASK_ID_MESSAGE, id);
	}

	@Override
	protected void updateEntityProperties(User persistedEntity, User entity) {
		// / TODO
		persistedEntity.setFirstName(entity.getFirstName());
		persistedEntity.setLastName(entity.getLastName());
		persistedEntity.setEmail(entity.getEmail());
		persistedEntity.setPhoneNumber(entity.getPhoneNumber());

	}

}
