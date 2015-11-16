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
import shared.definitions.ResourceType;

/**
 *
 * @author Michael
 */
public class MonopolyRequest implements IJSONSerializable
{
    
    
    private PlayerIdx playerIndex;
    private ResourceType resource;

    /**
     *
     * @param playerIndex The index of the player playing the card
     * @param resource The resource type desired
     */
    public MonopolyRequest(PlayerIdx playerIndex, ResourceType resource)
    {
        this.playerIndex = playerIndex;
        this.resource = resource;
    }
    
    public MonopolyRequest()
    {
        
    }
    
    /**
     *
     * @return The index of the player playing the card
     */
    public PlayerIdx getPlayerIndex()
    {
        return playerIndex;
    }

    /**
     *
     * @return The resource type desired
     */
    public ResourceType getResource()
    {
        return resource;
    }

    
    @Override
    public String serialize()
    {
    	String rec = resource.toString();
    	String recLower = rec.toLowerCase();
    	String serializing = "{type: \"Monopoly\", resource: \"" + recLower
    						+ "\", playerIndex: "+ playerIndex.getIndex() + "}";
        return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.playerIndex = new PlayerIdx(obj.get("playerIndex").getAsInt());
        this.resource = ResourceType.valueOf(obj.get("resource").getAsString().toUpperCase());
    }
    
    
}
