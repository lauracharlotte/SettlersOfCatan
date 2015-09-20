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
	int playerIdx;
	
	/**
	 * The Constructor for the PlayerIdx
	 * @param index
	 */
	public PlayerIdx(int index)
	{
		playerIdx = index;
	}

	/**
	 * Gets the Player's Idx
	 * @return playerIdx
	 */
	public int getPlayerIdx() {
		return playerIdx;
	}
	/**
	 * Sets the Player's Idx
	 * @param playerIdx
	 */
	public void setPlayerIdx(int playerIdx) {
		this.playerIdx = playerIdx;
	}

}