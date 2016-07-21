package summer.camp.judge.commons;

import java.io.Serializable;

import com.google.inject.Singleton;

import summer.camp.judge.entities.User;

@Singleton
public class CurrentUserContext implements Serializable {
	private static final long serialVersionUID = -6225458652154987336L;

	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
