package model.map;

import shared.locations.*;

import java.io.Serializable;

import model.player.*;

/**
 * Represents a piece (a city or a settlement) located on a vertex of the 
 * CatanMap
 * @author Madison Brooks
 *
 */
@SuppressWarnings("serial")
public class VertexObject implements Serializable 
{

	/**
	 * Location of the piece
	 */
	private VertexLocation location;
	
	/**
	 * Index of the piece's owner
	 */
	private PlayerIdx owner;
	
	/**
	 * Constructor for VertexObject
	 * @param location
	 * @param owner
	 */
	public VertexObject(VertexLocation location, PlayerIdx owner)
	{
		this.location = location;
		this.owner = owner;
	}

	/**
	 * @return the location
	 */
	public VertexLocation getLocation() 
	{
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(VertexLocation location) 
	{
		this.location = location;
	}

	/**
	 * @return the owner
	 */
	public PlayerIdx getOwner() 
	{
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	public void setOwner(PlayerIdx owner) 
	{
		this.owner = owner;
	}
	
}
