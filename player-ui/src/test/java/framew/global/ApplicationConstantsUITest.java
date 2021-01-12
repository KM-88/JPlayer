package framew.global;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import core.global.ApplicationConstants;

public class ApplicationConstantsUITest {

	private static ApplicationConstants appConstantTest = new ApplicationConstants("Beans-UI-test.xml");

	@Test
	public void getBeanTest() {
		String[] beanNames = { "logger", "logFileOption", "libDirOption", "bufferedImage", "cacheExecutionEvent",
				"libraryExecutionEvent", "periodicViewUpdateExecutionEvent", "eventMessageMap", "cacheExecutionTask",
				"libraryExecutionTask", "periodicViewUpdateExecutionTask", "taskInventory",
				"periodicEventMessageGenerationThread", "eventMessageExecutionerDaemonThread",
				"nativeCacheCLINonDaemonThread", "threadMap", "threadStartupTimestamp", "defaultViewComponent",
				"playListPanel", "visualizationPanel", "visualizationNonDaemonThread", "VISUALIZATION_SERVICE",
				"mainUINonDaemonThread" };
		for (String str : beanNames) {
			Assertions.assertNotNull(appConstantTest.getBean(str));
			System.out.println(appConstantTest.getBean(str));
		}
	}

	public static Object getByName(String name) {
		
		return appConstantTest.getBean(name);
	}
}