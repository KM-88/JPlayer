package app.exception;

@SuppressWarnings("serial")
public class TaskExecutionFailedException extends BaseException {

	public TaskExecutionFailedException() {
	}

	public TaskExecutionFailedException(String message) {
		super(message);
	}

	public TaskExecutionFailedException(String message, Throwable cause) {
		super(message, cause);
	}

}
