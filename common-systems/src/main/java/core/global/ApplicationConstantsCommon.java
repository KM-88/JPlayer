package core.global;

import framew.log.Logger;

public class ApplicationConstantsCommon {

	private static ApplicationConstants appConstant = new ApplicationConstants("Beans-Common.xml");

	public static Object getByName(String name) {
		return appConstant.getBean(name);
	}

	public static Object getByClass(Class clazz) {
		return appConstant.getBean(clazz);
	}

	public static Logger getLogger() {
		return (Logger) appConstant.getBean(ApplicationConstants.LOGGER);
	}

}
