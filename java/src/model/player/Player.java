package model.player;

import model.cards.ResourceCardList;
import shared.definitions.CatanColor;
import model.cards.Hand;

/**
 * 
 * @author LaurasAdventurePC
 *
 */
public class Player {

	/**
	 * The number of cities a player has left.
	 */
	private int cities;
	/**
	 * The player's piece color on the board.
	 */
	private CatanColor color;
	/**.
	 * Tells whether a player has discarded their cards or not
	 */
	private Boolean discarded;
	/**
	 * How many monuments a player has.
	 */
	private int monuments;
	/**
	 * The name of a player.
	 */
	private String name;
	/**
	 * The player's index.
	 */
	private PlayerIdx playerIndex;
	/**
	 * Whether or not the player had played a development card yet on their turn.
	 */
	private Boolean playedDevCard;
	/**
	 * The player's ID.
	 */
	private int playerId;
	/**
	 * The number of roads the player has left.
	 */
	private int roads;
	/**
	 * The number of settlements the player has left.
	 */
	private int settlements;
	/**
	 * The number of soldiers the player has.
	 */
	private int soldiers;
	/**
	 * The number of victory points the player has.
	 */
	private int victoryPoints;
	/**
	 * The hand the player has (hand contains the list of development cards and resource cards.
	 */
	private Hand hand;
	
	/**
	 * Constructor for player.
	 * @param citiess
	 * @param colorr
	 * @param discardedd
	 * @param monumentss
	 * @param namee
	 * @param playerIndexx
	 * @param playedDevCardd
	 * @param playerIdd
	 * @param roadss
	 * @param settlementss
	 * @param soldierss
	 * @param victoryPointss
	 * @param handd
	 */
	public Player(int cities, CatanColor color, Boolean discarded, int monuments, String name, PlayerIdx playerIndex,
				  Boolean playedDevCard, int playerId, int roads, int settlements, int soldiers, int victoryPoints, Hand hand)
	{
		this.cities = cities;
		this.color = color;
		this.discarded = discarded;
		this.monuments = monuments;
		this.name = name;
		this.playerIndex = playerIndex;
		this.playedDevCard = playedDevCard;
		this.playerId = playerId;
		this.roads = roads;
		this.settlements = settlements;
		this.soldiers = soldiers;
		this.victoryPoints = victoryPoints;
		this.hand = hand;
	}

	/**
	 * Checks if the player has the: resource and number of objects left to build a road, city, settlement, or  buy card
	 * @return
	 */
	public Boolean canBuy(String type)
	{
		return null;
	}
	
	/**
	 * Does the Player have the recourses asked for to trade?
	 * @return
	 */
	public Boolean canTrade(ResourceCardList cardsToTrade)
	{
		return null;
	}
	
	/**
	 * Gets the number of cities left
	 * @return cities
	 */
	public int getCities() 
	{
		return cities;
	}
	/**
	 * Sets the number of cities left
	 * @param cities
	 */
	public void setCities(int cities) 
	{
		this.cities = cities;
	}
	/**
	 * Gets the players current color.
	 * @return color
	 */
	public CatanColor getColor() 
	{
		return color;
	}
	/**
	 * Sets the players current color.
	 * @param color
	 */
	public void setColor(CatanColor color) 
	{
		this.color = color;
	}
	/**
	 * Gets whether the player has discarded a card that turn.
	 * @return discarded
	 */
	public Boolean getDiscarded() 
	{
		return discarded;
	}
	/**
	 * Sets whether the player has discarded a card that turn
	 * @param discarded
	 */
	public void setDiscarded(Boolean discarded) 
	{
		this.discarded = discarded;
	}
	/**
	 * Get the number of monuments.
	 * @return monuments
	 */
	public int getMonuments() 
	{
		return monuments;
	}
	/**
	 * Sets the number of monuments
	 * @param monuments
	 */
	public void setMonuments(int monuments) 
	{
		this.monuments = monuments;
	}
	/**
	 * Gets the name of the player.
	 * @return name
	 */
	public String getName() 
	{
		return name;
	}
	/**
	 * Sets the name of the player.
	 * @param name
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * Gets the player's index.
	 * @return
	 */
	public PlayerIdx getPlayerIndex() 
	{
		return playerIndex;
	}
	/**
	 * Sets the player's index.
	 * @param playerIndex
	 */
	public void setPlayerIndex(PlayerIdx playerIndex) 
	{
		this.playerIndex = playerIndex;
	}
	/**
	 * Gets the player's ID.
	 * @return playerId
	 */
	public int getPlayerId() 
	{
		return playerId;
	}
	/**
	 * Sets the Player's Id
	 * @param playerId
	 */
	public void setPlayerId(int playerId) 
	{
		this.playerId = playerId;
	}
	/**
	 * Gets the number of roads the player has left.
	 * @return roads
	 */
	public int getRoads() 
	{
		return roads;
	}
	/**
	 * Sets the number of roads the player has left.
	 * @param roads
	 */
	public void setRoads(int roads) 
	{
		this.roads = roads;
	}
	/**
	 * Gets the number of settlements the player has left.
	 * @return settlements
	 */
	public int getSettlements() 
	{
		return settlements;
	}
	/**
	 * Sets the number of settlements the player has.
	 * @param settlements
	 */
	public void setSettlements(int settlements) 
	{
		this.settlements = settlements;
	}
	/**
	 * Gets the number of soldiers.
	 * @return soldiers
	 */
	public int getSoldiers() 
	{
		return soldiers;
	}
	/**
	 * Sets the number of soldiers.
	 * @param soldiers
	 */
	public void setSoldiers(int soldiers) 
	{
		this.soldiers = soldiers;
	}
	/**
	 * Gets the number of victory points.
	 * @return victoryPoints.
	 */
	public int getVictoryPoints() 
	{
		return victoryPoints;
	}
	/**
	 * Sets the number of victory points.
	 * @param victoryPoints
	 */
	public void setVictoryPoints(int victoryPoints) 
	{
		this.victoryPoints = victoryPoints;
	}

	
}
