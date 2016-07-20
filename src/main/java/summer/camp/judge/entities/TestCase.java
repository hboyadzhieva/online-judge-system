package summer.camp.judge.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;

/**
 * Entity implementation class for Entity: TestCase
 */
@Entity
@Table(name = "T_TEST_CASE")
public class TestCase implements IJPAEntity<Long>, Serializable {

	private static final long serialVersionUID = -3870580534573288562L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 64, nullable = false)
	private String inputData;

	@Column(length = 64, nullable = false)
	private String result;

	@Column(name = "TASKID")
	private Long taskId;

	/**
	 * Needed for serialization . desrialization
	 */
	public TestCase() {
	}

	public TestCase(String inputData, String result) {
		this.inputData = inputData;
		this.result = result;
	}

	public String getInput() {
		return inputData;
	}

	public void setInput(String inputData) {
		this.inputData = inputData;
	}

	public String getOutput() {
		return result;
	}

	public void setOutput(String result) {
		this.result = result;
	}

	/** {@inheritDoc} */
	@Override
	public Long getKeyValue() {
		return id;
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

	public Long getId() {
		return id;
	}

	public void setTaskId(long id) {
		this.id = id;
	}

}
