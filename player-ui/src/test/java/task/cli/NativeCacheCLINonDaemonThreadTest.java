package task.cli;

import org.junit.jupiter.api.Test;

import core.global.ApplicationConstants;
import framew.global.ApplicationConstantsUITest;
import framew.log.Logger;

public class NativeCacheCLINonDaemonThreadTest {

	@Test
	public void initTest() {
		Logger log = (Logger) ApplicationConstantsUITest.getByName(ApplicationConstants.LOGGER);
		String[] arr = { "list", "play", "cd", "CLI_OPTIONS", "libraryManager" };
		for (String str : arr)
			log.debug(getClass(), "init", str + ApplicationConstantsUITest.getByName(str).toString());
	}
}
