package summer.camp.judge.initialization;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import summer.camp.judge.inject.ApplicationContextListener;

/**
 * Listener for initializing Judge System initial configuration
 */
public class ConfigurationInitListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		initializeConfiguration();
	}

	private void initializeConfiguration() {
		DBInitializer instance = ApplicationContextListener.getStaticInjector().getInstance(DBInitializer.class);
		instance.initData();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// empty block
	}

}
