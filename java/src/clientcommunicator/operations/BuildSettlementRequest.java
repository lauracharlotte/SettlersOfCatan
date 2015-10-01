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
public class BuildSettlementRequest implements IJSONSerializable
{

    private PlayerIdx playerIndex;
    private VertexLocation location;
    private boolean free;

    /**
     *
     * @param playerIndex The index of the player that wants to build the settlement
     * @param location The VertexLocation the player wants to build on
     * @param free True if in setup phase, false otherwise
     */
    public BuildSettlementRequest(PlayerIdx playerIndex, VertexLocation location, boolean free)
    {
        this.playerIndex = playerIndex;
        this.location = location;
        this.free = free;
    }
    
    /**
     *
     * @return The index of the player that wants to build the settlement
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return The VertexLocation the player wants to build on
     */
    public VertexLocation getLocation()
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
    	
    	String serializing = "{type: \"buildSettlement\", playerIndex: "
				+ playerIndex.getIndex() + ", vertexLocation: {" 
				+ "x: " + location.getHexLoc().getX() + ", y: "+ location.getHexLoc().getY()
				+ ", direction: \""+ theDirection + "\"}, free: " + free +"}";
    	return serializing;
     }
    
    public static void main(final String[] args)
    {
    	PlayerIdx index = new PlayerIdx(2);
    	HexLocation hexLoc = new HexLocation(1,1);
    	VertexDirection vertDir = VertexDirection.NorthEast;
    	VertexLocation newLocation = new VertexLocation(hexLoc, vertDir);
    	Boolean isFree = true;
    	BuildSettlementRequest thisTrade = new BuildSettlementRequest(index, newLocation, isFree);
    	String work = thisTrade.serialize();
    	System.out.println(work);
    }
    
}
