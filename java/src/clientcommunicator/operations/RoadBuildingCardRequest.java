/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import model.player.PlayerIdx;
import org.json.JSONException;
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

    @Override
    public String serialize()
    {
    	String theDirection1 = spot1.getDir().toString();
    	String theDirection2 = spot2.getDir().toString();
    	
    	Abbreviate abrev = new Abbreviate();    	
    	theDirection1 = abrev.abbreviate(theDirection1);
    	theDirection2 = abrev.abbreviate(theDirection2);

    	String serializing = "{type: \"Road_Building\", playerIndex: " + playerIndex.getIndex()
    	+ ", spot1: {x: "+spot1.getHexLoc().getX()+", y: "+ spot1.getHexLoc().getY()+", direction: \""+ theDirection1+"\"}, spot2: {"
    	+ "x: "+spot2.getHexLoc().getX() + ", y: "+spot2.getHexLoc().getY()+", direction: \""+theDirection2+"\"}}";
        return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
