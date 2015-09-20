package model.messages;

/**
 * Holds a list of MessageLines
 * @author Madison Brooks
 *
 */
public class MessageList {

	/**
	 * All messages in list
	 */
	private MessageLine[] lines;
	
	/**
	 * Constructor for MessageList
	 * @param lines
	 */
	public MessageList(MessageLine[] lines)
	{
		this.lines = lines;
	}

	/**
	 * @return the lines
	 */
	public MessageLine[] getLines() {
		return lines;
	}

	/**
	 * @param lines the lines to set
	 */
	public void setLines(MessageLine[] lines) {
		this.lines = lines;
	}
	
}
