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
 * Entity implementation class for Entity: Solution
 */
@Entity
@Table(name = "T_SOLUTION")
public class Solution implements IJPAEntity<Long>, Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long solutionId;

	@Column(length = 256, nullable = false)
	private String text;

	@Column(length = 64, nullable = false)
	private String language;

	@ManyToOne
	private Task task;

	@ManyToOne
	private User user;

	public Solution() {
		super();
	}

	public Solution(Long solutionId, String text, String language, Task task, User user) {
		super();
		this.solutionId = solutionId;
		this.text = text;
		this.language = language;
		this.task = task;
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/** {@inheritDoc} */
	@Override
	public Long getKeyValue() {
		return solutionId;
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
