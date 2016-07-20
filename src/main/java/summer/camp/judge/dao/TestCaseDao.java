package summer.camp.judge.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import summer.camp.judge.entities.TestCase;

/**
 * DAO class for {@link TestCase}
 */
public class TestCaseDao extends AbstractJpaDao<Long, TestCase> {

	private static final Logger logger = LoggerFactory.getLogger(TestCaseDao.class);

	protected TestCaseDao() {
		super(TestCase.class);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
