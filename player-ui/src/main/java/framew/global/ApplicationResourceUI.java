package framew.global;

import java.util.Map;

import core.global.ApplicationResourceFramework;
import framew.log.Logger;
import framew.threads.api.NonDaemonThread;
import framew.threads.threadgen.ThreadGeneratorInit;

public class ApplicationResourceUI extends ApplicationResourceFramework {

	private static Logger logger = ApplicationConstantsUI.getLogger();

	public static void init(String[] argsEval) {
		logger.debug(ApplicationConstantsUI.class, "init", "Starting Threads");
		startThreads();
	}

	private static void startThreads() {
		Map<String, NonDaemonThread> threadMap = (Map<String, NonDaemonThread>) ApplicationConstantsUI
				.getBean("threadMap");
		Map<String, Integer> threadStartuptimestamp = (Map<String, Integer>) ApplicationConstantsUI
				.getBean("threadStartupTimestamp");
		ThreadGeneratorInit.setThreadMap(threadMap);
		ThreadGeneratorInit.setThreadStartupTimestamp(threadStartuptimestamp);
		ThreadGeneratorInit.startThreads();
	}

}
