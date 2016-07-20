package summer.camp.judge.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
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
	private long id;

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

	@OneToMany
	@JoinColumn(name = "TASKID", referencedColumnName = "ID")
	private List<TestCase> testCases;

	/**
	 * Needed for serialization . desrialization
	 */
	public Task() {
		// empty block
	}

	public Task(String name, String statement, String sampleInput, String sampleOutput, Long timelimit) {
		this.name = name;
		this.statement = statement;
		this.sampleInput = sampleInput;
		this.sampleOutput = sampleOutput;
		this.timelimit = timelimit;
	}

	public Long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public List<TestCase> getTestCases() {
		return testCases;
	}

	public void setTestCases(List<TestCase> testCases) {
		this.testCases = testCases;
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

}
