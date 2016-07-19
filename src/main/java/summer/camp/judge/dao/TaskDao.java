package summer.camp.judge.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import summer.camp.judge.entities.Task;

/**
 * DAO class for {@link Task}
 */
public class TaskDao extends AbstractJpaDao<Long, Task> {

	private static final Logger logger = LoggerFactory.getLogger(TaskDao.class);

	protected TaskDao() {
		super(Task.class);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
