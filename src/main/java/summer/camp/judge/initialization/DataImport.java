package summer.camp.judge.initialization;

import java.util.List;

import summer.camp.judge.dao.AbstractJpaDao;
import summer.camp.judge.entities.IJPAEntity;

/**
 * Data import for demo purposes
 *
 * @param <Key>
 * @param <Entity>
 */
public class DataImport<Key, Entity extends IJPAEntity<Key>> {

	/**
	 * Imports entity data using the provided entity implementation
	 *
	 * @param objects
	 * @param dao
	 */
	public void importFromJSON(List<Entity> objects, AbstractJpaDao<Key, Entity> dao) {
		dao.delete(dao.findAll());
		for (Entity d : objects) {
			dao.create(d);
		}
	}
}
