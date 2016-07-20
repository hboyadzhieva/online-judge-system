package summer.camp.judge.resources;

import java.text.MessageFormat;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import summer.camp.judge.commons.UnitOfWorkUtils;
import summer.camp.judge.dao.SolutionDao;
import summer.camp.judge.entities.Solution;
import summer.camp.judge.validation.SolutionValidator;

/**
 * Service for educations
 */
@Singleton
@Path("/protected/admin/tasks")
public class SolutionResource extends AbstractCRUDService<Long, Solution> {

	private static final String ERROR_THERE_IS_NO_TASK_WITH_TASK_ID_MESSAGE = "There is no task with [taskId={0}]";

	/**
	 * Constructor
	 *
	 * @param taskDao
	 * @param taskValidator
	 * @param unitOfWorkUtils
	 */
	@Inject
	public SolutionResource(SolutionDao solutionDao, SolutionValidator solutionValidator, UnitOfWorkUtils unitOfWorkUtils) {
		super(solutionDao, solutionValidator, unitOfWorkUtils);
	}

	/**
	 * Returns a list of all tasks
	 *
	 * @return a list of all tasks
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Solution> getSolution() {
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
	public Solution getEducation(@PathParam("id") final Long id) {
		return get(id);
	}

	/**
	 * Adds new task
	 *
	 * @param task
	 * @return HTTP 201 CREATED if the task was successfully added or
	 *         HTTP 400 BAD REQUEST if something went wrong
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEducation(Solution solution) {
		return add(solution);
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
	public Response updateSolution(@PathParam("id") final Long id, Solution solution) {
		return update(id, solution);
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

	@Override
	protected String getNotFoundMessage(Long id) {
		return MessageFormat.format(ERROR_THERE_IS_NO_TASK_WITH_TASK_ID_MESSAGE, id);
	}

	@Override
	protected void updateEntityProperties(Solution persistedEntity, Solution entity) {
		persistedEntity.setText(entity.getText());
		persistedEntity.setLanguage(entity.getLanguage());
		persistedEntity.setTask(entity.getTask());
		persistedEntity.setUser(entity.getUser());
	}

}
