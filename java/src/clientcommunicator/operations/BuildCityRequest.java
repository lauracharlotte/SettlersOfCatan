/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clientcommunicator.operations;

import com.google.gson.JsonElement;
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
    
    public BuildCityRequest()
    {
        
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
    public String serialize()
    {
    	String theDirection = location.getDir().toString();
    	Abbreviate abrev = new Abbreviate();
    	theDirection = abrev.abbreviate(theDirection);
    	
    	String serializing = "{type: \"buildCity\", playerIndex: "
    						+ playerIndex.getIndex() + ", vertexLocation: {" 
    						+ "x: " + location.getHexLoc().getX() + ", y: "+ location.getHexLoc().getY()
    						+ ", direction: \""+ theDirection + "\"}}";
    	return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.playerIndex = new PlayerIdx(obj.get("playerIndex").getAsInt());
        JsonObject vertLocation = obj.get("vertexLocation").getAsJsonObject();
        int x = vertLocation.get("x").getAsInt();
        int y = vertLocation.get("y").getAsInt();
        String direction = vertLocation.get("direction").getAsString();
        this.location = new VertexLocation(new HexLocation(x, y), Abbreviate.unAbbreviateVertex(direction));
    }
}
