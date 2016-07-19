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
 * Entity class representing the T_TASK database table
 */
@Entity
@Table(name = "T_TASK")
public class Task implements IJPAEntity<Long>, Serializable {

	private static final long serialVersionUID = 8484404984922079077L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long taskId;

	@Column(length = 64, nullable = false)
	private String name;

	@Column(length = 256, nullable = false)
	private String statement;

	@Column(length = 256)
	private String sampleInput;

	@Column(length = 64)
	private String sampleOutput;

	@Column
	private Long timelimit;

	/**
	 * Needed for serialization . desrialization
	 */
	public Task() {
		// empty block
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatement() {
		return statement;
	}

	public void setStatement(String statement) {
		this.statement = statement;
	}

	public String getSampleInput() {
		return sampleInput;
	}

	public void setSampleInput(String sampleInput) {
		this.sampleInput = sampleInput;
	}

	public String getSampleOutput() {
		return sampleOutput;
	}

	public void setSampleOutput(String sampleOutput) {
		this.sampleOutput = sampleOutput;
	}

	public Long getTimelimit() {
		return timelimit;
	}

	public void setTimelimit(Long timelimit) {
		this.timelimit = timelimit;
	}

	/** {@inheritDoc} */
	@Override
	public Long getKeyValue() {
		return taskId;
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
