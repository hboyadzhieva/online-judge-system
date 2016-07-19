package summer.camp.judge.validation;

import javax.ws.rs.core.Response.Status;

import summer.camp.judge.entities.Task;

import com.google.inject.Singleton;

/**
 * Class for validating {@link Task}
 */
@Singleton
public class TaskValidator implements IValidator<Task> {

	private static final String VALIDATION_MESSAGE_THE_EDUCATION_ENTITY_CAN_T_BE_NULL = "The task entity can't be null";
	private static final String VALIDATION_MESSAGE_THE_DEGREE_PROPERTY_CAN_T_BE_NULL = "The [name] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_SCHOOL_NAME_PROPERTY_CAN_T_BE_NULL = "The [statement] property can't be null";

	private String validationMessage;

	@Override
	public boolean isValidCreate(Task entity) {
		return haveAllProperties(entity);

	}

	@Override
	public boolean isValidUpdate(Task entity) {
		return haveAllProperties(entity);
	}

	private boolean haveAllProperties(Task entity) {
		boolean isValid = false;
		if (entity == null) {
			validationMessage = VALIDATION_MESSAGE_THE_EDUCATION_ENTITY_CAN_T_BE_NULL;
		} else if (entity.getName() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_DEGREE_PROPERTY_CAN_T_BE_NULL;
		} else if (entity.getStatement() == null) {
			validationMessage = VALIDATION_MESSAGE_THE_SCHOOL_NAME_PROPERTY_CAN_T_BE_NULL;
		} else {
			isValid = true;
		}
		return isValid;
	}

	@Override
	public String getValidationMessage() {
		return validationMessage;
	}

	@Override
	public Status getResponseStatus() {
		return null;
	}

}
