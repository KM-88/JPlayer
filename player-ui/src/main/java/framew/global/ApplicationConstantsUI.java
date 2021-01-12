package framew.global;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import app.EventMessage;
import app.ExecutionTask;
import core.global.ApplicationConstants;
import framew.api.impl.LibraryManagerImpl;
import framew.api.infc.Library;
import framew.api.infc.LibraryManager;
import framew.bus.infc.Event;
import framew.log.Logger;
import framew.threads.api.ReferenceThread;
import framew.threads.threadgen.ExecutionTaskRegistry;
import framew.threads.threadgen.TaskEventExecutorRegistry;
import lib.impl.DataSourceImpl;
import lib.impl.LocalMusicFileSystemScannerImpl;

public class ApplicationConstantsUI {

	private static ApplicationConstants appConstantUI = new ApplicationConstants("Beans-UI.xml");

	public static final String VISUALIZATION_SERVICE = "visualizationService";

	public static final String MAIN_UI_SERVICE = "MainUIService";

	public static final String PERIODIC_EVENT_MESSAGE_GENERATION_SERVICE = "periodicEventMessageGenerationService";

	public static final String EVENT_MESSAGE_EXECUTION_SERVICE = "eventMessageExecutionService";

	public static final String MEDIA_COLLECTION_PATH = "mediaCollectionsPath";

	public static final String LOGGER = "logger";

	public static final String DATA_SOURCE = "dataSource";

	private static final Map<String, Object> resourceMap = new HashMap<>();

	private static Path DEFAULT_LIBRARY_PATH;

	private static final String DEFAULT_LIBRARY_NAME = "Hindi";

	public static final Event PLAYING_EVENT = new EventMessage("PLAYING_EVENT");

	public static final String INTERNAL_APP_CACHE = "internal_app_cache";

	public static final String LIBRARY_MANAGER = "libraryManager";

	public static final String FILE_SYSTEM_SCANNER = "LocalMusicFileSystemScanner";

	public static void init() {
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0)
			DEFAULT_LIBRARY_PATH = Paths.get("\\192.168.0.104\\Personal\\Hindi");
		else
			DEFAULT_LIBRARY_PATH = Paths.get("/samba/Personal/Hindi");
		List<Path> mediaPath = new LinkedList<>();
		resourceMap.put(MEDIA_COLLECTION_PATH, mediaPath);
		resourceMap.put(DATA_SOURCE, new DataSourceImpl());
	}

	public static boolean registerNewEntity(String key, Object value, boolean overWrite) {
		if (null == resourceMap.get(key) || overWrite) {
			resourceMap.put(key, value);
			return true;
		} else
			return false;
	}

	public static Object getByName(String name) {
		return appConstantUI.getBean(name);
	}

	public static void createThreadResources(Map<String, Boolean> servicesStatus) {
	}

	public static Object getBean(String name) {
		return appConstantUI.getBean(name);
	}

	public static Logger getLogger() {
		return (Logger) appConstantUI.getBean(ApplicationConstants.LOGGER);

	}

	private static void registerTasks(List<ExecutionTask> taskInventory) {
		for (ExecutionTask task : taskInventory) {
			TaskEventExecutorRegistry.registerTaskEventPair(task.getEvent(), (Class<ExecutionTask>) task.getClass(),
					false);
			ExecutionTaskRegistry.registerExecutionTask(task);
		}
	}

}
