/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

/**
 *
 * @author Michael
 */
public class BuildRoadRequest implements IJSONSerializable
{

    /**
     *
     * @param playerIndex The index of the player that wants to build the road
     * @param location Where the player wants to build the road
     * @param free True if in setup phase, false otherwise
     */
    public BuildRoadRequest(PlayerIdx playerIndex, EdgeLocation location, boolean free)
    {
        this.playerIndex = playerIndex;
        this.location = location;
        this.free = free;
    }

    private PlayerIdx playerIndex;
    private EdgeLocation location;
    private boolean free;
    
    /**
     *
     * @return The index of the player that wants to build the road
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return Where the player wants to build the road
     */
    public EdgeLocation getLocation()
    {
        return location;
    }

    /**
     *
     * @return True if in setup phase, false otherwise
     */
    public boolean isFree()
    {
        return free;
    }

    @Override
    public String serialize()//400
    {
    	String theDirection = location.getDir().toString();
    	Abbreviate abrev = new Abbreviate();
    	theDirection = abrev.abbreviate(theDirection);
    	String serializing = "{type: \"buildRoad\", playerIndex: " + playerIndex.getIndex()
    						+ ", roadLocation: {" 
    						+ "x: " + location.getHexLoc().getX() + ", y: "+ location.getHexLoc().getY()
    						+ ", direction: \""+ theDirection + "\"}, free: " + free + "}";
        return serializing;
     }
    
    public static void main(final String[] args)
    {
    	PlayerIdx index = new PlayerIdx(2);
    	HexLocation hexLoc = new HexLocation(1,1);
    	EdgeDirection edgeDir = EdgeDirection.NorthEast;
    	EdgeLocation newLocation = new EdgeLocation(hexLoc, edgeDir);
    	Boolean isFree = true;
    	BuildRoadRequest thisTrade = new BuildRoadRequest(index, newLocation, isFree);
    	String work = thisTrade.serialize();
    	System.out.println(work);
    }
    
}
