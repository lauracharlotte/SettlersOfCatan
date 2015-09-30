/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 *
 * @author Michael
 */
public class BuildCityRequest implements IJSONSerializable
{

    /**
     *
     * @param playerIndex The index of the player that wants to build the city
     * @param location The vertex location that the player wants to put the city at
     */
    public BuildCityRequest(PlayerIdx playerIndex, VertexLocation location)
    {
        this.playerIndex = playerIndex;
        this.location = location;
    }

    /**
     *
     * @return The index of the player that wants to build the city
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return The vertex location that the player wants to put the city at
     */
    public VertexLocation getLocation()
    {
        return location;
    }
    
    private PlayerIdx playerIndex;
    private VertexLocation location;

    @Override
    public String serialize()//400
    {
    	String theDirection = location.getDir().toString();
    	Abbreviate abrev = new Abbreviate();
    	theDirection = abrev.abbreviate(theDirection);
    	
    	String serializing = "{type: \"buildCity\", playerIndex: "
    						+ playerIndex.getPlayerIdx() + ", vertexLocation: {" 
    						+ "x: " + location.getHexLoc().getX() + ", y: "+ location.getHexLoc().getY()
    						+ ", direction: \""+ theDirection + "\"}}";
    	return serializing;
    }
    public static void main(final String[] args)
    {
    	PlayerIdx index = new PlayerIdx(2);
    	HexLocation hexLoc = new HexLocation(1,1);
    	VertexDirection vertDir = VertexDirection.NorthEast;
    	VertexLocation newLocation = new VertexLocation(hexLoc, vertDir);
    	BuildCityRequest thisTrade = new BuildCityRequest(index, newLocation);
    	String work = thisTrade.serialize();
    	System.out.println(work);
    }
}
