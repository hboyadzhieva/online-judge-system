package summer.camp.judge.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import summer.camp.judge.entities.Solution;

/**
 * DAO class for {@link Solution}
 */
public class SolutionDao extends AbstractJpaDao<Long, Solution> {

	private static final Logger logger = LoggerFactory.getLogger(SolutionDao.class);

	protected SolutionDao() {
		super(Solution.class);
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

}
