package model.map;

import shared.locations.*;
import shared.definitions.*;

/**
 * Represents a hex on the CatanMap
 * @author Madison Brooks
 *
 */
public class Hex {

	/**
	 * Location (set of coordinates) of the Hex on the CatanMap
	 */
	private HexLocation location;
	
	/**
	 * Type of hex (type of resource, desert, or water)
	 */
	private HexType type;
	
	/**
	 * Number/chit on the hex; -1 if no number
	 */
	private int number;
	
	/**
	 * Constructor for Hex
	 * @param location
	 * @param type
	 * @param number
	 */
	public Hex(HexLocation location, HexType type, int number)
	{
		this.location = location;
		this.type = type;
		this.number = number;
	}

	/**
	 * @return the location
	 */
	public HexLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(HexLocation location) {
		this.location = location;
	}

	/**
	 * @return the type
	 */
	public HexType getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(HexType type) {
		this.type = type;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}
	
}
