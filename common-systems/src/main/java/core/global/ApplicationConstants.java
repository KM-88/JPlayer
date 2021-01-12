package core.global;

import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import framew.log.Logger;

public class ApplicationConstants {

	/**
	 * @param configLocation
	 */
	public ApplicationConstants(String configLocation) {
		super();
		this.configLocation = configLocation;
		configure();
		getLogger().debug(getClass(), "Constructor", configLocation);
	}

	protected String configLocation;
	public static final String LOGGER = "logger";
	public static final String SERVICES = "service";
	protected static ApplicationContext context;

	public static void registerNewEntity(String key, Object value, boolean overWrite) {
	}

	public void createThreadResources(Map<String, Boolean> servicesStatus) {

	}

	public void configure() {
		context = new ClassPathXmlApplicationContext(configLocation);
	}

	public Logger getLogger() {
		return (Logger) context.getBean(LOGGER);
	}

	public Object getBean(Class clazz) {
		return context.getBean(clazz);
	}

	public Object getBean(String name) {
		if (context == null)
			configure();
		return context.getBean(name);
	}

}
