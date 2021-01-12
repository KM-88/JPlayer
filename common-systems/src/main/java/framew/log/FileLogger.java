package framew.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.Date;

@SuppressWarnings("serial")
public class FileLogger implements Logger {

	private int DEFAULT_LOG_LEVEL;

	private PrintStream output;
	private PrintStream error;
	private PrintStream debug;
	private Date date;

	public FileLogger(String outputFileName, String errorFileName, String debugFileName, int defaultLogLevel)
			throws FileNotFoundException {
		output = new PrintStream(new File(outputFileName));
		output.println("FileLogger Initialised");

		error = new PrintStream(new File(errorFileName));
		error.println("FileLogger Initialised");

		debug = new PrintStream(new File(debugFileName));
		debug.println("FileLogger Initialised");

		this.DEFAULT_LOG_LEVEL = defaultLogLevel;
	}

	public void changeDefaultLogLevel(int newDefaultLogLevel) {
		this.DEFAULT_LOG_LEVEL = newDefaultLogLevel;
	}

	@Override
	public void log(Class className, String methodName, String message) {
		date = Calendar.getInstance().getTime();
		String start = "[" + date.toString() + "] [";
		String end = "/" + this.DEFAULT_LOG_LEVEL + "] [" + className.getCanonicalName() + " : " + methodName + " ] ["
				+ message + "]";
		printLogToStream(start, end);
	}

	@Override
	public void log(Class className, String methodName, String[] message) {
		date = Calendar.getInstance().getTime();
		String start = "[" + date.toString() + "] [";
		StringBuilder stringBuilder = new StringBuilder(
				"/" + this.DEFAULT_LOG_LEVEL + "] [" + className.getCanonicalName() + " : " + methodName + " ] ");
		for (String msg : message) {
			stringBuilder.append("[").append(msg).append("]\n\t");
		}
		stringBuilder.append("]");
		printLogToStream(start, stringBuilder.toString());
	}

	@Override
	public void log(Class className, String methodName, String message, int priority) {
		try {
			if (priority <= DEFAULT_LOG_LEVEL)
				log(className, methodName, message);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void log(Class className, String methodName, String[] message, int priority) {
		if (priority <= DEFAULT_LOG_LEVEL) {
			log(className, methodName, message);
		}
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
		date = Calendar.getInstance().getTime();
		String start = "[" + date.toString() + "] [";
		String end = "/" + this.DEFAULT_LOG_LEVEL + "] [" + className.getCanonicalName() + " : " + methodName + " ] ["
				+ message + "]";
		debug.println(start + "DEBUG" + end);
	}

	@Override
	public void debug(Class className, String methodName, String[] message) {
		date = Calendar.getInstance().getTime();
		String start = "[" + date.toString() + "] [";
		StringBuilder stringBuilder = new StringBuilder(
				"/" + this.DEFAULT_LOG_LEVEL + "] [" + className.getCanonicalName() + " : " + methodName + " ] ");
		for (String msg : message) {
			stringBuilder.append("[").append(msg).append("]\n\t");
		}
		stringBuilder.append("]");
		debug.println(start + "DEBUG" + stringBuilder.toString());
	}

	@Override
	public void info(Class className, String methodName, String message) {
		date = Calendar.getInstance().getTime();
		String start = "[" + date.toString() + "] [";
		String end = "/" + this.DEFAULT_LOG_LEVEL + "] [" + className.getCanonicalName() + " : " + methodName + " ] ["
				+ message + "]";
		output.println(start + "INFO" + end);
	}

	@Override
	public void info(Class className, String methodName, String[] message) {
		date = Calendar.getInstance().getTime();
		String start = "[" + date.toString() + "] [";
		StringBuilder stringBuilder = new StringBuilder(
				"/" + this.DEFAULT_LOG_LEVEL + "] [" + className.getCanonicalName() + " : " + methodName + " ] ");
		for (String msg : message) {
			stringBuilder.append("[").append(msg).append("]\n\t");
		}
		stringBuilder.append("]");
		output.println(start + "INFO" + stringBuilder.toString());
	}

	@Override
	public void error(Class className, String methodName, String message) {
		date = Calendar.getInstance().getTime();
		String start = "[" + date.toString() + "] [";
		String end = "/" + this.DEFAULT_LOG_LEVEL + "] [" + className.getCanonicalName() + " : " + methodName + " ] ["
				+ message + "]";
		error.println(start + "ERROR" + end);
	}

	@Override
	public void error(Class className, String methodName, String[] message) {
		date = Calendar.getInstance().getTime();
		String start = "[" + date.toString() + "] [";
		StringBuilder stringBuilder = new StringBuilder(
				"/" + this.DEFAULT_LOG_LEVEL + "] [" + className.getCanonicalName() + " : " + methodName + " ] ");
		for (String msg : message) {
			stringBuilder.append("[").append(msg).append("]\n\t");
		}
		stringBuilder.append("]");
		error.println(start + "ERROR" + stringBuilder.toString());
	}
}
