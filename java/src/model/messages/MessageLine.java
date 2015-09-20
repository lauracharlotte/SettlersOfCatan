package model.messages;

/**
 * Holds the content and source of a message line
 * @author Madison Brooks
 *
 */
public class MessageLine {

	/**
	 * Message content
	 */
	private String message;
	
	/**
	 * Source of message
	 */
	private String source;
	
	/**
	 * Constructor for MessageLine
	 * @param message
	 * @param source
	 */
	public MessageLine(String message, String source)
	{
		this.message = message;
		this.source = source;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
}
