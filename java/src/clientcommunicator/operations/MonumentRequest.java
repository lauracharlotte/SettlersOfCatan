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
public class MonumentRequest implements IJSONSerializable
{
    
    
    private PlayerIdx playerIndex;
    
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
     * @param playerIndex The index of the player playing the card
     */
    public MonumentRequest(PlayerIdx playerIndex)
    {
        this.playerIndex = playerIndex;
    }

    public MonumentRequest()
    {
        
    }
    
    
    @Override
    public String serialize()
    {
    	String serializing = "{type: \"Monument\", playerIndex: "+ playerIndex.getIndex()+ "}";
    	return serializing;
    }

    @Override
    public void deserialize(String JSON) throws JSONException
    {
        JsonObject obj = new JsonParser().parse(JSON).getAsJsonObject();
        this.playerIndex = new PlayerIdx(obj.get("playerIndex").getAsInt());
    }
}
