package core.global;

import framew.log.Logger;

public class ApplicationConstantsFramework {

	private static ApplicationConstants appConstant = new ApplicationConstants("Beans-Framework.xml");
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

}
