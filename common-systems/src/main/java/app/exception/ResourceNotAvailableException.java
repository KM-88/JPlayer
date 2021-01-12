package app.exception;

@SuppressWarnings("serial")
public class ResourceNotAvailableException extends BaseException {

	public ResourceNotAvailableException(String message) {
		super(message);
	}

	public ResourceNotAvailableException(String message, Throwable cause) {
		super(message, cause);
	}

}
