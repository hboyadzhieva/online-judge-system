package summer.camp.judge.request;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response.Status;

import summer.camp.judge.dao.UserDao;
import summer.camp.judge.inject.ApplicationContextListener;
import summer.camp.judge.request.UserManager.UserRole;
import summer.camp.judge.resources.UserRegistrationResource;

/**
 * Application filter
 */
public class ApplicationFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// Do nothing
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		UserManager.setUp(httpRequest);
		authorizationCheck(httpRequest, httpResponse, chain);
	}

	private void authorizationCheck(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (!isAuthenticated()) {
			sendError(response, Status.UNAUTHORIZED);
		} else {
			UserDao userDao = ApplicationContextListener.getStaticInjector().getInstance(UserDao.class);
			UserRegistrationResource urs = new UserRegistrationResource(userDao);
			urs.registerUser();
			if (!request.getMethod().equalsIgnoreCase("HEAD")) {
				chain.doFilter(request, response);
			}
		}
	}

	private void sendError(HttpServletResponse response, Status status) throws IOException {
		response.sendError(status.getStatusCode(), status.getReasonPhrase());
	}

	private boolean isAuthenticated() {
		return UserManager.getUserRoles().contains(UserRole.EVERYONE);
	}

	private boolean haveAdminRole() {
		return UserManager.getUserRoles().contains(UserRole.ADMIN);
	}

	@Override
	public void destroy() {
		UserManager.cleanUp();
	}

}
