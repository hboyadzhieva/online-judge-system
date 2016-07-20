package summer.camp.judge.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.google.gson.Gson;

/**
 * Entity implementation class for Entity: TestCase
 */
@Entity
@Table(name = "T_TEST_CASE")
public class TestCase implements IJPAEntity<Long>, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -3870580534573288562L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long testCaseId;

	@Column(length = 64, nullable = false)
	private String input;

	@Column(length = 64, nullable = false)
	private String output;

	@ManyToOne
	private Task task;

	/**
	 * Needed for serialization . desrialization
	 */
	public TestCase() {
		super();
	}

	public TestCase(String input, String output, Task task) {
		super();
		this.input = input;
		this.output = output;
		this.task = task;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	/** {@inheritDoc} */
	@Override
	public Long getKeyValue() {
		return testCaseId;
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return toJson();
	}

	/**
	 * Convert Feature to JSON.
	 *
	 * @return target JSON
	 */
	public String toJson() {
		return new Gson().toJson(this);
	}

}
