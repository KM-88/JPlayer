package core.global;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import framew.log.Logger;

public class ApplicationConstantsFrameworkTest {

	private static ApplicationConstants appConstant = new ApplicationConstants("Beans-Framework-test.xml");
	public static final String INTERNAL_APP_CACHE = "internal_app_cache";
	public static final String DATA_SOURCE = "dataSource";
	public static final String FILE_SYSTEM_SCANNER = "LocalMusicFileSystemScanner";
	public static final String MEDIA_COLLECTION_PATH = "mediaCollectionsPath";

	public static Object getByName(String name) {
		return appConstant.getBean(name);
	}

	public static Object getByClass(Class clazz) {
		return appConstant.getBean(clazz);
	}

	public static Logger getLogger() {
		return (Logger) appConstant.getBean(ApplicationConstants.LOGGER);
	}

	@Test
	public void getByNameTest() {
		String methodName = "getByNameTest()";
		String[] beans = { "LocalMusicFileSystemScanner", ApplicationConstants.LOGGER,
				ApplicationConstantsFramework.INTERNAL_APP_CACHE, ApplicationConstantsFramework.MEDIA_COLLECTION_PATH };
		Logger logger = ApplicationConstantsFrameworkTest.getLogger();
		Assertions.assertNotNull(logger);
		for (String bean : beans) {
			Assertions.assertNotNull(ApplicationConstantsFrameworkTest.getByName(bean));
			logger.debug(getClass(), methodName, ApplicationConstantsFrameworkTest.getByName(bean).toString());
		}

	}
}
