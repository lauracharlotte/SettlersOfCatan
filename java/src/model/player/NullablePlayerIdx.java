package model.player;

/**
 * 
 * @author LaurasAdventurePC
 *
 */

public class NullablePlayerIdx {
	
	/**
	 * The players index, it can be null.
	 */
	PlayerIdx index;
	
	/**
	 * Constructor for the Nullable Player Idx.
	 * @param indexx
	 */
	public NullablePlayerIdx(PlayerIdx index)
	{
		this.index = index;
	}

	/**
	 * Gets the Nullable Player Idx;
	 * @return index
	 */
	public PlayerIdx getIndex() {
		return index;
	}
	/**
	 * sets the Nullable Player Idx
	 * @param index
	 */
	public void setIndex(PlayerIdx index) {
		this.index = index;
	}
}