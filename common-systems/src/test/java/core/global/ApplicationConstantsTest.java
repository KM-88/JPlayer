package core.global;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import framew.log.Logger;

public class ApplicationConstantsTest {

	private static ApplicationConstants appConstantTest = new ApplicationConstants("Beans-Common-test.xml");

	public static Object getByName(String name) {
		return appConstantTest.getBean(name);
	}
	
	public static Object getByClass(Class clazz) {
		return appConstantTest.getBean(clazz);
	}
	
	public static Logger getLogger() {
		return (Logger) appConstantTest.getLogger();
	}

	@Test
	public void configTest() {
		Assertions.assertNotNull(getLogger());
		getLogger().debug(getClass(), "configTest()", "Is logging working ?");
	}

}
