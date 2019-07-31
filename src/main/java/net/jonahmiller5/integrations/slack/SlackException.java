package net.jonahmiller5.integrations.slack;

public class SlackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SlackException(Throwable cause) {
		super(cause);
	}
}
