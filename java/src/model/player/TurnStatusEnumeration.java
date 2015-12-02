package model.player;

import java.io.Serializable;

/**
 * 
 * @author LaurasAdventurePC
 *
 */

public enum TurnStatusEnumeration implements Serializable {
	/**
	 * We are rolling right now
	 */
	rolling, robbing, playing, discarding, firstround, secondround;
	
}