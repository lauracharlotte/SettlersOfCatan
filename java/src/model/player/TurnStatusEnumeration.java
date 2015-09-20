package model.player;

import java.util.ArrayList;
import java.util.Enumeration;

/**
 * 
 * @author LaurasAdventurePC
 *
 */

public class TurnStatusEnumeration {
	/**
	 * The enumerator that goes through each player in the list of players
	 */
	Enumeration enumerator;
	/**
	 * The array of user indexes.
	 */
	ArrayList users;

	/**
	 * Constructor for the Turn Status Enumeration
	 * @param userss
	 */
	public TurnStatusEnumeration()
	{
	}
	
	/**
	 * Enumerates through the list and returns the index of the next player.
	 * @return index of next player
	 */
	public int Enumerate()
	{
		return 0;
	}
	/**
	 * Get the enumerator
	 * @return enumerator
	 */
	public Enumeration getEnumerator() {
		return enumerator;
	}
	/**
	 * Set the enumerator
	 * @param enumerator
	 */
	public void setEnumerator(Enumeration enumerator) {
		this.enumerator = enumerator;
	}
	
}