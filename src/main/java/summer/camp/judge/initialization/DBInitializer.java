package summer.camp.judge.initialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import summer.camp.judge.dao.SolutionDao;
import summer.camp.judge.dao.TaskDao;
import summer.camp.judge.dao.TestCaseDao;
import summer.camp.judge.dao.UserDao;
import summer.camp.judge.entities.Solution;
import summer.camp.judge.entities.Task;
import summer.camp.judge.entities.TestCase;
import summer.camp.judge.entities.User;

import com.google.gson.Gson;
import com.google.inject.Inject;

/**
 * Database initializer
 */
public class DBInitializer {

	private TaskDao taskDao;
	private TestCaseDao testCaseDao;
	private UserDao userDao;
	private SolutionDao solutionDao;

	private static final String TEST_CASES = "/data/testcases.json";
	private static final String TASKS = "/data/tasks.json";
	private static final String SOLUTIONS = "/data/solutions.json";
	private static final String USERS = "/data/users.json";
	private static final String ERROR_LOADING_INITIAL_CONFIGURATION_MESSAGE = "Error while initializing tank app initial configuration [{}]";

	private static final Logger logger = LoggerFactory.getLogger(DBInitializer.class);

	/**
	 * Constructor
	 *
	 * @param taskDao
	 * @param testCaseDao
	 * @param userDao
	 * @param solutionDao
	 */
	@Inject
	public DBInitializer(TaskDao taskDao, TestCaseDao testCaseDao, UserDao userDao, SolutionDao solutionDao) {
		this.taskDao = taskDao;
		this.testCaseDao = testCaseDao;
		this.userDao = userDao;
		this.solutionDao = solutionDao;
	}

	/**
	 * Fills the database with initial data
	 */
	public void initData() {
		new DataImport<Long, TestCase>().importFromJSON(Arrays.asList(parseJsonFileConfiguration(TEST_CASES, TestCase[].class)), testCaseDao);
		new DataImport<Long, Task>().importFromJSON(Arrays.asList(parseJsonFileConfiguration(TASKS, Task[].class)), taskDao);
		new DataImport<Long, User>().importFromJSON(Arrays.asList(parseJsonFileConfiguration(USERS, User[].class)), userDao);
		new DataImport<Long, Solution>().importFromJSON(Arrays.asList(parseJsonFileConfiguration(SOLUTIONS, Solution[].class)), solutionDao);
	}

	private <T> T parseJsonFileConfiguration(String filename, Class<T> type) {
		T result = null;
		InputStream resourceAsStream = DBInitializer.class.getResourceAsStream(filename);
		try (InputStreamReader reader = new InputStreamReader(resourceAsStream)) {
			if (resourceAsStream.available() != 0) {
				result = new Gson().fromJson(reader, type);
			}
		} catch (IOException e) {
			logger.error(ERROR_LOADING_INITIAL_CONFIGURATION_MESSAGE, e);
		}
		return result;
	}
}
