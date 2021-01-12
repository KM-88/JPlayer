package framew.log;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import core.global.ApplicationConstantsTest;

public class ConsoleLoggerTest {
	
	private Logger log = ApplicationConstantsTest.getLogger();

	@Test
	public void consoleLoggingTest() {

		log.debug(getClass(), "FileLoggingTest", "Test Debug Message");
		log.info(getClass(), "FileLoggingTest", "Test Info Message");
		log.error(getClass(), "FileLoggingTest", "Test Error Message");
		log.log(getClass(), "FileLoggingTest", "Test Log Message");
		Assertions.assertTrue(true);
	}
}
