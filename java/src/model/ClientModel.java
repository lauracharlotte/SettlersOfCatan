package model;

import model.messages.*;
import model.map.*;
import model.player.*;
import model.cards.*;

/**
 * Model for the entire client side of the Settlers of Catan game
 * @author Madison Brooks
 *
 */
public class ClientModel
{
	/**
	 * Static instance of the ClientModel
	 */
	private static ClientModel _instance = null;
	
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
	private Player[] players;
	
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
	 * Gets the ClientModel singleton
	 * @return the instance of the ClientModel
	 */
	public static ClientModel getInstance()
	{
		if (_instance == null)
		{
			_instance = new ClientModel();
		}
		return _instance;
	}
	
	/**
	 * Private constructor for ClientModel; only called by getInstance()
	 */
	private ClientModel()
	{
		
	}
	
//	/**
//	 * Constructor for ClientModel
//	 * @param bank
//	 * @param chat
//	 * @param log
//	 * @param map
//	 * @param players
//	 * @param tradeOffer
//	 * @param turnTracker
//	 * @param version
//	 * @param winner
//	 */
//	public ClientModel(Hand bank, MessageList chat, MessageList log,
//			CatanMap map, Player[] players, TradeOffer tradeOffer,
//			TurnTracker turnTracker, int version, NullablePlayerIdx winner)
//	{
//		this.bank = bank;
//		this.chat = chat;
//		this.log = log;
//		this.map = map;
//		this.players = players;
//		this.tradeOffer = tradeOffer;
//		this.turnTracker = turnTracker;
//		this.version = version;
//		this.winner = winner;
//	}

	/**
	 * @return the bank
	 */
	public Hand getBank() {
		return bank;
	}

	/**
	 * @param bank the bank to set
	 */
	public void setBank(Hand bank) {
		this.bank = bank;
	}

	/**
	 * @return the chat
	 */
	public MessageList getChat() {
		return chat;
	}

	/**
	 * @param chat the chat to set
	 */
	public void setChat(MessageList chat) {
		this.chat = chat;
	}

	/**
	 * @return the log
	 */
	public MessageList getLog() {
		return log;
	}

	/**
	 * @param log the log to set
	 */
	public void setLog(MessageList log) {
		this.log = log;
	}

	/**
	 * @return the map
	 */
	public CatanMap getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(CatanMap map) {
		this.map = map;
	}

	/**
	 * @return the players
	 */
	public Player[] getPlayers() {
		return players;
	}

	/**
	 * @param players the players to set
	 */
	public void setPlayers(Player[] players) {
		this.players = players;
	}

	/**
	 * @return the tradeOffer
	 */
	public TradeOffer getTradeOffer() {
		return tradeOffer;
	}

	/**
	 * @param tradeOffer the tradeOffer to set
	 */
	public void setTradeOffer(TradeOffer tradeOffer) {
		this.tradeOffer = tradeOffer;
	}

	/**
	 * @return the turnTracker
	 */
	public TurnTracker getTurnTracker() {
		return turnTracker;
	}

	/**
	 * @param turnTracker the turnTracker to set
	 */
	public void setTurnTracker(TurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @return the winner
	 */
	public NullablePlayerIdx getWinner() {
		return winner;
	}

	/**
	 * @param winner the winner to set
	 */
	public void setWinner(NullablePlayerIdx winner) {
		this.winner = winner;
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
	
        

}
