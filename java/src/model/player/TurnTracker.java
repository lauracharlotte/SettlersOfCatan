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
	private String status;
	/**
	 * The index of the player who currently holds the largest road.
	 */
	private PlayerIdx longestRoad;
	/**
	 * The index of the player who currently holds the largest army.
	 */
	private PlayerIdx largestArmy;
	
	/**
	 * Constructor for the TurnTracker
	 * @param currentTurnn
	 * @param statuss
	 * @param longestRoadd
	 * @param largestArmyy
	 */
	public TurnTracker(PlayerIdx currentTurnn, String statuss, PlayerIdx longestRoadd, PlayerIdx largestArmyy)
	{
		currentTurn = currentTurnn;
		status = statuss;
		longestRoad = longestRoadd;
		largestArmy = largestArmyy;
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
	public String getStatus() {
		return status;
	}
	/**
	 * Sets the current Status.
	 * @param status
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * Gets who has the longest road.
	 * @return longestRoad
	 */
	public PlayerIdx getLongestRoad() {
		return longestRoad;
	}
	/**
	 * Sets which player has the longest road
	 * @param longestRoad
	 */
	public void setLongestRoad(PlayerIdx longestRoad) {
		this.longestRoad = longestRoad;
	}
	/**
	 * Gets which player has the largest army
	 * @return largestArmy
	 */
	public PlayerIdx getLargestArmy() {
		return largestArmy;
	}
	/**
	 * Sets which player has the largest army
	 * @param largestArmy
	 */
	public void setLargestArmy(PlayerIdx largestArmy) {
		this.largestArmy = largestArmy;
	}
}