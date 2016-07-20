package summer.camp.judge.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import summer.camp.judge.entities.Task;
import summer.camp.judge.entities.User;

/**
 * DAO class for {@link Task}
 */
public class UserDao extends AbstractJpaDao<Long, User> {

	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	protected UserDao() {
		super(User.class);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
