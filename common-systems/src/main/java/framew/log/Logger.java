package framew.log;

/**
 * For now we only support single logger at one time.
 */

import java.io.Serializable;

/**
 * @author kranti
 * 
 *         Interface will expose method for logging. This may be disabled by
 *         application or a specific logger may be created with respective
 *         implementation.
 * 
 */
public interface Logger extends Serializable {

	int PRIORITY_LOWEST = 0;

	int PRIORITY_LOW = 1;

	int PRIORITY_MODERATE = 2;

	int PRIORITY_HIGH = 4;

	int PRIORITY_HIGHEST = 4;

	String OUT = "OUT";

	String ERROR = "ERROR";
	
	String DEBUG = "DEBUG";

	void log(Class className, String methodName, String message);

	void log(Class className, String methodName, String[] message);

	void log(Class className, String methodName, String message, int priority);

	void log(Class className, String methodName, String[] message, int priority);
	
	void debug(Class className, String methodName, String message);
	void debug(Class className, String methodName, String[] message);
	
	void info(Class className, String methodName, String message);
	void info(Class className, String methodName, String[] message);
	
	void error(Class className, String methodName, String message);
	void error(Class className, String methodName, String[] message);

}
