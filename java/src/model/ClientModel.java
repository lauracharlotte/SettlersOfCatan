package model;

import model.messages.*;
import model.map.*;
import model.player.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import model.cards.*;

/**
 * Model for the entire client side of the Settlers of Catan game
 * @author Madison Brooks
 *
 */
@SuppressWarnings("serial")
public class ClientModel implements Serializable
{	
	/**
	 * Title of game
	 */
	private String title;
	
	/**
	 * Bank of unused Resource and Development cards
	 */
	private Hand bank;
	
	/**
	 * List of chat messages
	 */
	private MessageList chat;
	
	/**
	 * List of log messages
	 */
	private MessageList log;
	
	/**
	 * Catan map, including hex positioning and pieces
	 */
	private CatanMap map;
	
	/**
	 * List of players (at least 2)
	 */
	private Collection<Player> players;
	
	/**
	 * Current trade offer; may be null
	 */
	private TradeOffer tradeOffer;
	
	/**
	 * Player turn tracker
	 */
	private TurnTracker turnTracker;
	
	/**
	 * Version of current model
	 */
	private int version;
	
	/**
	 * Winner of the game; may be null
	 */
	private NullablePlayerIdx winner;

	
	/**
	 * Constructor for ClientModel
	 * @param title
	 * @param bank
	 * @param chat
	 * @param log
	 * @param map
	 * @param players
	 * @param tradeOffer
	 * @param turnTracker
	 * @param version
	 * @param winner
	 */
	public ClientModel(String title, Hand bank, MessageList chat, MessageList log,
			CatanMap map, Collection<Player> players, TradeOffer tradeOffer,
			TurnTracker turnTracker, int version, NullablePlayerIdx winner)
	{
		this.title = title;
		this.bank = bank;
		this.chat = chat;
		this.log = log;
		this.map = map;
		this.players = players;
		this.tradeOffer = tradeOffer;
		this.turnTracker = turnTracker;
		this.version = version;
		this.winner = winner;
	}
	
	/**
	 * Creates a default new game 
	 * @param randomTiles
	 * @param randomNumbers
	 * @param randomPorts
	 */
	public ClientModel(boolean randomTiles, boolean randomNumbers, boolean randomPorts, String title)
	{
		this.title = title;
		this.bank = new Hand(true);
		this.chat = new MessageList(new ArrayList<MessageLine>());
		this.log = new MessageList(new ArrayList<MessageLine>());
		this.map = new CatanMap(randomTiles, randomNumbers, randomPorts);
		this.players = new ArrayList<Player>();
		this.turnTracker = new TurnTracker();
		this.version = 0;
		this.winner = new NullablePlayerIdx(-1);
	}

        public ClientModel() //only should be used for testing
        {
            
        }
        
	/**
	 * @return the bank
	 */
	public Hand getBank() 
	{
		return bank;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(Hand bank) 
	{
		this.bank = bank;
	}

	/**
	 * @return the chat
	 */
	public MessageList getChat() 
	{
		return chat;
	}

	/**
	 * @param chat the chat to set
	 */
	public void setChat(MessageList chat) 
	{
		this.chat = chat;
	}

	/**
	 * @return the log
	 */
	public MessageList getLog() 
	{
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(MessageList log) 
	{
		this.log = log;
	}

	/**
	 * @return the map
	 */
	public CatanMap getMap() 
	{
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(CatanMap map) 
	{
		this.map = map;
	}

	/**
	 * @return the players
	 */
	public Collection<Player> getPlayers() 
	{
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(Collection<Player> players) 
	{
		this.players = players;
	}

	/**
	 * @return the tradeOffer
	 */
	public TradeOffer getTradeOffer() 
	{
		return tradeOffer;
	}

	/**
	 * @param tradeOffer the tradeOffer to set
	 */
	public void setTradeOffer(TradeOffer tradeOffer) 
	{
		this.tradeOffer = tradeOffer;
	}

	/**
	 * @return the turnTracker
	 */
	public TurnTracker getTurnTracker() 
	{
		return turnTracker;
	}

	/**
	 * @param turnTracker the turnTracker to set
	 */
	public void setTurnTracker(TurnTracker turnTracker) 
	{
		this.turnTracker = turnTracker;
	}

	/**
	 * @return the version
	 */
	public int getVersion() 
	{
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) 
	{
		this.version = version;
	}

	/**
	 * @return the winner
	 */
	public NullablePlayerIdx getWinner() 
	{
		return winner;
	}

	/**
	 * @param winner the winner to set
	 */
	public void setWinner(NullablePlayerIdx winner) 
	{
		this.winner = winner;
	}

        /**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

		@Override
        public int hashCode()
        {
            int hash = 7;
            hash = 67 * hash + this.version;
            return hash;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj == null)
            {
                return false;
            }
            if (getClass() != obj.getClass())
            {
                return false;
            }
            final ClientModel other = (ClientModel) obj;
            return this.version == other.version;
        }
	
        @Override
        public String toString()
        {
        	StringBuilder str = new StringBuilder();
        	str.append("ClientModel, version ");
        	str.append(version);
        	str.append(":\n\nBank:\n");
        	str.append(bank.toString());
        	str.append("\n\nChat:\n");
        	str.append(chat.toString());
        	str.append("\n\nLog:\n");
        	str.append(log.toString());
        	str.append("\n\n");
        	str.append(map.toString());
        	str.append("\n\nList of Players:\n");
        	for (int i = 0; i < players.size(); i++)
        	{
        		str.append(((ArrayList<Player>) players).get(i).toString());
        		str.append("\n\n");
        	}
        	if (tradeOffer != null)
        	{
        		str.append(tradeOffer.toString());
        	}
        	str.append("\n\n");
        	str.append(turnTracker.toString());
        	str.append("\n\nWinner:");
        	str.append(winner.toString());
        	return str.toString();
        }

}
