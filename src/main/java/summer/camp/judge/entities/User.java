package summer.camp.judge.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.gson.Gson;

/**
 * Entity implementation class for Entity: User
 */
@Entity
@Table(name = "T_USER")
public class User implements IJPAEntity<String>, Serializable {

	private static final long serialVersionUID = 6014718856140269190L;

	@Id
	private String id;

	@Column(length = 64, nullable = false)
	private String firstName;

	@Column(length = 64, nullable = false)
	private String lastName;

	@Column(length = 256, nullable = false)
	private String email;

	// @Column(length = 256, nullable = false)
	// private String password;

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

	public User(String firstName, String lastName, String email, String password, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		// this.password = password;
		this.phoneNumber = phoneNumber;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	// public String getPassword() {
	// return password;
	// }
	//
	// public void setPassword(String password) {
	// this.password = password;
	// }

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/** {@inheritDoc} */
	@Override
	public String getKeyValue() {
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
