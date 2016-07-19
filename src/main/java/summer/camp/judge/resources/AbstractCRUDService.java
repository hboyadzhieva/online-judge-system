package summer.camp.judge.resources;

import java.util.List;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import summer.camp.judge.commons.UnitOfWorkUtils;
import summer.camp.judge.dao.AbstractJpaDao;
import summer.camp.judge.entities.IJPAEntity;
import summer.camp.judge.validation.IValidator;
import summer.camp.judge.validation.ValidationErrorResponseBuilder;

/**
 * Generic abstract CRUD service implementation
 *
 * @param <Key>
 * @param <Entity>
 */
public abstract class AbstractCRUDService<Key, Entity extends IJPAEntity<Key>> {

	private AbstractJpaDao<Key, Entity> dao;
	private IValidator<Entity> validator;
	private UnitOfWorkUtils unitOfWorkUtils;

	protected AbstractCRUDService(AbstractJpaDao<Key, Entity> dao, IValidator<Entity> validator, UnitOfWorkUtils unitOfWorkUtils) {
		this.dao = dao;
		this.validator = validator;
		this.unitOfWorkUtils = unitOfWorkUtils;
	}

	protected abstract String getNotFoundMessage(Key id);

	protected abstract void updateEntityProperties(Entity persistedEntity, Entity entity);

	protected List<Entity> getAll() {
		unitOfWorkUtils.begin();
		List<Entity> all = dao.findAll();
		unitOfWorkUtils.end();
		return all;
	}

	protected Entity get(Key id) {
		unitOfWorkUtils.begin();
		Entity entity = dao.findById(id);
		unitOfWorkUtils.end();

		if (entity == null) {
			throw new NotFoundException(getNotFoundMessage(id));
		}

		return entity;
	}

	protected Response add(Entity entity) {
		unitOfWorkUtils.begin();

		Response response = null;
		if (validator.isValidCreate(entity)) {
			dao.create(entity);
			response = Response.status(Status.CREATED).entity(entity.getKeyValue()).build();
		} else {
			response = ValidationErrorResponseBuilder.toResponse(validator);
		}

		unitOfWorkUtils.end();
		return response;
	}

	protected Response bulkUpsert(List<Entity> entities) {
		unitOfWorkUtils.begin();

		Response response = null;
		for (Entity e : entities) {
			if (!validator.isValidCreate(e) && !validator.isValidCreate(e)) {
				response = ValidationErrorResponseBuilder.toResponse(validator);
				break;
			}
		}
		if (response == null) {
			dao.bulkUpsert(entities);
			response = Response.status(Status.NO_CONTENT).build();
		}

		unitOfWorkUtils.end();
		return response;
	}

	protected Response update(Key id, Entity entity) {
		unitOfWorkUtils.begin();

		Response response = null;
		Entity persistedEntity = dao.findById(id);
		if (persistedEntity != null) {
			if (validator.isValidUpdate(entity)) {
				updateEntityProperties(persistedEntity, entity);
				dao.update(persistedEntity);
				response = Response.status(Status.NO_CONTENT).build();
			} else {
				response = ValidationErrorResponseBuilder.toResponse(validator);
			}
		} else {
			response = ValidationErrorResponseBuilder.toResponse(Status.NOT_FOUND, getNotFoundMessage(id));
		}

		unitOfWorkUtils.end();
		return response;
	}

	protected Response delete(Key id) {
		unitOfWorkUtils.begin();

		Response response = null;
		Entity persistedEntity = dao.findById(id);
		if (persistedEntity != null) {
			dao.delete(persistedEntity);
			response = Response.status(Status.NO_CONTENT).build();
		} else {
			response = ValidationErrorResponseBuilder.toResponse(Status.NOT_FOUND, getNotFoundMessage(id));
		}

		unitOfWorkUtils.end();
		return response;
	}

	protected Long countAll() {
		return dao.countAll();
	}
}
