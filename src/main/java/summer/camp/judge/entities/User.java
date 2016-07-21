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
 * Entity implementation class for Entity: User
 */
@Entity
@Table(name = "T_USER")
public class User implements IJPAEntity<Long>, Serializable {

	private static final long serialVersionUID = 6014718856140269190L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(length = 64, nullable = false)
	private String firstName;

	@Column(length = 64, nullable = false)
	private String lastName;

	@Column(length = 256, nullable = false)
	private String email;

	@Column(length = 256)
	private String phoneNumber;

	public String getFirstName() {
		return firstName;
	}

	/**
	 * Needed for serialization . deserialization
	 */
	public User() {
	}

	public User(String firstName, String lastName, String email, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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
