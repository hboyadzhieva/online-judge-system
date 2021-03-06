package summer.camp.judge.validation;

import javax.ws.rs.core.Response.Status;

import com.google.inject.Singleton;

import summer.camp.judge.entities.Task;
import summer.camp.judge.entities.TestCase;

/**
 * Class for validating {@link Task}
 */
@Singleton
public class TestCaseValidator implements IValidator<TestCase> {

	private static final String VALIDATION_MESSAGE_THE_EDUCATION_ENTITY_CAN_T_BE_NULL = "The task entity can't be null";
	private static final String VALIDATION_MESSAGE_THE_DEGREE_PROPERTY_CAN_T_BE_NULL = "The [name] property can't be null";
	private static final String VALIDATION_MESSAGE_THE_SCHOOL_NAME_PROPERTY_CAN_T_BE_NULL = "The [statement] property can't be null";

	private String validationMessage;

	@Override
	public boolean isValidCreate(TestCase entity) {
		return haveAllProperties(entity);

	}

	@Override
	public boolean isValidUpdate(TestCase entity) {
		return haveAllProperties(entity);
	}

	private boolean haveAllProperties(TestCase entity) {
		/// TODO
		return true;
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
