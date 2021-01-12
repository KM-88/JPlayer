package app.exception;

@SuppressWarnings("serial")
public class EventListnerGroupAlreadyRegisteredException extends BaseException {

	public EventListnerGroupAlreadyRegisteredException(String message) {
		super(message);
	}

	public EventListnerGroupAlreadyRegisteredException(String message, Throwable cause) {
		super(message, cause);
	}

}
