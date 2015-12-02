package model.messages;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Holds a list of MessageLines
 * @author Madison Brooks
 *
 */
@SuppressWarnings("serial")
public class MessageList implements Serializable 
{

	/**
	 * All messages in list
	 */
	private Collection<MessageLine> lines;
	
	/**
	 * Constructor for MessageList
	 * @param lines
	 */
	public MessageList(Collection<MessageLine> lines)
	{
		this.lines = lines;
	}

	/**
	 * @return the lines
	 */
	public Collection<MessageLine> getLines() 
	{
		return lines;
	}

	/**
	 * @param lines the lines to set
	 */
	public void setLines(Collection<MessageLine> lines) 
	{
		this.lines = lines;
	}
	
	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < lines.size(); i++)
		{
			str.append(((ArrayList<MessageLine>)lines).get(i).toString());
			str.append("\n");
		}
		return str.toString();
	}
	
}
