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
import summer.camp.judge.dao.TestCaseDao;
import summer.camp.judge.entities.TestCase;
import summer.camp.judge.validation.TestCaseValidator;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Service for educations
 */
@Singleton
@Path("/testcases")
public class TestCaseResource extends AbstractCRUDService<Long, TestCase> {

	private static final String ERROR_THERE_IS_NO_TASK_WITH_TASK_ID_MESSAGE = "There is no task with [taskId={0}]";

	/**
	 * Constructor
	 *
	 * @param taskDao
	 * @param taskValidator
	 * @param unitOfWorkUtils
	 */
	@Inject
	public TestCaseResource(TestCaseDao testcaseDao, TestCaseValidator testcaseValidator, UnitOfWorkUtils unitOfWorkUtils) {
		super(testcaseDao, testcaseValidator, unitOfWorkUtils);
	}

	/**
	 * Returns a list of all tasks
	 *
	 * @return a list of all tasks
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<TestCase> getTestsCase() {
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
	public TestCase getEducation(@PathParam("id") final Long id) {
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
	public Response addEducation(TestCase task) {
		return add(task);
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
	public Response updateTestCase(@PathParam("id") final Long id, TestCase testcase) {
		return update(id, testcase);
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
	protected void updateEntityProperties(TestCase persistedEntity, TestCase entity) {
		persistedEntity.setInput(entity.getInput());
		persistedEntity.setOutput(entity.getOutput());

	}

}
