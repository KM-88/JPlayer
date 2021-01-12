package framew.log;

import java.io.PrintStream;

public class ConsoleLogger implements Logger {

	private PrintStream output = System.out;
	private PrintStream error = System.err;
	private PrintStream debug = output;

	private int DEFAULT_LOG_LEVEL = 6;

	private static final long serialVersionUID = 1L;

	public ConsoleLogger() {
		output = System.out;
		error = System.err;
		debug = output;
		output.println("ConsoleLogger - OUTPUT Stream - Initialised");
		error.println("ConsoleLogger - ERROR Stream -Initialised");
		debug.println("ConsoleLogger - DEBUG Stream -Initialised");
	}

	public ConsoleLogger(PrintStream output, PrintStream error, PrintStream debug) {
		this.output = output;
		this.error = error;
		this.debug = debug;
		output.println("ConsoleLogger - OUTPUT Stream - Initialised");
		error.println("ConsoleLogger - ERROR Stream -Initialised");
		debug.println("ConsoleLogger - DEBUG Stream -Initialised");
	}

	@Override
	public void log(Class className, String methodName, String message) {
		log(className, methodName, message, PRIORITY_MODERATE);
	}

	@Override
	public void log(Class className, String methodName, String[] message) {
		log(className, methodName, message, PRIORITY_MODERATE);
	}

	@Override
	public void log(Class className, String methodName, String message, int priority) {
		String start = "[" + className.getCanonicalName() + " : " + methodName + " ]---" + priority + " [";
		String end = "]---[" + message + "]";
		printLogToStream(start, end);
	}

	@Override
	public void log(Class className, String methodName, String[] message, int priority) {
		String start = "[" + className.getCanonicalName() + " : " + methodName + " ]---" + priority + " [";
		StringBuilder stringBuilder = new StringBuilder("] ");
		for (String msg : message) {
			stringBuilder.append("[").append(msg).append("]\n\t");
		}
		stringBuilder.append("]");
		printLogToStream(start, stringBuilder.toString());
	}

	private void printLogToStream(String start, String end) {
		output.println(start + "INFO" + end);
		debug.println(start + "DEBUG" + end);
		error.println(start + "ERROR" + end);
		flushAllStream();
	}

	private void flushAllStream() {
		output.flush();
		error.flush();
		debug.flush();
	}

	@Override
	public void debug(Class className, String methodName, String message) {
		String start = "[" + className.getCanonicalName() + " : " + methodName + " ]---" + this.DEFAULT_LOG_LEVEL
				+ " [";
		String end = "]---[" + message + "]";
		debug.println(start + "DEBUG" + end);
	}

	@Override
	public void debug(Class className, String methodName, String[] message) {
		String start = "[" + className.getCanonicalName() + " : " + methodName + " ]---" + this.DEFAULT_LOG_LEVEL
				+ " [";
		StringBuilder stringBuilder = new StringBuilder("] ");
		for (String msg : message) {
			stringBuilder.append("[").append(msg).append("]\n\t");
		}
		stringBuilder.append("]");
		debug.println(start + "DEBUG" + stringBuilder.toString());
	}

	@Override
	public void info(Class className, String methodName, String message) {
		String start = "[" + className.getCanonicalName() + " : " + methodName + " ]---" + this.DEFAULT_LOG_LEVEL
				+ " [";
		String end = "]---[" + message + "]";
		output.println(start + "INFO" + end);
	}

	@Override
	public void info(Class className, String methodName, String[] message) {
		String start = "[" + className.getCanonicalName() + " : " + methodName + " ]---" + this.DEFAULT_LOG_LEVEL
				+ " [";
		StringBuilder stringBuilder = new StringBuilder("] ");
		for (String msg : message) {
			stringBuilder.append("[").append(msg).append("]\n\t");
		}
		stringBuilder.append("]");
		output.println(start + "INFO" + stringBuilder.toString());
	}

	@Override
	public void error(Class className, String methodName, String message) {
		String start = "[" + className.getCanonicalName() + " : " + methodName + " ]---" + this.DEFAULT_LOG_LEVEL
				+ " [";
		String end = "]---[" + message + "]";
		error.println(start + "ERROR" + end);
	}

	@Override
	public void error(Class className, String methodName, String[] message) {
		String start = "[" + className.getCanonicalName() + " : " + methodName + " ]---" + this.DEFAULT_LOG_LEVEL
				+ " [";
		StringBuilder stringBuilder = new StringBuilder("] ");
		for (String msg : message) {
			stringBuilder.append("[").append(msg).append("]\n\t");
		}
		stringBuilder.append("]");
		error.println(start + "ERROR" + stringBuilder.toString());
	}

}
