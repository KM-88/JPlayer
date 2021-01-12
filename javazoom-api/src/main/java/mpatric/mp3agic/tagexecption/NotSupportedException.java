package mpatric.mp3agic.tagexecption;

import app.exception.BaseException;

public class NotSupportedException extends BaseException {

	private static final long serialVersionUID = 1L;

	public NotSupportedException() {
		super();
	}

	public NotSupportedException(String message) {
		super(message);
	}

	public NotSupportedException(String message, Throwable cause) {
		super(message, cause);
	}
}