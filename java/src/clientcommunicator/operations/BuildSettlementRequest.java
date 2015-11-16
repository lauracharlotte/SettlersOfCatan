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
    
    public BuildSettlementRequest()
    {
        
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
    public String serialize()
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

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.free = obj.get("free").getAsBoolean();
        this.playerIndex = new PlayerIdx(obj.get("playerIndex").getAsInt());
        JsonObject vertLocation = obj.get("vertexLocation").getAsJsonObject();
        int x = vertLocation.get("x").getAsInt();
        int y = vertLocation.get("y").getAsInt();
        String direction = vertLocation.get("direction").getAsString();
        this.location = new VertexLocation(new HexLocation(x, y), Abbreviate.unAbbreviateVertex(direction));
    }
}
