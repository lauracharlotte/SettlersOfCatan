/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.player.PlayerIdx;
import org.json.JSONException;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;

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

    public BuildRoadRequest()
    {
        
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
    public String serialize()
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

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.playerIndex = new PlayerIdx(obj.get("playerIndex").getAsInt());
        JsonObject roadLocation = obj.getAsJsonObject("roadLocation");
        this.free = obj.get("free").getAsBoolean();
        int x = roadLocation.get("x").getAsInt();
        int y = roadLocation.get("y").getAsInt();
        String direction = roadLocation.get("direction").getAsString();
        this.location = new EdgeLocation(new HexLocation(x, y), Abbreviate.unAbbreviateEdge(direction));
    }
}
