package app.exception;

@SuppressWarnings("serial")
public class ResourceDataFailureException extends BaseException {

	public ResourceDataFailureException(String message) {
		super(message);
	}

	public ResourceDataFailureException(String message, Throwable cause) {
		super(message, cause);
	}

}