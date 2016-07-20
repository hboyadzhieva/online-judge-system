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

import summer.camp.judge.commons.UnitOfWorkUtils;
import summer.camp.judge.dao.SolutionDao;
import summer.camp.judge.entities.Solution;
import summer.camp.judge.validation.SolutionValidator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Service for educations
 */
@Singleton
@Path("/solutions")
public class SolutionResource extends AbstractCRUDService<Long, Solution> {

	private static final String ERROR_THERE_IS_NO_TASK_WITH_TASK_ID_MESSAGE = "There is no task with [id={0}]";

	/**
	 * Constructor
	 *
	 * @param solutionDao
	 * @param solutionValidator
	 * @param unitOfWorkUtils
	 */
	@Inject
	public SolutionResource(SolutionDao solutionDao, SolutionValidator solutionValidator, UnitOfWorkUtils unitOfWorkUtils) {
		super(solutionDao, solutionValidator, unitOfWorkUtils);
	}

	/**
	 * Returns a list of all solutions
	 *
	 * @return a list of all solutions
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Solution> getSolution() {
		return getAll();
	}

	/**
	 * Returns a solution specified by the id path parameter
	 *
	 * @param id
	 * @return a solution specified by the id path parameter
	 */
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Solution getEducation(@PathParam("id") final Long id) {
		return get(id);
	}

	/**
	 * Adds new solution
	 *
	 * @param solution
	 * @return HTTP 201 CREATED if the solution was successfully added or
	 *         HTTP 400 BAD REQUEST if something went wrong
	 */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addEducation(Solution solution) {
		return add(solution);
	}

	/**
	 * Updates existing solution
	 *
	 * @param id
	 * @param solution
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
	 * Deletes existing solution
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
	 * Returns the count of all solutions
	 *
	 * @return the count of all solutions
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
