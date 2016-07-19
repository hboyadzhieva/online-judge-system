package summer.camp.judge.commons;

/**
 * Enumeration of the verdict types
 */
public enum VerdictType {

	/**
	 * The technical skill type
	 */
	COMPILE_SUCCESS("Compilation Successful"),

	/**
	 * The soft skill type
	 */
	COMPILE_ERROR("Compilation Error"),

	/**
	 * Others type
	 */
	OTHER("Unexpected Behaviour"),

	/**
	 * The hobby type
	 */
	RUN_SUCCESS("Execution Succesful"),

	/**
	 *
	 */
	RUN_ERROR("Execution Error");

	private final String name;

	VerdictType(String name) {
		this.name = name;
	}

	/**
	 * Returns the name of the verdict type
	 *
	 * @return the name of the verdict type
	 */
	public String getName() {
		return name;
	}
}
