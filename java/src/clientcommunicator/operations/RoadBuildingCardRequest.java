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

/**
 *
 * @author Michael
 */
public class RoadBuildingCardRequest implements IJSONSerializable
{
    private PlayerIdx playerIndex;
    private EdgeLocation spot1;
    private EdgeLocation spot2;

    /**
     *
     * @param playerIndex The index of the player who wants to play the road building card
     * @param spot1 EdgeLocation of the first road.  Spot1 has to be connected to a current road.
     * @param spot2 EdgeLocation of the second road.  Must be connected to a current road or spot1.
     */
    public RoadBuildingCardRequest(PlayerIdx playerIndex, EdgeLocation spot1, EdgeLocation spot2)
    {
        this.playerIndex = playerIndex;
        this.spot1 = spot1;
        this.spot2 = spot2;
    }

    /**
     *
     * @return The index of the player who wants to play the road building card
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return EdgeLocation of the first road
     */
    public EdgeLocation getSpot1()
    {
        return spot1;
    }

    /**
     *
     * @return EdgeLocation of the second road
     */
    public EdgeLocation getSpot2()
    {
        return spot2;
    }
    
    /*private String abbreviate(String originalDirection)
    {
    	String newDirection = "";
    	if(originalDirection.equals("South"))
    	{
    		newDirection = "S";
    	}
    	else if(originalDirection.equals("SouthWest")) 
    	{
    		newDirection = "SW";
    	}
    	else if(originalDirection.equals("SouthEast")) 
    	{
    		newDirection = "SE";
    	}
    	else if(originalDirection.equals("North")) 
    	{
    		newDirection = "N";
    	}
    	else if(originalDirection.equals("NorthWest")) 
    	{
    		newDirection = "NE";
    	}
    	else if(originalDirection.equals("NorthEast")) 
    	{
    		newDirection = "NW";
    	}
    	return newDirection;
    }*/

    @Override
    public String serialize() //???
    {
    	
    	String theDirection1 = spot1.getDir().toString();
    	String theDirection2 = spot2.getDir().toString();
    	
    	Abbreviate abrev = new Abbreviate();    	
    	theDirection1 = abrev.abbreviate(theDirection1);
    	theDirection2 = abrev.abbreviate(theDirection2);

    	String serializing = "{type:\"Road_Building\", playerIndex: " + playerIndex.getIndex()
    	+ ", spot1: {x: "+spot1.getHexLoc().getX()+", y: "+ spot1.getHexLoc().getY()+", direction: \""+ theDirection1+"\"}, spot2: {"
    	+ "x:"+spot2.getHexLoc().getX() + ", y: "+spot2.getHexLoc().getY()+", direction: \""+theDirection2+"\"}}";
        return serializing;
    }
    
    public static void main(final String[] args)
    {
    	
    	PlayerIdx index = new PlayerIdx(2);
    	int vicIndex =1;
    	HexLocation hexLoc = new HexLocation(1,1);
    	EdgeDirection edgeDir = EdgeDirection.South;
    	EdgeLocation newLocation = new EdgeLocation(hexLoc, edgeDir);
    	
    	HexLocation hexLoc2 = new HexLocation(1,1);
    	EdgeDirection edgeDir2 = EdgeDirection.SouthEast;
    	EdgeLocation newLocation2 = new EdgeLocation(hexLoc2, edgeDir2);
    	    	
    	RoadBuildingCardRequest soldierReq = new RoadBuildingCardRequest(index, newLocation, newLocation2);
    	String work = soldierReq.serialize();
    	System.out.println(work);
    }
    
}
