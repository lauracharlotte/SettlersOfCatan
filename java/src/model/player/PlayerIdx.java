package model.player;

/**
 * 
 * @author LaurasAdventurePC
 *
 */

public class PlayerIdx {
	/**
	 * The players index, a number 0-3.
	 */
	int playerIndex;
	
	/**
	 * The Constructor for the PlayerIdx
	 * @param index
	 */
	public PlayerIdx(int playerIndex)
	{
		this.playerIndex = playerIndex;
	}

	/**
	 * Gets the Player's Idx
	 * @return playerIdx
	 */
	public int getPlayerIdx() {
		return playerIndex;
	}
	/**
	 * Sets the Player's Idx
	 * @param playerIdx
	 */
	public void setPlayerIdx(int playerIndex) {
		this.playerIndex = playerIndex;
	}

}