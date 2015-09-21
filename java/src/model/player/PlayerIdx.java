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
	int index;
	
	/**
	 * The Constructor for the PlayerIdx
	 * @param index
	 */
	public PlayerIdx(int index)
	{
		this.index = index;
	}

	/**
	 * Gets the Player's Idx
	 * @return playerIdx
	 */
	public int getPlayerIdx() {
		return index;
	}
	/**
	 * Sets the Player's Idx
	 * @param playerIdx
	 */
	public void setPlayerIdx(int index) {
		this.index = index;
	}

}