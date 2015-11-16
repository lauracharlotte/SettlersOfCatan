package model.player;

/**
 * 
 * @author LaurasAdventurePC
 *
 */

public class TurnTracker {
	/**
	 * The index of the player whoom the current turn is on.
	 */
	private PlayerIdx currentTurn;
	/**
	 * The current status of the game.
	 */
	private TurnStatusEnumeration status;
	/**
	 * The index of the player who currently holds the largest road.
	 */
	private NullablePlayerIdx longestRoad;
	/**
	 * The index of the player who currently holds the largest army.
	 */
	private NullablePlayerIdx largestArmy;
	
	
	/**
	 * Constructor for the TurnTracker
	 * @param currentTurnn
	 * @param statuss
	 * @param longestRoadd
	 * @param largestArmyy
	 */
	public TurnTracker(PlayerIdx currentTurn, TurnStatusEnumeration status, NullablePlayerIdx longestRoad, NullablePlayerIdx largestArmy)
	{
		this.currentTurn = currentTurn;
		this.status = status;
		this.longestRoad = longestRoad;
		this.largestArmy = largestArmy;
	}
	
	/**
	 * Default TurnTracker constructor
	 */
	public TurnTracker()
	{
		currentTurn = new PlayerIdx(0);
		status = TurnStatusEnumeration.firstround;
		longestRoad = new NullablePlayerIdx(-1);
		largestArmy = new NullablePlayerIdx(-1);
	}
	
	/**
	 * Gets whos turn it currently is.
	 * @return currentTurn
	 */
	public PlayerIdx getCurrentTurn() {
		return currentTurn;
	}
	/**
	 * Sets who turn it currently is or should be
	 * @param currentTurn
	 */
	public void setCurrentTurn(PlayerIdx currentTurn) {
		this.currentTurn = currentTurn;
	}
	/**
	 * Gets the current Status.
	 * @return status
	 */
	public TurnStatusEnumeration getStatus() {
		return status;
	}
	/**
	 * Sets the current Status.
	 * @param status
	 */
	public void setStatus(TurnStatusEnumeration status) {
		this.status = status;
	}
	/**
	 * Gets who has the longest road.
	 * @return index of player with longestRoad
	 */
	public NullablePlayerIdx getLongestRoad() {
		return longestRoad;
	}
	/**
	 * Sets which player has the longest road
	 * @param index of new player with longestRoad
	 */
	public void setLongestRoad(NullablePlayerIdx longestRoad) {
		this.longestRoad = longestRoad;
	}
	/**
	 * Gets which player has the largest army
	 * @return index of player with largestArmy
	 */
	public NullablePlayerIdx getLargestArmy() {
		return largestArmy;
	}
	/**
	 * Sets which player has the largest army
	 * @param index of new player with largestArmy
	 */
	public void setLargestArmy(NullablePlayerIdx largestArmy) {
		this.largestArmy = largestArmy;
	}
	
	@Override 
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append("TurnTracker:\nCurrent turn: ");
		str.append(currentTurn.toString());
		str.append("\nStatus: ");
		str.append(status.toString());
		str.append("\nLongestRoad: ");
		str.append(longestRoad.toString());
		str.append("\nLargest Army: ");
		str.append(largestArmy.toString());
		return str.toString();
	}
}