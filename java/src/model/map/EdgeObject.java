package model.map;

import shared.locations.*;
import model.player.*;

/**
 * Represents a piece (road) on a Hex edge
 * @author Madison Brooks
 *
 */
public class EdgeObject {

	/**
	 * Location of object on map
	 */
	private EdgeLocation location;
	
	/**
	 * Index of owner in array of Players
	 */
	private PlayerIdx owner;
	
	/**
	 * Constructor for EdgeObject
	 * @param location
	 * @param owner
	 */
	public EdgeObject(EdgeLocation location, PlayerIdx owner)
	{
		this.location = location;
		this.owner = owner;
	}

	/**
	 * @return the location
	 */
	public EdgeLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(EdgeLocation location) {
		this.location = location;
	}

	/**
	 * @return the owner
	 */
	public PlayerIdx getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(PlayerIdx owner) {
		this.owner = owner;
	}

	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		str.append("Location: \n");
		str.append(location.toString());
		str.append("\nOwner: ");
		str.append(owner.toString());
		return str.toString();
	}
}
