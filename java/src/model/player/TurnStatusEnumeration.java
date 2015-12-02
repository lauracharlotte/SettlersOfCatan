package model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;

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