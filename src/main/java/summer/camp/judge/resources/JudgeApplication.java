package summer.camp.judge.resources;

import java.util.Set;

import javax.ws.rs.core.Application;

import summer.camp.judge.inject.ApplicationContextListener;

/**
 * This class extends the CXF Application and adds CXF services
 */
public class JudgeApplication extends Application {

	@Override
	public Set<Object> getSingletons() {
		return ApplicationContextListener.getSingletons();
	}
}
