package summer.camp.judge.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import summer.camp.judge.entities.Task;
import summer.camp.judge.entities.User;

/**
 * DAO class for {@link Task}
 */
public class UserDao extends AbstractJpaDao<String, User> {

	private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

	protected UserDao() {
		super(User.class);
	}

	@Override
	public void create(final User user) {
		// user.setPassword(getHashedPassword(user.getPassword()));

		getEntityManager().getTransaction().begin();
		getEntityManager().persist(user);
		getEntityManager().getTransaction().commit();
	}

	public User byEmailAndPassword(String email, String password) {
		String hashedPassword = getHashedPassword(password);
		String query = "SELECT u FROM User u WHERE u.email = :email AND u.password = :password";
		TypedQuery<User> userQuery = this.getEntityManager().createQuery(query, User.class);
		userQuery.setParameter("email", email);
		userQuery.setParameter("password", hashedPassword);

		User user = userQuery.getSingleResult();
		return user;
	}

	@Override
	protected Logger getLogger() {
		return logger;
	}

	private String getHashedPassword(String password) {
		MessageDigest mda;
		try {
			mda = MessageDigest.getInstance("SHA-512");
			password = new String(mda.digest(password.getBytes()));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password;
	}

}
